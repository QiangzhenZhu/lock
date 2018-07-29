package com.hzdongcheng.bll.dto;

import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.components.toolkits.utils.DateUtils;

import java.util.Date;

public class InParamPTDeliveryKeyVerify implements IRequest {
    public String FunctionID = ""; //功能编号

    public String TerminalNo = ""; //设备号
    public String OperID; //仓管Id
    public String KeyId = ""; //钥匙ID
    public String OpenBoxPwd = ""; //开箱密码
    public Integer BoxNo; //箱门编号
    public Date OccurTime = DateUtils.getMinDate(); //入柜申请时间
}
