package com.hzdongcheng.parcellocker.viewmodel;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.OnLifecycleEvent;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.LifecycleObserver;
import android.graphics.ColorSpace;

import com.google.gson.reflect.TypeToken;
import com.hzdongcheng.bll.DBSContext;
import com.hzdongcheng.bll.MHCContext;
import com.hzdongcheng.bll.basic.dto.InParamPATerminalCtrlParamMod;
import com.hzdongcheng.bll.basic.dto.InParamPTPickupPackage;
import com.hzdongcheng.bll.basic.dto.InParamPTVerfiyUser;
import com.hzdongcheng.bll.basic.dto.InParamTBOpenBox4Delivery;
import com.hzdongcheng.bll.constant.SysDict;
import com.hzdongcheng.bll.dto.InParamPTPickupBeforeOpen;
import com.hzdongcheng.bll.dto.InParamPTPickupFailed;
import com.hzdongcheng.bll.dto.InParamPTPickupListLocalFilter;
import com.hzdongcheng.bll.dto.InParamPTObtainPickupList;
import com.hzdongcheng.bll.dto.InParamTBOpenAllBox4Recovery;
import com.hzdongcheng.bll.basic.dto.InParamTBOpenBox4Pickup;
import com.hzdongcheng.bll.basic.dto.OutParamPTVerfiyUser;
import com.hzdongcheng.bll.common.FaultResult;
import com.hzdongcheng.bll.common.IResponder;
import com.hzdongcheng.bll.common.JsonResult;
import com.hzdongcheng.bll.dto.InParamMBEmergencyPwdUser;
import com.hzdongcheng.bll.dto.OutParamPTObtainPickupList;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import com.hzdongcheng.components.toolkits.utils.Log4jUtils;
import com.hzdongcheng.components.toolkits.utils.RandUtils;
import com.hzdongcheng.components.toolkits.utils.StringUtils;
import com.hzdongcheng.device.HAL;
import com.hzdongcheng.device.bean.BoxStatus;
import com.hzdongcheng.parcellocker.model.FingerBean;
import com.hzdongcheng.parcellocker.model.PickupModel;
import com.hzdongcheng.parcellocker.utils.FingerprintCache;
import com.hzdongcheng.parcellocker.utils.ResourceUtils;
import com.hzdongcheng.parcellocker.utils.SoundUtils;
import com.hzdongcheng.parcellocker.utils.TimerUtils;
import com.hzdongcheng.parcellocker.utils.ToastUtils;
import com.hzdongcheng.parcellocker.views.pickup.PickupCodeFragment;
import com.hzdongcheng.parcellocker.views.pickup.PickupListFragment;
import com.hzdongcheng.parcellocker.views.pickup.PickupOpenedFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;

public class PickupViewmodel extends ViewModel implements LifecycleObserver {
    private Log4jUtils log = Log4jUtils.createInstanse(this.getClass());
    public PickupModel pickupModel = new PickupModel();
    private List<FingerBean> fingerBeans = new ArrayList<>();
    List<OutParamPTObtainPickupList> list = new ArrayList<>();

    /**
     * 业务是否正在执行，防止重入执行
     */
    private AtomicBoolean isExecuting = new AtomicBoolean(false);

    private boolean detectFinger = false;

    public void cancelFingerCollect() {
        detectFinger = false;
    }

    private ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();


