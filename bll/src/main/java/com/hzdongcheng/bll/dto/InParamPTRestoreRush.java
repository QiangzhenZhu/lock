package com.hzdongcheng.bll.dto;

import com.hzdongcheng.bll.common.IRequest;

public class InParamPTRestoreRush implements IRequest {
    public String FunctionID = ""; //功能编号

    public String TerminalNo = ""; //终端编号
    public String EmergenciesPwd = ""; //应急密码
    public String remark;

}
