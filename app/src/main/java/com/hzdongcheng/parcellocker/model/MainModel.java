package com.hzdongcheng.parcellocker.model;

import android.arch.lifecycle.MutableLiveData;

import com.hzdongcheng.parcellocker.utils.WrapperFragment;

public class MainModel {
    private MutableLiveData<String> processTips = new MutableLiveData<>();

    public MutableLiveData<String> getProcessTips() {
        return processTips;
    }

    private MutableLiveData<Boolean> dataBaseState = new MutableLiveData<>();

    public MutableLiveData<Boolean> getDataBaseState() {
        return dataBaseState;
    }

    private MutableLiveData<Boolean> networkState = new MutableLiveData<>();

    public MutableLiveData<Boolean> getNetworkState() {
        return networkState;
    }

    public MutableLiveData<Boolean> getDeviceState() {
        return deviceState;
    }

    public MutableLiveData<Boolean> deviceState = new MutableLiveData<>();



    private MutableLiveData<String> version = new MutableLiveData<>();

    public MutableLiveData<String> getVersion() {
        return version;
    }

    private MutableLiveData<String> deviceCode = new MutableLiveData<>();

    public MutableLiveData<String> getDeviceCode() {
        return deviceCode;
    }

    private MutableLiveData<String> serverHot = new MutableLiveData<>();

    public MutableLiveData<String> getServerHot() {
        return serverHot;
    }

    private MutableLiveData<String> registerCode = new MutableLiveData<>();

    private MutableLiveData<WrapperFragment> currentFragment = new MutableLiveData<>();

    public MutableLiveData<WrapperFragment> getCurrentFragment() {
        return currentFragment;
    }

    public MutableLiveData<String> getRegisterCode() {
        return registerCode;
    }
}
