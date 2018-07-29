package com.hzdongcheng.bll.dto;

import com.hzdongcheng.bll.basic.dto.InParamPTPickupPackage;
import com.hzdongcheng.bll.common.IRequest;

import java.util.List;

public class InParamPTPickupBeforeOpen implements IRequest {
    public String FunctionID = ""; //功能编号

    public String TerminalNo= ""; //设备号
    public List<InParamPTPickupPackage> pickupPackageList; //出柜钥匙列表
}
