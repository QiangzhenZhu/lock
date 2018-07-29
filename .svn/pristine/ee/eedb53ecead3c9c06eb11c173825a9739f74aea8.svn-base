package com.hzdongcheng.bll.local;

import android.database.SQLException;

import com.hzdongcheng.bll.AbstractLocalBusiness;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.constant.DBSErrorCode;
import com.hzdongcheng.bll.dto.InParamPTPickupListLocalFilter;
import com.hzdongcheng.bll.dto.OutParamPTObtainPickupList;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import com.hzdongcheng.components.toolkits.utils.StringUtils;
import com.hzdongcheng.persistent.DAOFactory;
import com.hzdongcheng.persistent.dao.PTInBoxPackageDAO;
import com.hzdongcheng.persistent.db.JDBCFieldArray;
import com.hzdongcheng.persistent.dto.PTInBoxPackage;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class PTPickupListLocalFilter extends AbstractLocalBusiness {

    public String doBusiness(IRequest inparam) throws DcdzSystemException {
        return super.callMethod(inparam);
    }

    @Override
    protected String handleBusiness(IRequest inparam) throws DcdzSystemException {
        InParamPTPickupListLocalFilter inParam = (InParamPTPickupListLocalFilter) inparam;
        String result = "";

        // 1. 验证输入参数是否有效，如果无效返回-1。
        if (StringUtils.isEmpty(inParam.PostmanID))
            throw new DcdzSystemException(DBSErrorCode.ERR_SYSTEM_PARAMTER);

        Map<String, String> packidDict = new HashMap<String, String>(20);

        // 查询所有在箱记录
        PTInBoxPackageDAO inboxPackDAO = DAOFactory.getPTInBoxPackageDAO();
        JDBCFieldArray whereColsDummy = new JDBCFieldArray();
        try {
            List<PTInBoxPackage> inboxList = inboxPackDAO.executeQuery(database,
                    whereColsDummy);
            for (PTInBoxPackage obj : inboxList) {
                packidDict.put(obj.PackageID, obj.BoxNo);
            }
            if (inParam.pickupQries != null) {
                Iterator<OutParamPTObtainPickupList> it = inParam.pickupQries.iterator();
                while (it.hasNext()) {
                    OutParamPTObtainPickupList param = it.next();
                    if (!packidDict.containsKey(param.keyID)) {
                        it.remove();
                        log.info("[取] 【注意】本地数据库没有找到钥匙信息,被移除取用列表 --> ID=" + param.keyID);
                    } else {
                        param.boxName = packidDict.get(param.keyID);
                    }
                }
            }
        } catch (SQLException e) {
            throw new DcdzSystemException(DBSErrorCode.ERR_DATABASE_DATABASELAYER,
                    e.getMessage());
        }

        return result;
    }
}
