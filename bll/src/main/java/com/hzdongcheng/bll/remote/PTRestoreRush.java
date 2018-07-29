package com.hzdongcheng.bll.remote;

import com.hzdongcheng.bll.AbstractRemoteBusiness;
import com.hzdongcheng.bll.DBSContext;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.common.IResponder;
import com.hzdongcheng.bll.constant.DBSErrorCode;
import com.hzdongcheng.bll.dto.InParamPTRestoreRush;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import com.hzdongcheng.components.toolkits.utils.RandUtils;
import com.hzdongcheng.components.toolkits.utils.StringUtils;

/**
 * MHC 重置紧急取件密码
 */
public class PTRestoreRush extends AbstractRemoteBusiness {
    public String doBusiness(IRequest request, IResponder responder, int timeOut) throws DcdzSystemException {
        return super.callMethod(request, responder, timeOut);
    }

    @Override
    protected String handleBusiness(IRequest request, IResponder responder,
                                    int timeOut) throws DcdzSystemException {
        String result = "";
        InParamPTRestoreRush inParam = (InParamPTRestoreRush) request;
        if (StringUtils.isEmpty(inParam.TerminalNo))
            throw new DcdzSystemException(DBSErrorCode.ERR_SYSTEM_PARAMTER);
        // 1. 验证输入参数是否有效，如果无效返回-1。
        if (StringUtils.isEmpty(inParam.EmergenciesPwd))
            inParam.EmergenciesPwd = RandUtils.generateNumber(6);
        if (responder != null)
            netProxy.sendRequest(request, responder, timeOut,
                    DBSContext.secretKey);
        return result;
    }
}
