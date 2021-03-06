package com.hzdongcheng.parcellocker.viewmodel;

import android.app.Fragment;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.CheckBox;

import com.hzdongcheng.bll.DBSContext;
import com.hzdongcheng.bll.MHCContext;
import com.hzdongcheng.bll.basic.dto.InParamPTDeliveryPackage;
import com.hzdongcheng.bll.basic.dto.InParamPTPackIsDelivery;
import com.hzdongcheng.bll.basic.dto.InParamPTPackageDetail;
import com.hzdongcheng.bll.basic.dto.InParamPTPostmanLogin;
import com.hzdongcheng.bll.basic.dto.InParamPTSetBoxFault;
import com.hzdongcheng.bll.basic.dto.OutParamPTPostmanLogin;
import com.hzdongcheng.bll.common.FaultResult;
import com.hzdongcheng.bll.common.IResponder;
import com.hzdongcheng.bll.constant.DBSErrorCode;
import com.hzdongcheng.bll.constant.SysDict;
import com.hzdongcheng.bll.dto.InParamMBUploadFingerPrint;
import com.hzdongcheng.bll.dto.InParamPTCancelDelivery;
import com.hzdongcheng.bll.dto.InParamTBAllotUsableBox;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import com.hzdongcheng.components.toolkits.utils.DateUtils;
import com.hzdongcheng.components.toolkits.utils.Log4jUtils;
import com.hzdongcheng.components.toolkits.utils.NumberUtils;
import com.hzdongcheng.components.toolkits.utils.RandUtils;
import com.hzdongcheng.components.toolkits.utils.StringUtils;
import com.hzdongcheng.device.HAL;
import com.hzdongcheng.device.bean.BoxStatus;
import com.hzdongcheng.device.bean.ICallBack;
import com.hzdongcheng.parcellocker.R;
import com.hzdongcheng.parcellocker.model.DeliverModel;
import com.hzdongcheng.parcellocker.model.FingerBean;
import com.hzdongcheng.parcellocker.utils.FingerprintCache;
import com.hzdongcheng.parcellocker.utils.ResourceUtils;
import com.hzdongcheng.parcellocker.utils.SoundUtils;
import com.hzdongcheng.parcellocker.utils.TimerUtils;
import com.hzdongcheng.parcellocker.utils.ToastUtils;
import com.hzdongcheng.parcellocker.views.deliver.DeliverBoxFaultFragment;
import com.hzdongcheng.parcellocker.views.deliver.DeliverHomeFragment;
import com.hzdongcheng.parcellocker.views.deliver.DeliverInputFingerFragment;
import com.hzdongcheng.parcellocker.views.deliver.DeliverOpenedFragment;
import com.hzdongcheng.parcellocker.views.deliver.DeliverOpeningFragment;
import com.hzdongcheng.parcellocker.views.deliver.DeliverPackageFragment;
import com.hzdongcheng.parcellocker.views.widget.InformationFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;