    /**
     * 继续取件
     */
    public void pickupContinue() {
        pickupModel.getOpenBoxCode().postValue("");
        pickupModel.getInputError().postValue("");
        obtainPickupList();
        pickupModel.getCurrentFragment().postValue(PickupListFragment.class);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    void onResume(LifecycleOwner owner) {
        log.info("[UI]-->【进入取件界面】");
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestroy(LifecycleOwner owner) {
        log.info("[UI]-->【取件界面销毁】");
        onCleared();
    }

    /**
     * 清理数据
     */
    @Override
    protected void onCleared() {
        pickupModel.getOpenBoxCode().postValue("");
        pickupModel.getInputError().postValue("");
        pickupModel.getBoxOpenSuccess().postValue("");
        pickupModel.getCurrentFragment().postValue(null);
        cancelFingerCollect();
        detectFlag = false;
        TimerUtils.getInstance().cancelCountDownTimer();
    }

    /**
     * 取件密码验证
     */
    public void pickupByCode() {
        InParamPTVerfiyUser inParam = new InParamPTVerfiyUser();
        inParam.OpenBoxKey = pickupModel.getOpenBoxCode().getValue();

        IResponder responder = new IResponder() {
            @Override
            public void result(Object data) {
                JsonResult result = (JsonResult) data;
                OutParamPTVerfiyUser eval = result.Eval(OutParamPTVerfiyUser.class);
                pickupModel.setPickupWay(SysDict.TAKEOUT_WAY_PWD);
                pickupModel.pickupInfo.boxName = eval.BoxNo;
                pickupModel.pickupInfo.keyID = eval.PackageID;
                openBox4Pickup(pickupModel.pickupInfo);
                detectBoxStatus();
            }

            @Override
            public void fault(Object info) {
                if (info != null) {
                    FaultResult faultResult = (FaultResult) info;
                    pickupModel.getInputError().postValue(ResourceUtils.getString(faultResult.faultCode));
                    switch (faultResult.faultCode) {
                        case "E33003":
                            SoundUtils.getInstance().play(23);
                            break;
                        case "E500":
                        case "E501":
                        case "E505":
                        case "E507":
                            SoundUtils.getInstance().play(27);
                            break;
                        default:
                            //24，提货码错误，请重试
                            SoundUtils.getInstance().play(24);
                            break;
                    }
                }
            }
        };
        try {
            DBSContext.remoteContext.doBusiness(inParam, responder, 200);
        } catch (DcdzSystemException e) {
            log.error("[取]-->取件异常 " + e.getMessage());
        }
    }

    /**
     * 取件开箱
     */
    private void openBox4Pickup(OutParamPTObtainPickupList out) {
        log.info("[取] 取件验证通过，开始开箱 : boxName=" + out.boxName + ", keyId= " + out.keyID);
        InParamTBOpenBox4Pickup inParam = new InParamTBOpenBox4Pickup();
        inParam.BoxNo = out.boxName;
        inParam.PackageID = out.keyID;
        try {
            DBSContext.localContext.doBusiness(inParam);
            out.retryCount++;
            out.isOpened = true;
            pickupModel.updateListView.postValue(inParam.BoxNo);
            SoundUtils.getInstance().play(8);
        } catch (DcdzSystemException e) {
            log.error("[取] 开箱失败，错误信息：" + e.getMessage());
            pickupModel.getInputError().postValue(ResourceUtils.getString(e.getErrorCode()));
        }
    }

    private void pickupSuccess(String keyId) {
        // 取件完成，删除在箱并上传记录
        InParamPTPickupPackage inParamPTPickupPackage = new InParamPTPickupPackage();
        inParamPTPickupPackage.PackageID = keyId;
        inParamPTPickupPackage.TakedWay = pickupModel.pickupWay;
        try {
            DBSContext.remoteContext.doBusiness(inParamPTPickupPackage, null, 20);
        } catch (DcdzSystemException e) {
            log.error("[取] 取件完成后执行【PTPickupPackage】出现异常:" + e.getMessage());
        }
    }

    private boolean detectFlag = true;

    public boolean canContinue() {
        boolean can = true;
        for (OutParamPTObtainPickupList it : pickupModel.getCheckedList().getValue()) {
            if ((it.status == 0)) {
                can = false;
                break;
            }
        }
        return can;
    }

    /**
     * 开始检测箱门状态
     */
    private void detectBoxStatus() {
        detectFlag = true;
        service.submit(new Runnable() {
            @Override
            public void run() {
                while (detectFlag) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ignored) {
                    }

                    for (OutParamPTObtainPickupList next : pickupModel.getCheckedList().getValue()) {
                        if (next.isOpened && next.status == 0) {
                            try {
                                BoxStatus boxStatus = HAL.getBoxStatus(next.boxName);
                                if (boxStatus.getGoodsStatus() == 0 && boxStatus.getOpenStatus() == 0) {
                                    pickupSuccess(next.keyID);
                                    next.status = 1;
                                    //次变量的值更改，用以刷新ListView
                                    pickupModel.updateListView.postValue(next.boxName);
                                }

                                if (boxStatus.getOpenStatus() == 0 && boxStatus.getGoodsStatus() == 1) {
                                    try {
                                        HAL.openBox(next.boxName);
                                    } catch (Exception e) {
                                        log.error("[取] 检测线程弹开箱门异常 " + e.getMessage());
                                    }
                                }
                            } catch (DcdzSystemException ignored) {
                                log.error("[取] 取件检测箱门状态异常 " + ignored.getMessage());
                            }
                        }
                    }
                }
            }
        });
    }

