package com.hzdongcheng.parcellocker.viewmodel;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;
import android.arch.lifecycle.ViewModel;

import com.hzdongcheng.bll.DBSContext;
import com.hzdongcheng.bll.basic.dto.InParamOPOperLogin;
import com.hzdongcheng.bll.basic.dto.InParamPTManagerPickupPack;
import com.hzdongcheng.bll.basic.dto.InParamTBBoxHeightQry;
import com.hzdongcheng.bll.basic.dto.InParamTBBoxHeightSet;
import com.hzdongcheng.bll.basic.dto.InParamTBBoxQry4Detect;
import com.hzdongcheng.bll.basic.dto.InParamTBBoxTypeMod;
import com.hzdongcheng.bll.basic.dto.InParamTBDeskAdd;
import com.hzdongcheng.bll.basic.dto.InParamTBDeskDel;
import com.hzdongcheng.bll.basic.dto.InParamTBDeskQry;
import com.hzdongcheng.bll.basic.dto.InParamTBFaultStatusMod;
import com.hzdongcheng.bll.basic.dto.InParamTBNetworkParamMod;
import com.hzdongcheng.bll.basic.dto.InParamTBNetworkParamQry;
import com.hzdongcheng.bll.basic.dto.InParamTBOpenBox4Manager;
import com.hzdongcheng.bll.basic.dto.InParamTBTerminalAdd;
import com.hzdongcheng.bll.basic.dto.InParamTBTerminalDetail;
import com.hzdongcheng.bll.basic.dto.InParamTBTerminalMod;
import com.hzdongcheng.bll.basic.dto.InParamTBTerminalModStatus;
import com.hzdongcheng.bll.basic.dto.InParamTBTerminalParamMod;
import com.hzdongcheng.bll.basic.dto.InParamTBTerminalParamQry;
import com.hzdongcheng.bll.basic.dto.OutParamTBNetworkParamQry;
import com.hzdongcheng.bll.basic.dto.OutParamTBTerminalDetail;
import com.hzdongcheng.bll.basic.dto.OutParamTBTerminalParamQry;
import com.hzdongcheng.bll.common.IResponder;
import com.hzdongcheng.bll.constant.SysDict;
import com.hzdongcheng.bll.websocket.SocketClient;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import com.hzdongcheng.components.toolkits.utils.Log4jUtils;
import com.hzdongcheng.components.toolkits.utils.NumberUtils;
import com.hzdongcheng.parcellocker.model.BoxModel;
import com.hzdongcheng.parcellocker.model.ManageModel;
import com.hzdongcheng.parcellocker.utils.ResourceUtils;
import com.hzdongcheng.parcellocker.utils.TimerUtils;
import com.hzdongcheng.parcellocker.views.manage.ManageHomeFragment;
import com.hzdongcheng.parcellocker.views.manage.ManageLoginFragment;
import com.hzdongcheng.persistent.dto.TBBox4Detect;
import com.hzdongcheng.persistent.dto.TBDesk;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ManageViewmodel extends ViewModel implements LifecycleObserver {
    private Log4jUtils log = Log4jUtils.createInstanse(ManageViewmodel.class);
    public ManageModel manageModel = new ManageModel();

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate(LifecycleOwner owner) {
        log.info("[UI]-->进入打开管理开关界面");
        manageModel.getCurrentFragment().postValue(ManageLoginFragment.class);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestroy(LifecycleOwner owner) {
        log.info("[UI]-->退出投递");
        onCleared();
    }

    /**
     * 清理数据
     */
    @Override
    protected void onCleared() {
        manageModel.getCurrentFragment().postValue(null);
        TimerUtils.getInstance().cancelCountDownTimer();
    }
    /*******************************************  manage_login ************************************/
    /**
     * 管理员登录
     */
    public void login() {
        InParamOPOperLogin inParam = new InParamOPOperLogin();
        inParam.OperID = manageModel.getUserName().getValue();
        inParam.OperPassword = manageModel.getPassword().getValue();
        try {
            DBSContext.localContext.doBusiness(inParam);
            DBSContext.currentOperID = manageModel.getUserName().getValue();
            manageModel.getCurrentFragment().setValue(ManageHomeFragment.class);
        } catch (DcdzSystemException e) {
            log.error("管理员登入错误 " + e.getErrorTitle());
            manageModel.getErrorTips().postValue(ResourceUtils.getString(e.getErrorCode()));
        }
    }

    /*******************************************  manage_deploy ************************************/
    /**
     * 获取设备信息
     */
    public void loadData() {
        try {
            OutParamTBTerminalDetail terminalDetail = DBSContext.localContext.doBusiness(new InParamTBTerminalDetail());
            if (terminalDetail != null) {
                manageModel.getTerminalNo().setValue(terminalDetail.TerminalNo);
                manageModel.getTerminalName().setValue(terminalDetail.TerminalName);
                manageModel.getTrminalSite().setValue(terminalDetail.Location);
                manageModel.getTerminalStatus().setValue(Objects.equals(terminalDetail.TerminalStatus, SysDict.TERMINAL_STATUS_NORMAL));
            }
        } catch (DcdzSystemException e) {
            log.error("获取设备信息错误 " + e.getErrorTitle());
        }
        try {
            OutParamTBTerminalParamQry terminalParamQry = DBSContext.localContext.doBusiness(new InParamTBTerminalParamQry());
            if (terminalParamQry != null) {
                manageModel.getVoiceHint().setValue(Objects.equals(terminalParamQry.ScreensoundFlag, SysDict.DETECT_STATUS_LOCKED));
                manageModel.getRepulseOpen().setValue(terminalParamQry.RefuseCloseDoor > 0);
                manageModel.getShakeDetect().setValue(Objects.equals(terminalParamQry.ShockInspectFlag, SysDict.DETECT_STATUS_LOCKED));
                manageModel.getGoodsDetect().setValue(Objects.equals(terminalParamQry.DoorInspectFlag, SysDict.DETECT_STATUS_LOCKED));
                manageModel.getPowerDetect().setValue(Objects.equals(terminalParamQry.PowerInspectFlag, SysDict.DETECT_STATUS_LOCKED));
            }
        } catch (DcdzSystemException e) {
            log.error("获取坚持功能错误 " + e.getErrorTitle());
        }
        try {
            OutParamTBNetworkParamQry networkParamQry = DBSContext.localContext.doBusiness(new InParamTBNetworkParamQry());
            if (networkParamQry != null) {
                manageModel.getSerHost().setValue(networkParamQry.ServerIP);
                manageModel.getSerPort().setValue(networkParamQry.ServerPort + "");
                manageModel.getMonHost().setValue(networkParamQry.MonServerIP);
                manageModel.getMonPort().setValue(networkParamQry.MonServerPort + "");

            }
        } catch (DcdzSystemException e) {
            log.error("获取服务器地址信息错误 " + e.getErrorTitle());
        }
    }

    /**
     * 保存设备信息
     */
    public void saveload() {
        if (Objects.equals("2017001", DBSContext.terminalUid)) {
            InParamTBTerminalAdd terminalAdd = new InParamTBTerminalAdd();
            terminalAdd.TerminalNo = manageModel.getTerminalNo().getValue();
            terminalAdd.TerminalName = manageModel.getTerminalName().getValue();
            terminalAdd.Location = manageModel.getTrminalSite().getValue();
            try {
                DBSContext.localContext.doBusiness(terminalAdd);
            } catch (DcdzSystemException e) {
                log.error("保存设备信息错误 " + e.getErrorTitle());
            }
        } else {
            InParamTBTerminalMod terminalMod = new InParamTBTerminalMod();
            terminalMod.TerminalNo = manageModel.getTerminalNo().getValue();
            terminalMod.TerminalName = manageModel.getTerminalName().getValue();
            terminalMod.Location = manageModel.getTrminalSite().getValue();
            try {
                DBSContext.localContext.doBusiness(terminalMod);
            } catch (DcdzSystemException e) {
                log.error("修改设备信息错误 " + e.getErrorTitle());
            }
        }

        InParamTBTerminalModStatus terminalModStatus = new InParamTBTerminalModStatus();
        terminalModStatus.TerminalStatus = manageModel.getTerminalStatus().getValue() ? SysDict.TERMINAL_STATUS_NORMAL : SysDict.TERMINAL_STATUS_FAULT;
        try {
            DBSContext.localContext.doBusiness(terminalModStatus);
        } catch (DcdzSystemException e) {
            log.error("修改设备状态信息错误 " + e.getErrorTitle());
        }

        InParamTBTerminalParamMod terminalParamMod = new InParamTBTerminalParamMod();
        terminalParamMod.ScreensoundFlag = manageModel.getVoiceHint().getValue() ? SysDict.DETECT_STATUS_LOCKED : SysDict.DETECT_STATUS_NORMAL;
        terminalParamMod.ShockInspectFlag = manageModel.getShakeDetect().getValue() ? SysDict.DETECT_STATUS_LOCKED : SysDict.DETECT_STATUS_NORMAL;
        terminalParamMod.DoorInspectFlag = manageModel.getGoodsDetect().getValue() ? SysDict.DETECT_STATUS_LOCKED : SysDict.DETECT_STATUS_NORMAL;
        terminalParamMod.PowerInspectFlag = manageModel.getPowerDetect().getValue() ? SysDict.DETECT_STATUS_LOCKED : SysDict.DETECT_STATUS_NORMAL;
        terminalParamMod.RefuseCloseDoor = manageModel.getRepulseOpen().getValue() ? 3 : 0;
        terminalParamMod.ArticleInspectFlag = manageModel.getVoiceHint().getValue() ? SysDict.DETECT_STATUS_LOCKED : SysDict.DETECT_STATUS_NORMAL;
        try {
            DBSContext.localContext.doBusiness(terminalParamMod);
        } catch (DcdzSystemException e) {
            log.error("修改配置功能错误 " + e.getErrorTitle());
        }

        InParamTBNetworkParamMod inParamMod = new InParamTBNetworkParamMod();
        inParamMod.ServerIP = manageModel.getSerHost().getValue();
        inParamMod.ServerPort = NumberUtils.parseInt(manageModel.getSerPort().getValue());
        inParamMod.MonServerIP = manageModel.getSerHost().getValue();
        inParamMod.MonServerPort = NumberUtils.parseInt(manageModel.getMonPort().getValue());
        try {
            DBSContext.localContext.doBusiness(inParamMod);
            SocketClient.stop();
            SocketClient.start();
        } catch (DcdzSystemException e) {
            log.error("修改服务器地址错误 " + e.getErrorTitle());
        }
    }

    /*******************************************  manage_records ************************************/
    /**
     * 获取投递记录
     */
    public void queryDeliverRecords() {
        try {
            manageModel.getOutDeliveryRecordQry().setValue(DBSContext.localContext.doBusiness(manageModel.getIndeliveryRecordQry().getValue()));
        } catch (DcdzSystemException e) {
            log.error("获取投递记录错误 " + e.getErrorTitle());
        }
    }

    /**
     * 管理员取件
     */
    public void managerPickUp() {
        InParamPTManagerPickupPack inParam = new InParamPTManagerPickupPack();
        inParam.PackageID = manageModel.getPackageId().getValue();
        inParam.OperID = manageModel.getUserName().getValue();
        IResponder responder = new IResponder() {
            @Override
            public void result(Object data) {

            }

            @Override
            public void fault(Object info) {

            }
        };
        try {
            DBSContext.remoteContext.doBusiness(inParam, responder, 20);
            queryDeliverRecords();
        } catch (DcdzSystemException e) {
            log.error("管理员取件错误 " + e.getErrorTitle());
        }

    }
    /*******************************************  manage_boxdetect ************************************/
    /**
     * 获取箱门信息
     */
    public List<BoxModel> queryBoxInfo() {
        try {
            List<TBBox4Detect> box4Detects = DBSContext.localContext.doBusiness(new InParamTBBoxQry4Detect());
            List<TBDesk> deskList = DBSContext.localContext.doBusiness(new InParamTBDeskQry());
            List<BoxModel> boxModels = new ArrayList<>();
            int deskId = 0;
            for (TBBox4Detect box : box4Detects) {
                if (deskId != box.DeskNo) {
                    BoxModel linear = new BoxModel();
                    linear.linear = 1;
                    linear.deskId = box.DeskNo;
                    boxModels.add(linear);
                    deskId = box.DeskNo;
                }
                BoxModel boxModel = new BoxModel();
                boxModel.boxName = box.BoxName;
                boxModel.boxSpan = (int) box.BoxHeight;
                boxModel.article = Integer.parseInt(box.ArticeStatus);
                boxModel.boxType = Integer.parseInt(box.BoxType);
                boxModel.door = Integer.parseInt(box.OpenStatus);
                boxModel.boxId = Integer.parseInt(box.BoxNo);
                boxModel.deskId = box.DeskNo;
                boxModel.deskType = deskList.get(box.DeskNo).DeskType;
                boxModel.fault = Objects.equals("1", box.FaultStatus);
                boxModels.add(boxModel);
            }
            manageModel.getBoxinfo().postValue(boxModels);
            return boxModels;
        } catch (DcdzSystemException e) {
            log.error("获取箱门信息错误 " + e.getErrorTitle());
            return new ArrayList<>();
        }
    }

    /**
     * 开箱
     */
    public void openOneBox(String boxName) {
        InParamTBOpenBox4Manager InParam = new InParamTBOpenBox4Manager();
        InParam.BoxNo = boxName;
        try {
            DBSContext.localContext.doBusiness(InParam);
        } catch (DcdzSystemException e) {
            log.error("后台开箱错误 " + e.getErrorTitle());
        }
    }

    /**
     * 全开
     */
    public void openfullBox() {
        for (BoxModel box : manageModel.getBoxinfo().getValue()) {
            if (!Objects.equals("", box.boxName)) {
                openOneBox(box.boxName);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /*******************************************  manage_boxlayout ************************************/

    /**
     * 获取箱门高度
     */
    public void getBoxesSpan() {
        InParamTBBoxHeightQry inParam = new InParamTBBoxHeightQry();
        try {
            manageModel.getBoxHeightQry().setValue(DBSContext.localContext.doBusiness(inParam));
        } catch (DcdzSystemException e) {
            log.error("获取箱门高度错误 " + e.getErrorTitle());
        }
    }

    /**
     * 设置箱门高度
     */
    public void setBoxesSpan() {
        InParamTBBoxHeightSet inParam = new InParamTBBoxHeightSet();
        inParam.TerminalHeight = manageModel.getBoxHeightQry().getValue().TerminalHeight;
        inParam.MiniHeight = manageModel.getBoxHeightQry().getValue().MiniHeight;
        inParam.SmallHeight = manageModel.getBoxHeightQry().getValue().SmallHeight;
        inParam.MediumHeight = manageModel.getBoxHeightQry().getValue().MediumHeight;
        inParam.LargeHeight = manageModel.getBoxHeightQry().getValue().LargeHeight;
        inParam.SuperHeight = manageModel.getBoxHeightQry().getValue().SuperHeight;
        inParam.MasterHeight = manageModel.getBoxHeightQry().getValue().MasterHeight;
        inParam.AdvertisingHeight = manageModel.getBoxHeightQry().getValue().AdvertisingHeight;
        try {
            DBSContext.localContext.doBusiness(inParam);
            queryBoxInfo();
        } catch (DcdzSystemException e) {
            log.error("设置箱门高度错误 " + e.getErrorTitle());
        }
    }

    /**
     * 添加副柜
     */
    public void doSaveCabinetAdd(int boxCount) {
        InParamTBDeskAdd inParam = new InParamTBDeskAdd();
        inParam.BoxNumStr = boxCount + "";
        inParam.DeskType = manageModel.getCabinetType().getValue();
        try {
            DBSContext.localContext.doBusiness(inParam);
        } catch (DcdzSystemException e) {
            log.error("添加副柜错误 " + e.getErrorTitle());
        }
    }

    /**
     * 删除副柜
     */
    public void doSaveCabinetDelete(int deskId) {
        InParamTBDeskDel inParam = new InParamTBDeskDel();
        inParam.DeskNo = deskId;
        try {
            DBSContext.localContext.doBusiness(inParam);
        } catch (DcdzSystemException e) {
            log.error("删除副柜错误 " + e.getErrorTitle());
        }
    }

    /**
     * 修改箱门类型
     */
    public void doSaveBoxMod(int boxId, String boxType) {
        InParamTBBoxTypeMod inParam = new InParamTBBoxTypeMod();
        inParam.BoxNo = boxId + "";
        inParam.BoxType = boxType;
        try {
            DBSContext.localContext.doBusiness(inParam);
        } catch (DcdzSystemException e) {
            log.error("修改箱门类型错误 " + e.getErrorTitle());
        }
    }

    /**
     * 修改箱门状态
     */
    public void doSaveBoxStatueMod(int boxId, String boxStatue) {
        InParamTBFaultStatusMod inParam = new InParamTBFaultStatusMod();
        inParam.BoxNo = boxId + "";
        inParam.FaultStatus = boxStatue;
        try {
            DBSContext.localContext.doBusiness(inParam);
        } catch (DcdzSystemException e) {
            log.error("修改箱门状态错误 " + e.getErrorTitle());
        }
    }

}
