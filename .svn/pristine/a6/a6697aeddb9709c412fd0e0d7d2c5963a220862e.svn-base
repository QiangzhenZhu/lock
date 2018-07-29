package com.hzdongcheng.parcellocker.model;

import android.arch.lifecycle.MutableLiveData;

import com.hzdongcheng.bll.basic.dto.OutParamPTDeliveryRecordQry;
import com.hzdongcheng.bll.basic.dto.OutParamPTExpiredPackQry;
import com.hzdongcheng.bll.basic.dto.OutParamPTPostmanLogin;
import com.hzdongcheng.bll.basic.dto.OutParamPTReadPackageQry;
import com.hzdongcheng.parcellocker.utils.WrapperFragment;

import java.util.List;

public class DeliverModel {
    public OutParamPTPostmanLogin postmanInfo;
    public List<OutParamPTExpiredPackQry> expiredPackQryList;
    public List<OutParamPTReadPackageQry> readPackageList;
    public OutParamPTDeliveryRecordQry outParamPTDeliveryRecordQry;
    public boolean needNetCheck = false;
    public boolean localCheck = false;
    public String packageID;

    private MutableLiveData<Class<? extends WrapperFragment>> currentFragment = new MutableLiveData<>();

    public MutableLiveData<Class<? extends WrapperFragment>> getCurrentFragment() {
        return currentFragment;
    }

    private MutableLiveData<String> userName = new MutableLiveData<>();

    public MutableLiveData<String> getUserName() {
        return userName;
    }

    private MutableLiveData<String> password = new MutableLiveData<>();

    public MutableLiveData<String> getPassword() {
        return password;
    }

    private MutableLiveData<String> waybillNo = new MutableLiveData<>();

    public MutableLiveData<String> getWaybillNo() {
        return waybillNo;
    }

    private MutableLiveData<String> addressee = new MutableLiveData<>();

    public MutableLiveData<String> getAddressee() {
        return addressee;
    }

    public String openPwd;

    public String boxName;

    private MutableLiveData<Integer> operationType = new MutableLiveData<>();

    public MutableLiveData<Integer> getOperationType() {
        return operationType;
    }

    private MutableLiveData<Integer> boxUsableNum = new MutableLiveData<>();

    public MutableLiveData<Integer> getBoxUsableNum() {
        return boxUsableNum;
    }

    private MutableLiveData<Integer> smallBoxUsableNum = new MutableLiveData<>();

    public MutableLiveData<Integer> getSmallBoxUsableNum() {
        return smallBoxUsableNum;
    }

    private MutableLiveData<Integer> middleBoxUsableNum = new MutableLiveData<>();

    public MutableLiveData<Integer> getMiddleBoxUsableNum() {
        return middleBoxUsableNum;
    }

    private MutableLiveData<Integer> largeBoxUsableNum = new MutableLiveData<>();

    public MutableLiveData<Integer> getLargeBoxUsableNum() {
        return largeBoxUsableNum;
    }

    private MutableLiveData<Integer> expirePackageNum = new MutableLiveData<>();

    public MutableLiveData<Integer> getExpirePackageNum() {
        return expirePackageNum;
    }

    private MutableLiveData<Integer> currentBoxSize = new MutableLiveData<>();

    public MutableLiveData<Integer> getCurrentBoxSize() {
        return currentBoxSize;
    }

    private MutableLiveData<String> customerMobile = new MutableLiveData<>();

    public MutableLiveData<String> getCustomerMobile() {
        return customerMobile;
    }

    private MutableLiveData<List<OutParamPTDeliveryRecordQry>> outParamPTDeliveryRecordQrys = new MutableLiveData<>();

    public MutableLiveData<List<OutParamPTDeliveryRecordQry>> getOutParamPTDeliveryRecordQrys() {
        return outParamPTDeliveryRecordQrys;
    }
}
