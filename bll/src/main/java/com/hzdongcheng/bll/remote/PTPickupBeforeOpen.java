package com.hzdongcheng.bll.remote;

import com.hzdongcheng.bll.AbstractRemoteBusiness;
import com.hzdongcheng.bll.DBSContext;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.common.IResponder;
import com.hzdongcheng.bll.constant.DBSErrorCode;
import com.hzdongcheng.bll.dto.InParamPTPickupBeforeOpen;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import com.hzdongcheng.components.toolkits.utils.StringUtils;

public class PTPickupBeforeOpen extends AbstractRemoteBusiness {
    @Override
    public String doBusiness(IRequest request, IResponder responder, int timeOut) throws DcdzSystemException {
        return super.doBusiness(request, responder, timeOut);
    }

    @Override
    protected String handleBusiness(IRequest request, IResponder responder, int timeOut) throws DcdzSystemException {
        InParamPTPickupBeforeOpen inParam = (InParamPTPickupBeforeOpen) request;
        if (StringUtils.isEmpty(inParam.TerminalNo)) {
            inParam.TerminalNo = DBSContext.terminalUid;
        }
        if (inParam.pickupPackageList == null || inParam.pickupPackageList.size() < 1) {
            throw new DcdzSystemException(DBSErrorCode.ERR_SYSTEM_PARAMTER);
        }
        if (responder != null) {
            netProxy.sendRequest(request, responder, timeOut, "");
        }

        return "";
    }
}
