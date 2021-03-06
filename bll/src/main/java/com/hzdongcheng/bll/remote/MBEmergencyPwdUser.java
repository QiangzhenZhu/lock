package com.hzdongcheng.bll.remote;

import com.hzdongcheng.bll.AbstractRemoteBusiness;
import com.hzdongcheng.bll.common.CommonDAO;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.common.IResponder;
import com.hzdongcheng.bll.constant.DBSErrorCode;
import com.hzdongcheng.bll.dto.InParamMBEmergencyPwdUser;
import com.hzdongcheng.bll.dto.InParamMBUploadFingerPrint;
import com.hzdongcheng.bll.monitors.SyncDataProc;
import com.hzdongcheng.bll.utils.ThreadPool;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import com.hzdongcheng.components.toolkits.utils.StringUtils;

public class MBEmergencyPwdUser extends AbstractRemoteBusiness {
    @Override
    public String doBusiness(IRequest request, IResponder responder, int timeOut) throws DcdzSystemException {
        isUseDB = true;
        // 本地数据已经提交
        String result = callMethod(request, responder, timeOut);
        // ////////////////////////////////////////////////////////////////////////
        if (StringUtils.isNotEmpty(result)) {
            SyncDataProc.SyncData syncData = new SyncDataProc.SyncData(request, result);
            ThreadPool.QueueUserWorkItem(new SyncDataProc.SendSyncDataToServer(syncData));
        }

        return result;
    }

    @Override
    protected String handleBusiness(IRequest request, IResponder responder, int timeOut) throws DcdzSystemException {
        String result = "";
        InParamMBEmergencyPwdUser inParam = (InParamMBEmergencyPwdUser) request;

        // 1. 验证输入参数是否有效，如果无效返回-1。
        if (StringUtils.isEmpty(inParam.TerminalNo))
            throw new DcdzSystemException(DBSErrorCode.ERR_SYSTEM_PARAMTER);

        result = CommonDAO.insertUploadDataQueue(database, request);

        return result;
    }
}
