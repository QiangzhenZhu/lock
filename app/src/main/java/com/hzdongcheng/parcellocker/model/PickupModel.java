package com.hzdongcheng.parcellocker.model;

import android.arch.lifecycle.MutableLiveData;

import com.hzdongcheng.bll.basic.dto.OutParamPTVerfiyUser;
import com.hzdongcheng.bll.constant.SysDict;
import com.hzdongcheng.bll.dto.OutParamPTObtainPickupList;
import com.hzdongcheng.parcellocker.utils.WrapperFragment;

import java.util.List;

public class PickupModel {
    public MutableLiveData<String> OpenBoxCode = new MutableLiveData<>();
    /**
     * 密码取用时使用
     */
    public OutParamPTObtainPickupList pickupInfo = new OutParamPTObtainPickupList();

    public String pickupWay = SysDict.TAKEOUT_WAY_PWD;

    /**
     * 用户取件方式
     *
     * @param pickupWay 取件方式,默认密码取件
     */
    public void setPickupWay(String pickupWay) {
        this.pickupWay = pickupWay;
    }

    private MutableLiveData<Class<? extends WrapperFragment>> currentFragment = new MutableLiveData<>();

    public MutableLiveData<Class<? extends WrapperFragment>> getCurrentFragment() {
        return currentFragment;
    }

    private MutableLiveData<String> openBoxCode = new MutableLiveData<>();

    public MutableLiveData<String> getOpenBoxCode() {
        return openBoxCode;
    }

    private MutableLiveData<String> inputError = new MutableLiveData<>();

    public MutableLiveData<String> getInputError() {
        return inputError;
    }

    private MutableLiveData<String> boxOpenSuccess = new MutableLiveData<>();

    public MutableLiveData<String> getBoxOpenSuccess() {
        return boxOpenSuccess;
    }

    public String userName = "";

    public MutableLiveData<String> updateListView = new MutableLiveData<>();

    private MutableLiveData<List<OutParamPTObtainPickupList>> pickupList = new MutableLiveData<>();

    public MutableLiveData<List<OutParamPTObtainPickupList>> getPickupList() {
        return pickupList;
    }

    private MutableLiveData<List<OutParamPTObtainPickupList>> checkedList = new MutableLiveData<>();

    public MutableLiveData<List<OutParamPTObtainPickupList>> getCheckedList() {
        return checkedList;
    }
}