public class DeliverViewmodel extends ViewModel implements LifecycleObserver {
    private Log4jUtils log = Log4jUtils.createInstanse(DeliverViewmodel.class);
    public DeliverModel model = new DeliverModel();
    private ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
    private AtomicBoolean onOpen = new AtomicBoolean(false);
    public List<FingerBean> fingerBeans = new ArrayList<>();

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate(LifecycleOwner owner) {
        log.info("[存]-->进入投递员登录界面");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestroy(LifecycleOwner owner) {
        log.info("[存]-->退出投递");
        onCleared();
    }

    /**
     * 清理数据
     */
    @Override
    protected void onCleared() {
        cancelFingerCollect();
        detectFlag = false;
        model.getUserName().postValue("");
        model.getPassword().postValue("");
        model.getCurrentFragment().postValue(null);
        onOpen.set(false);
    }

    private void getCardReaderData() {
        HAL.setCardReaderCallBack(new ICallBack() {
            @Override
            public void onMessage(String data, Integer type) {
                if (Objects.requireNonNull(model.getCurrentFragment().getValue()).getSimpleName().equals(DeliverPackageFragment.class.getSimpleName())) {
                    try {
                        if (onOpen.compareAndSet(false, true)) {
                            model.packageID = data;
                            prepareDelivery();
                        }
                    } catch (Exception e) {
                        log.error("[存]-->未被处理的异常 :" + e.getMessage());
                        onOpen.set(false);
                    }
                }
            }
        });
    }

    /**
     * 投递员账号密码登录-录指纹
     */
    public void login() {
        final InParamPTPostmanLogin inParam = new InParamPTPostmanLogin();
        inParam.PostmanID = model.getUserName().getValue();
        inParam.Password = model.getPassword().getValue();
        inParam.VerifyFlag = SysDict.POSTMAN_VERIFY_PWD;
        IResponder responder = new IResponder() {
            @Override
            public void result(Object data) {
                //登录成功之后进入【指纹管理】界面
                model.postmanInfo = (OutParamPTPostmanLogin) data;
                fingerBeans = FingerprintCache.getFinger(model.postmanInfo.PostmanID);
                model.getCurrentFragment().postValue(DeliverHomeFragment.class);
            }

            @Override
            public void fault(Object info) {
                if (info != null) {
                    FaultResult result = (FaultResult) info;
                    log.info("[存] 投递员登录失败," + result.getFaultString());
                    ToastUtils.showLong(ResourceUtils.getString(result.faultString));
                }
            }
        };
        try {
            DBSContext.remoteContext.doBusiness(inParam, responder, 20);
        } catch (DcdzSystemException e) {
            log.error("[存] 投递员登录错误 " + e.getErrorTitle());
            ToastUtils.showLong(ResourceUtils.getString(e.getErrorCode()));
        }

    }


    /**
     * 验证IC卡是否可以投递
     */
    private void prepareDelivery() {
        // 1. 预分配一个可用箱门
        InParamTBAllotUsableBox allotUsableBox = new InParamTBAllotUsableBox();
        try {
            model.boxName = MHCContext.localContext.doBusiness(allotUsableBox);
        } catch (DcdzSystemException e) {
            ToastUtils.showLong(ResourceUtils.getString(e.getErrorCode()));
            onOpen.set(false);
            return;
        }

        // 2. 本地查询 是否已投递过
        InParamPTPackIsDelivery inParam = new InParamPTPackIsDelivery();
        inParam.PackageID = model.packageID;
        inParam.ReadyPackList = model.readPackageList;
        try {
            DBSContext.localContext.doBusiness(inParam);
        } catch (DcdzSystemException e) {
            log.error("[存] IC卡本地校验失败" + e.getErrorTitle());
            if (e.getErrorCode() == DBSErrorCode.ERR_BUSINESS_PACKDELIVERYD) {
                ToastUtils.showLong("记录已经存在");
            } else
                ToastUtils.showLong(ResourceUtils.getString(e.getErrorCode()));
            // 存 流程终止
            onOpen.set(false);
            return;
        }

        // 3. 远程查询 IC卡是否做过绑定
        int pwdLen = NumberUtils.parseInt(DBSContext.ctrlParam.takeOutPwdLen);
        model.openPwd = RandUtils.generateNumber(pwdLen);

        final InParamPTPackageDetail detail = new InParamPTPackageDetail();
        detail.PackageID = model.packageID;
        detail.PostmanID = model.getUserName().getValue();
        detail.boxName = model.boxName;
        // 预生成取件密码
        detail.DynamicCode = model.openPwd;

        IResponder responder = new IResponder() {
            @Override
            public void result(Object data) {
                log.info("[存] IC卡验证成功，开始开箱，boxName=" + detail.boxName);
                retryCount = 0;
                openBox4Delivery(model.boxName);
                service.execute(detectBoxStatus);
                onOpen.set(false);
            }

            @Override
            public void fault(Object info) {
                FaultResult faultResult = (FaultResult) info;
                log.error("[存] IC卡验证失败 --> " + faultResult.faultString);
                ToastUtils.showLong(faultResult.faultString);
                onOpen.set(false);
            }
        };
        try {
            DBSContext.remoteContext.doBusiness(detail, responder, 10);
        } catch (DcdzSystemException e) {
            log.error("[存] IC卡验证请求失败 " + e.getErrorTitle());
            ToastUtils.showLong(ResourceUtils.getString(e.getErrorCode()));
            onOpen.set(false);
        }

//        openBox4Delivery(model.getBoxName().getValue());
//        onOpen.set(false);
    }

    /**
     * 投递开箱
     *
     * @param boxName
     * @return
     */
    private void openBox4Delivery(final String boxName) {
        service.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    int count = 0;
                    boolean opened = false;
                    do {
                        HAL.openBox(boxName);
                        try {
                            Thread.sleep(130);
                            BoxStatus boxStatus = HAL.getBoxStatus(boxName);
                            opened = boxStatus.getOpenStatus() == 1;
                        } catch (InterruptedException ignored) {
                        }

                    } while (!opened && count++ < 3);

                    model.getCurrentFragment().postValue(DeliverOpeningFragment.class);
                    model.getOperationType().postValue(SysDict.OPERATION_TYPE_DELIVER);
                    SoundUtils.getInstance().play(17);

                } catch (DcdzSystemException e) {
                    log.error("[存] 投递开箱异常 --" + e.getErrorTitle());
                    SoundUtils.getInstance().play(18);
                }
            }
        });
    }

    /**
     * 确认投递
     */
    private void ensureDelivery() {
        if (StringUtils.isEmpty(model.boxName)
                || StringUtils.isEmpty(model.packageID)) {
            return;
        }
        InParamPTDeliveryPackage inParam = new InParamPTDeliveryPackage();
        inParam.PostmanID = model.getUserName().getValue();
        inParam.PackageID = model.packageID;
        inParam.BoxNo = model.boxName;
        inParam.OpenBoxKey = model.openPwd;
        inParam.LeftFlag = SysDict.PACKAGE_LEFT_FLAG_N0;

        try {
            //业务处理
            DBSContext.remoteContext.doBusiness(inParam, null, 20);
            model.getCurrentFragment().postValue(DeliverOpenedFragment.class);
            log.info("[存] 存件成功，进入刷IC卡界面");
            model.boxName = "";
            model.packageID = "";
        } catch (DcdzSystemException e) {
            log.error("[存] 保存投递记录异常" + e.getMessage());
        }
    }

    int retryCount = 0;

    // 重试开箱
    public void retryOpenBox(Fragment fragment) {
        if (retryCount++ > 3) {
            SpannableStringBuilder builder = new SpannableStringBuilder("是否将"+model.boxName+"号柜设为故障柜？" );
            ForegroundColorSpan span = new ForegroundColorSpan(Color.parseColor("#FFD260"));
            builder.setSpan(span, 3, 3 + model.boxName.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            final InformationFragment informationFragment = InformationFragment.newInstance("已经重试3次?", builder);
            informationFragment.setOnFragmentInteractionListener(new InformationFragment.OnFragmentInteractionListener() {
                @Override
                public void onFragmentInteraction(View view) {
                    if (view.getId() == R.id.bt_positive) {
                        setBoxFault();
                        retryCount = 0;
                    }
                    informationFragment.dismiss();
                }
            });
            informationFragment.show(fragment.getChildFragmentManager(),fragment.getClass().getName());

        }
        log.info("[存] 重试打开箱门, boxName = " + model.boxName);
        openBox4Delivery(model.boxName);
    }

    // 设置为故障柜
    public void setBoxFault() {
        detectFlag = false;
        InParamPTSetBoxFault inParamPTSetBoxFault = new InParamPTSetBoxFault();
        inParamPTSetBoxFault.BoxNo = model.boxName;
        inParamPTSetBoxFault.PostmanID = model.getUserName().getValue();
        log.info("[存] 设置格口为故障，并取消此次存件, boxName=" + inParamPTSetBoxFault.BoxNo);

        try {
            DBSContext.localContext.doBusiness(inParamPTSetBoxFault);
        } catch (DcdzSystemException e) {
            log.error("[存] 设置为故障箱失败 --> boxName = " + inParamPTSetBoxFault.BoxNo);
        }

        InParamPTCancelDelivery cancelDelivery = new InParamPTCancelDelivery();
        cancelDelivery.KeyId = model.packageID;
        try {
            MHCContext.remoteContext.doBusiness(cancelDelivery, null, 0);
        } catch (DcdzSystemException e) {
            log.error("[存] 格口故障，取消投递异常 ==" + e.getMessage());
        }

        log.error("[存] 设置格口故障，跳转到设置故障柜成功界面");
        model.boxName = "";
        model.getCurrentFragment().setValue(DeliverBoxFaultFragment.class);
    }

    private boolean detectFlag = true;

    /**
     * 投递箱门检测
     *
     * @param boxList
     * @return
     */
    public Runnable detectBoxStatus = new Runnable() {
        @Override
        public void run() {
            String boxName = model.boxName;
            // 箱门打开成功，不允许倒计时自动退出
            detectFlag = true;
            while (detectFlag) {
                try {
                    Thread.sleep(500);
                    BoxStatus boxStatus = HAL.getBoxStatus(boxName);
                    if (boxStatus.getOpenStatus() == 0 && boxStatus.getGoodsStatus() == 1) {
                        ensureDelivery();
                        break;
                    }
                    if (boxStatus.getOpenStatus() == 0 && boxStatus.getGoodsStatus() == 0) {
                        HAL.openBox(boxName);
                    }
                } catch (DcdzSystemException | InterruptedException ignored) {
                }
            }
        }
    };

    //#region  指纹管理

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
                                    model.getUserName().postValue(f.pt);
                                    ToastUtils.showLong("指纹验证通过");
                                    model.getCurrentFragment().postValue(DeliverPackageFragment.class);
                                    getCardReaderData();
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


    public void addFinger() {
        if (fingerBeans.size() >= 10) {
            ToastUtils.showShort("指纹录入已达上限");
            return;
        }
        model.getCurrentFragment().setValue(DeliverInputFingerFragment.class);
        detectFinger = true;
        service.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    if (!HAL.fingerOpen()) {
                        ToastUtils.showLong("指纹仪打开失败");
                        return;
                    }

                    while (detectFinger) {
                        try {
                            Thread.sleep(300);
                        } catch (InterruptedException ignored) {
                        }
                        if (!(HAL.fingerPressed())) {
                            continue;
                        }
                        String fingerFeature = HAL.getFingerFeature();
                        if (StringUtils.isNotEmpty(fingerFeature)) {
                            FingerBean fingerBean = new FingerBean();
                            fingerBean.pt = model.postmanInfo.PostmanID;
                            fingerBean.fp = fingerFeature;
                            fingerBeans.add(fingerBean);
                            FingerprintCache.setFinger(fingerBeans);

                            try {
                                InParamMBUploadFingerPrint inParam = new InParamMBUploadFingerPrint();
                                inParam.OperID = model.postmanInfo.PostmanID;
                                inParam.WarehouseId = DBSContext.terminalNick;
                                MHCContext.remoteContext.doBusiness(inParam, null, 30);
                            } catch (DcdzSystemException e) {
                                log.error("指纹上传错误" + e.getMessage());
                            }
                            ToastUtils.showLong("新增成功！");
                            model.getCurrentFragment().postValue(DeliverHomeFragment.class);
                            break;
                        } else {
                            HAL.fingerClose();
                            try {
                                Thread.sleep(300);
                            } catch (InterruptedException ignored) {
                            }
                            HAL.fingerOpen();
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

    public void cancelFingerCollect() {
        detectFinger = false;
    }

    private boolean detectFinger = false;

    public void delFinger(List<Integer> index) {
        for (int i : index) {
            fingerBeans.remove(fingerBeans.get(i));
        }
        FingerprintCache.setFinger(fingerBeans);
    }
    //#endregion
}
