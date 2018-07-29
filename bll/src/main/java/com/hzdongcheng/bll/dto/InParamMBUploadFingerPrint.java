package com.hzdongcheng.bll.dto;

import com.hzdongcheng.bll.common.IRequest;

import java.util.Date;

public class InParamMBUploadFingerPrint  implements IRequest {
    public String FunctionID = ""; //功能编号

    public String OperID= ""; //管理员编号
    public String WarehouseId = ""; //仓库编号
    public String TerminalNo = ""; //终端编号
}
