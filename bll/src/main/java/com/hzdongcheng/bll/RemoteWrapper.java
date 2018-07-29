package com.hzdongcheng.bll;

import com.hzdongcheng.bll.basic.RemoteContext;
import com.hzdongcheng.bll.basic.dto.InParamMBDeviceRegister;
import com.hzdongcheng.bll.common.IResponder;
import com.hzdongcheng.bll.dto.InParamMBEmergencyPwdUser;
import com.hzdongcheng.bll.dto.InParamMBUploadFingerPrint;
import com.hzdongcheng.bll.dto.InParamPTCancelDelivery;
import com.hzdongcheng.bll.dto.InParamPTPickupBeforeOpen;
import com.hzdongcheng.bll.dto.InParamPTObtainPickupList;
import com.hzdongcheng.bll.dto.InParamPTPickupFailed;
import com.hzdongcheng.bll.dto.InParamPTRestoreRush;
import com.hzdongcheng.bll.remote.MBEmergencyPwdUser;
import com.hzdongcheng.bll.remote.MBUploadFingerPrint;
import com.hzdongcheng.bll.remote.PTCancelDelivery;
import com.hzdongcheng.bll.remote.PTPickupBeforeOpen;
import com.hzdongcheng.bll.remote.PTObtainPickupList;
import com.hzdongcheng.bll.remote.PTPickupFailed;
import com.hzdongcheng.bll.remote.PTRestoreRush;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;

public class RemoteWrapper extends RemoteContext {

    @Override
    public String doBusiness(InParamMBDeviceRegister request, IResponder responder, int timeOut) throws DcdzSystemException {
        return super.doBusiness(request, responder, timeOut);
    }

    /**
     * 指纹上传
     */
    public String doBusiness(InParamMBUploadFingerPrint request, IResponder responder, int timeOut) throws DcdzSystemException {
        return new MBUploadFingerPrint().doBusiness(request, responder, timeOut);
    }

    /**
     * 重置紧急密码
     */
    public String doBusiness(InParamPTRestoreRush request, IResponder responder, int timeOut) throws DcdzSystemException {
        return new PTRestoreRush().doBusiness(request, responder, timeOut);
    }

    /**
     * 应急密码被使用
     */
    public String doBusiness(InParamMBEmergencyPwdUser request, IResponder responder, int timeOut) throws DcdzSystemException {
        return new MBEmergencyPwdUser().doBusiness(request, responder, timeOut);
    }

    /**
     * 指纹登录获取取用列表
     */
    public String doBusiness(InParamPTObtainPickupList request, IResponder responder, int timeOut) throws DcdzSystemException {
        return new PTObtainPickupList().doBusiness(request, responder, timeOut);
    }

    /**
     * 取消存钥匙
     */
    public String doBusiness(InParamPTCancelDelivery request, IResponder responder, int timeOut) throws DcdzSystemException {
        return new PTCancelDelivery().doBusiness(request, responder, timeOut);
    }

    /**
     * 取件准备开箱时上传买好车
     */
    public String doBusiness(InParamPTPickupBeforeOpen request, IResponder responder, int timeOut) throws DcdzSystemException {
        return new PTPickupBeforeOpen().doBusiness(request, responder, timeOut);
    }

    /**
     * 取件开箱（重试）失败, 取消取件
     */
    public String doBusiness(InParamPTPickupFailed request, IResponder responder, int timeOut) throws DcdzSystemException {
        return new PTPickupFailed().doBusiness(request, responder, timeOut);
    }
}