    /**
     * 已经取出
     *
     * @param param
     */
    public void hadTakeOut(OutParamPTObtainPickupList param) {
        pickupSuccess(param.keyID);
    }

    /**
     * 放弃取用
     *
     * @param param
     */
    public void takeOutFailed(OutParamPTObtainPickupList param) {
        InParamPTPickupFailed inParamPTPickupFailed = new InParamPTPickupFailed();
        inParamPTPickupFailed.keyId = param.keyID;
        inParamPTPickupFailed.boxName = param.boxName;
        inParamPTPickupFailed.terminalNo = DBSContext.terminalUid;
        try {
            MHCContext.remoteContext.doBusiness(inParamPTPickupFailed, null, 0);
        } catch (DcdzSystemException e) {
            log.error("[取] 放弃取用异常 " + e.toString());
        }
    }

    // 重试开箱
    public void retryOpenBox(String boxName) {
        log.info("[取] 用户点击重试按钮，重试打开箱门 " + boxName);
        try {
            HAL.openBox(boxName);
        } catch (DcdzSystemException e) {
            log.error("[取] 取件重新开箱异常 " + e.getMessage());
        }
    }


    /**
     * 根据staffId获取可取列表
     */
    public void obtainPickupList() {
        detectFlag = false;
        final InParamPTObtainPickupList inParam = new InParamPTObtainPickupList();
        inParam.OperID = pickupModel.userName;
        try {
            MHCContext.remoteContext.doBusiness(inParam, new IResponder() {
                @Override
                public void result(Object data) {
                    List<OutParamPTObtainPickupList> outlist= (List<OutParamPTObtainPickupList>) data;
                    list.addAll(outlist);
                    if (list.size() < 1) {
                        ToastUtils.showLong("没有可以取用的钥匙");
                        return;
                    }
                    //本地查询箱号
                    InParamPTPickupListLocalFilter filter = new InParamPTPickupListLocalFilter();
                    filter.PostmanID = inParam.OperID;
                    filter.pickupQries = list;

                    try {
                        pickupModel.getCurrentFragment().postValue(PickupListFragment.class);
                        MHCContext.localContext.doBusiness(filter);
                        pickupModel.setPickupWay(SysDict.TAKEOUT_WAY_CARDID);
                    } catch (DcdzSystemException e) {
                        log.error("[取] 取用列表的本地箱号查询异常 -->" + e.getMessage());
                        ToastUtils.showLong("查找取用列表箱号错误");
                        return;
                    }
                    pickupModel.getPickupList().postValue(list);
                }

                @Override
                public void fault(Object data) {
                    if (data != null) {
                        FaultResult result = (FaultResult) data;
                        log.info("[取] 获取取用列表失败" + result.getFaultString());
                        ToastUtils.showLong(ResourceUtils.getString(result.faultString));
                    }
                }
            }, 30);
        } catch (DcdzSystemException e) {
            log.error("[取] 获取取用列表异常" + e.getErrorTitle());
            ToastUtils.showLong("数据加载失败");
        }

        // 测试取用列表效果-测试数据
//        List<OutParamPTObtainPickupList> list = new ArrayList<>();
//        for (int i = 0; i < 6; i++) {
//            OutParamPTObtainPickupList outParamPTObtainPickupList = new OutParamPTObtainPickupList();
//            outParamPTObtainPickupList.keyID = i + RandUtils.generateCharacter(5);
//            outParamPTObtainPickupList.boxName = RandUtils.generateNumber(2);
//            outParamPTObtainPickupList.carModel = RandUtils.generateString(32);
//            outParamPTObtainPickupList.reason = i % 2 == 0 ? "车辆出库" : "移位";
//            outParamPTObtainPickupList.carUnique = RandUtils.generateCharacter(20);
//            list.add(outParamPTObtainPickupList);
//        }
//        pickupModel.getCurrentFragment().postValue(PickupListFragment.class);
//        pickupModel.getPickupList().postValue(list);
    }

