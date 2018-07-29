package com.hzdongcheng.bll.remote;

import com.hzdongcheng.bll.AbstractRemoteBusiness;
import com.hzdongcheng.bll.DBSContext;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.common.IResponder;
import com.hzdongcheng.bll.constant.DBSErrorCode;
import com.hzdongcheng.bll.dto.InParamPTObtainPickupList;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import com.hzdongcheng.components.toolkits.utils.StringUtils;

public class PTObtainPickupList extends AbstractRemoteBusiness {
    @Override
    public String doBusiness(IRequest request, IResponder responder, int timeOut)
            throws DcdzSystemException {
        isUseDB = true;
        return super.doBusiness(request, responder, timeOut);
    }

    @Override
    protected String handleBusiness(IRequest request, IResponder responder,
                                    int timeOut) throws DcdzSystemException {
        String result = "";
        InParamPTObtainPickupList inParam = (InParamPTObtainPickupList) request;

        // 1. 验证输入参数是否有效，如果无效返回-1。
        if (StringUtils.isEmpty(inParam.TerminalNo))
            inParam.TerminalNo = DBSContext.terminalUid;

        if (StringUtils.isEmpty(inParam.OperID))
            throw new DcdzSystemException(DBSErrorCode.ERR_SYSTEM_PARAMTER);


        if (responder != null)
            netProxy.sendRequest(request, responder, timeOut,
                    DBSContext.secretKey);

        return result;
    }
}
