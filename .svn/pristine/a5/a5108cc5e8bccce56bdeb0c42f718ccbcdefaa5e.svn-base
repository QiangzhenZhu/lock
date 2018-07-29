package com.hzdongcheng.parcellocker.viewmodel;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;
import android.arch.lifecycle.ViewModel;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;

import com.hzdongcheng.bll.DBSContext;
import com.hzdongcheng.bll.MHCContext;
import com.hzdongcheng.bll.basic.dto.InParamMBDeviceRegister;
import com.hzdongcheng.bll.basic.dto.InParamPATerminalCtrlParamMod;
import com.hzdongcheng.bll.basic.dto.InParamTBTerminalAdd;
import com.hzdongcheng.bll.basic.dto.OutParamMBDeviceRegister;
import com.hzdongcheng.bll.common.FaultResult;
import com.hzdongcheng.bll.common.IResponder;
import com.hzdongcheng.bll.common.JsonResult;
import com.hzdongcheng.bll.dto.InParamPTRestoreRush;
import com.hzdongcheng.bll.dto.OutParamMBVerifyCabinet;
import com.hzdongcheng.bll.monitors.TimingTask;
import com.hzdongcheng.bll.websocket.SocketClient;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import com.hzdongcheng.components.toolkits.utils.Log4jUtils;
import com.hzdongcheng.components.toolkits.utils.RandUtils;
import com.hzdongcheng.components.toolkits.utils.StringUtils;
import com.hzdongcheng.device.HAL;
import com.hzdongcheng.parcellocker.DBSApplication;
import com.hzdongcheng.parcellocker.model.MainModel;
import com.hzdongcheng.parcellocker.utils.ResourceUtils;
import com.hzdongcheng.parcellocker.utils.SoundUtils;
import com.hzdongcheng.parcellocker.utils.ToastUtils;
import com.hzdongcheng.parcellocker.views.deliver.DeliverHolderFragment;
import com.hzdongcheng.parcellocker.views.deliver.DeliverPackageFragment;
import com.hzdongcheng.parcellocker.views.manage.ManageHolderFragment;
import com.hzdongcheng.parcellocker.views.navigate.ClientRegisterFragment;
import com.hzdongcheng.parcellocker.views.navigate.NavigateHomeFragment;
import com.hzdongcheng.parcellocker.views.navigate.NavigateLoaderFragment;
import com.hzdongcheng.parcellocker.views.pickup.PickupHolderFragment;

import java.util.Observable;
import java.util.Observer;

public class MainViewmodel extends ViewModel implements LifecycleObserver {
    private Log4jUtils log = Log4jUtils.createInstanse(MainViewmodel.class);
    public MainModel mainModel = new MainModel();

