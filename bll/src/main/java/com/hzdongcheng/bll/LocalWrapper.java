package com.hzdongcheng.bll;

import com.hzdongcheng.bll.basic.LocalContext;
import com.hzdongcheng.bll.basic.dto.InParamTBDeskAdd;
import com.hzdongcheng.bll.dto.InParamPTPickupListLocalFilter;
import com.hzdongcheng.bll.dto.InParamTBAllotUsableBox;
import com.hzdongcheng.bll.dto.InParamTBOpenAllBox4Recovery;
import com.hzdongcheng.bll.local.PTPickupListLocalFilter;
import com.hzdongcheng.bll.local.TBAllotUsableBox;
import com.hzdongcheng.bll.local.TBOpenAllBox;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;

public class LocalWrapper extends LocalContext {
    /**
     * 应急全开
     *
     * @param in
     */
    public String doBusiness(InParamTBOpenAllBox4Recovery in) throws DcdzSystemException {
        return (new TBOpenAllBox()).doBusiness(in);
    }

    /**
     * 获取可用门
     *
     * @param in
     */
    public String doBusiness(InParamTBAllotUsableBox in) throws DcdzSystemException {
        return (new TBAllotUsableBox()).doBusiness(in);
    }

    /**
     * 根据本地记录过滤取用列表
     */
    public String doBusiness(InParamPTPickupListLocalFilter in) throws DcdzSystemException {
        return new PTPickupListLocalFilter().doBusiness(in);
    }


}