    /**
     * 选择本次取用的钥匙，开始开箱
     */
    public void performOpenCheckList() {
        if (!isExecuting.compareAndSet(false, true)) {
            return;
        }
        final List<OutParamPTObtainPickupList> checked = new ArrayList<>();
        for (OutParamPTObtainPickupList it : pickupModel.getPickupList().getValue()) {
            if (it.isChecked) {
                checked.add(it);
            }
        }
        if (checked.size() < 1) {
            ToastUtils.showLong("请选择要取用的钥匙");
            isExecuting.set(false);
            return;
        }

        pickupModel.getCheckedList().setValue(checked);

        InParamPTPickupBeforeOpen inParam = new InParamPTPickupBeforeOpen();
        inParam.pickupPackageList = new ArrayList<>();
        for (OutParamPTObtainPickupList it : checked) {
            InParamPTPickupPackage pickupPackage = new InParamPTPickupPackage();
            pickupPackage.BoxNo = it.boxName;
            pickupPackage.PackageID = it.keyID;
            pickupPackage.PostmanID = pickupModel.userName;
            inParam.pickupPackageList.add(pickupPackage);
        }

        IResponder responder = new IResponder() {
            @Override
            public void result(Object data) {
                //通知服务器准备开箱后执行开箱
                pickupModel.getCurrentFragment().postValue(PickupOpenedFragment.class);
                for (OutParamPTObtainPickupList it : checked) {
                    openBox4Pickup(it);
                }
                detectBoxStatus();
                isExecuting.set(false);
            }

            @Override
            public void fault(Object data) {
                FaultResult faultResult = (FaultResult) data;
                ToastUtils.showLong(faultResult.faultString);
                isExecuting.set(false);
            }
        };

        try {
            MHCContext.remoteContext.doBusiness(inParam, responder, 10);
        } catch (DcdzSystemException e) {
            log.error("[取] 准备开箱异常" + e.getMessage());
            ToastUtils.showLong("服务连接失败，请重试");
            isExecuting.set(false);
        }

    }

    /**
     * 应急开箱
     */
    public void urgentOpenBox() {
        try {
            MHCContext.localContext.doBusiness(new InParamTBOpenAllBox4Recovery());

            InParamPATerminalCtrlParamMod inParam = new InParamPATerminalCtrlParamMod();
            inParam.CtrlTypeID = 33031;
            inParam.KeyString = "emergenciesPwd";
            inParam.DefaultValue = "";
            try {
                DBSContext.localContext.doBusiness(inParam);
                DBSContext.ctrlParam.emergenciesPwd = "";
            } catch (DcdzSystemException e) {
                log.error("清空应急密码错误" + e.getErrorTitle());
            }

            MHCContext.remoteContext.doBusiness(new InParamMBEmergencyPwdUser(), null, 30);
        } catch (DcdzSystemException e) {
            log.error("应急开箱错误 " + e.getErrorTitle());
            ToastUtils.showLong("应急开箱失败");
        }

    }

    /**
     * 指纹登入
     */
    public void fingerLogin() {
        detectFinger = true;
        service.submit(new Runnable() {
            @Override
            public void run() {

                fingerBeans = FingerprintCache.getFinger();
                if (fingerBeans.size() < 1) {
                    ToastUtils.showLong("请先录入指纹");
                    return;
                }

                try {
                    if (!HAL.fingerOpen()) {
                        ToastUtils.showLong("指纹仪打开失败");
                        return;
                    }

                    while (detectFinger) {
                        if (!(HAL.fingerPressed())) {
                            try {
                                Thread.sleep(300);
                            } catch (InterruptedException ignored) {
                            }
                            continue;
                        }
                        String fingerFeature = HAL.getFingerFeature();
                        if (StringUtils.isNotEmpty(fingerFeature)) {
                            for (FingerBean f : fingerBeans) {
                                if (HAL.featureMatch(fingerFeature, f.fp)) {
                                    pickupModel.userName = f.pt;
                                    ToastUtils.showLong("指纹验证通过");
                                    obtainPickupList();
                                    return;
                                }
                            }
                            ToastUtils.showLong("登录失败,请重试");
                        } else {
                            HAL.fingerClose();
                            try {
                                Thread.sleep(300);
                            } catch (InterruptedException ignored) {
                            }
                            HAL.fingerOpen();
                            ToastUtils.showLong("请刷指纹");
                        }
                    }
                } catch (DcdzSystemException e) {
                    ToastUtils.showLong("指纹仪连接失败，请返回重试");
                } finally {
                    try {
                        HAL.fingerClose();
                    } catch (DcdzSystemException ignored) {
                    }
                }
            }
        });
    }
}