    /**
     * Lifecycle.Event 运行在主线程
     *
     * @param owner
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate(LifecycleOwner owner) {
        log.debug("[model] -->界面onCreate通知" + owner.toString());
        prepareDevice();
    }

    private LoadAsyncTask loadAsyncTask;
    ;

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    void onResume(LifecycleOwner owner) {
    }

    private void prepareDevice() {
        mainModel.getCurrentFragment().postValue(NavigateLoaderFragment.newInstance());
        if (loadAsyncTask == null || loadAsyncTask.getStatus().equals(AsyncTask.Status.FINISHED)) {
            loadAsyncTask = new LoadAsyncTask();
            loadAsyncTask.execute("");
        }
    }

    public void performRush() {
        if (mainModel.getCurrentFragment().getValue() instanceof NavigateHomeFragment)
            mainModel.getCurrentFragment().postValue(PickupHolderFragment.newInstance("rush"));
    }

    public void performFinger() {
        if (mainModel.getCurrentFragment().getValue() instanceof NavigateHomeFragment)
            mainModel.getCurrentFragment().postValue(DeliverHolderFragment.newInstance("login"));
    }


    public void performPickup() {
        if (mainModel.getCurrentFragment().getValue() instanceof NavigateHomeFragment)
            mainModel.getCurrentFragment().postValue(PickupHolderFragment.newInstance(""));
    }

    public void performDelivery() {
        if (mainModel.getCurrentFragment().getValue() instanceof NavigateHomeFragment)
            mainModel.getCurrentFragment().postValue(DeliverHolderFragment.newInstance(""));
    }

    public void performQuery() {
        if (mainModel.getCurrentFragment().getValue() instanceof NavigateHomeFragment)
            mainModel.getCurrentFragment().postValue(PickupHolderFragment.newInstance(""));
    }

    public void performManager() {
        if (mainModel.getCurrentFragment().getValue() instanceof NavigateHomeFragment)
            mainModel.getCurrentFragment().postValue(ManageHolderFragment.newInstance());
    }

    // 重置紧急密码
    public void restoreRush() {
        final InParamPTRestoreRush restoreRush = new InParamPTRestoreRush();
        restoreRush.EmergenciesPwd = RandUtils.generateNumber(6);
        IResponder responder = new IResponder() {
            @Override
            public void result(Object data) {
                InParamPATerminalCtrlParamMod inParam = new InParamPATerminalCtrlParamMod();
                inParam.CtrlTypeID = 33031;
                inParam.KeyString = "emergenciesPwd";
                inParam.DefaultValue = restoreRush.EmergenciesPwd;
                try {
                    DBSContext.localContext.doBusiness(inParam);
                    DBSContext.ctrlParam.emergenciesPwd = restoreRush.EmergenciesPwd;
                    ToastUtils.showLong("重置密码成功");
                    mainModel.getCurrentFragment().postValue(NavigateHomeFragment.newInstance());
                } catch (DcdzSystemException e) {
                    log.error("[存] 保存应急密码错误" + e.getErrorTitle());
                    ToastUtils.showLong("保存密码失败");
                }
            }

            @Override
            public void fault(Object data) {
                FaultResult faultResult = (FaultResult) data;
                ToastUtils.showLong(faultResult.faultString);
            }
        };
        try {
            MHCContext.remoteContext.doBusiness(restoreRush, responder, 0);
        } catch (DcdzSystemException e) {
            ToastUtils.showLong("重置密码失败");
        }
    }

    /**
     * 初始化设备
     */
    public void terminalRegister() {
        log.info("[APP] 开始注册设备");
        final InParamMBDeviceRegister inParam = new InParamMBDeviceRegister();
        inParam.InitPasswd = mainModel.getRegisterCode().getValue();
        inParam.remark = RandUtils.generateNumber(6);
        try {
            MHCContext.remoteContext.doBusiness(inParam, new IResponder() {
                @Override
                public void result(Object data) {
                    OutParamMBDeviceRegister outParam = (OutParamMBDeviceRegister) data;
                    InParamTBTerminalAdd terminalAdd = new InParamTBTerminalAdd();
                    terminalAdd.OperID = "system";
                    terminalAdd.TerminalNo = inParam.InitPasswd;
                    terminalAdd.TerminalName = "";
                    terminalAdd.Location = "";
                    terminalAdd.MBDeviceNo = outParam.MBDeviceNo;
                    try {
                        DBSContext.localContext.doBusiness(terminalAdd);
                        prepareDevice();
                    } catch (DcdzSystemException e) {
                        log.error("保存设备信息错误 " + e.getErrorTitle());
                    }

                    InParamPATerminalCtrlParamMod paramMod = new InParamPATerminalCtrlParamMod();
                    paramMod.CtrlTypeID = 33031;
                    paramMod.KeyString = "emergenciesPwd";
                    paramMod.DefaultValue = inParam.remark;
                    try {
                        DBSContext.localContext.doBusiness(paramMod);
                        DBSContext.ctrlParam.emergenciesPwd = paramMod.Remark;
                    } catch (DcdzSystemException e) {
                        log.error("[存] 保存应急密码错误" + e.getErrorTitle());
                    }
                }

                @Override
                public void fault(Object data) {
                    FaultResult result = (FaultResult) data;
                    log.info("初始化设备失败," + result.getFaultString());
                    ToastUtils.showLong(ResourceUtils.getString(result.getFaultString()));
                }
            }, 30);
        } catch (DcdzSystemException e) {
            log.error("初始化设备错误 " + e.getErrorTitle());
            ToastUtils.showLong(ResourceUtils.getString(e.getErrorTitle()));
        }

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestroy(LifecycleOwner owner) {
        loadAsyncTask.cancel(true);
    }

    class LoadAsyncTask extends AsyncTask<String, String, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            try {
                MHCContext.initContext();
                log.info("开始初始化数据库");
                publishProgress("开始初始化数据库");
                DBSContext.initDatabase();
                publishProgress("初始化数据库成功");
                DBSContext.deviceFault = false;
            } catch (DcdzSystemException e) {
                DBSContext.deviceFault = true;
                log.error("初始化数据库失败,系统初始化中止 >>" + e.getMessage());
                return false;
            }


            publishProgress("开始初始化网络");

            SocketClient.setOnStateChanged(new Observer() {
                @Override
                public void update(Observable o, Object arg) {
                    mainModel.getNetworkState().postValue((Boolean) arg);
                }
            });
            SocketClient.start();
            TimingTask.transport(DBSApplication.getContext());
            publishProgress("初始化网络成功");

            publishProgress("初始化驱动服务");
            if (HAL.init(DBSApplication.getContext())) {
                publishProgress("驱动服务连接成功");
                mainModel.getDeviceState().postValue(true);
            } else {
                publishProgress("驱动服务不存在");
                mainModel.getDeviceState().postValue(false);
            }

            HAL.setServiceObserver(new Observer() {
                @Override
                public void update(Observable observable, Object o) {
                    mainModel.getDeviceState().postValue((Boolean) o);
                }
            });


            SoundUtils.getInstance().loadSound(DBSApplication.getContext());
            loadMainData();

            return true;
        }

        private void loadMainData() {
            final PackageManager pm = DBSApplication.getContext().getPackageManager();
            PackageInfo pi = null;
            try {
                pi = pm.getPackageInfo(DBSApplication.getContext().getPackageName(), PackageManager.GET_ACTIVITIES);
                DBSContext.currentVersion = pi.versionName;
                mainModel.getVersion().postValue(DBSContext.currentVersion);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            mainModel.getVersion().postValue(DBSContext.currentVersion);
            mainModel.getDeviceCode().postValue(DBSContext.terminalUid);
            mainModel.getServerHot().postValue(DBSContext.ctrlParam.serviceTel);
        }

        @Override
        protected void onProgressUpdate(String... values) {
            mainModel.getProcessTips().postValue(values[0]);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (StringUtils.isNotEmpty(DBSContext.terminalUid)) {
                mainModel.getCurrentFragment().postValue(NavigateHomeFragment.newInstance());
            } else {
                mainModel.getCurrentFragment().postValue(ClientRegisterFragment.newInstance());
            }

        }
    }
}
