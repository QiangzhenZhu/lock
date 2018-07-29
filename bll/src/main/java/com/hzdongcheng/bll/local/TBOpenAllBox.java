package com.hzdongcheng.bll.local;

import android.database.SQLException;

import com.hzdongcheng.bll.AbstractLocalBusiness;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.constant.DBSErrorCode;
import com.hzdongcheng.bll.constant.SysDict;
import com.hzdongcheng.bll.dto.InParamTBOpenAllBox4Recovery;
import com.hzdongcheng.bll.utils.EncryptHelper;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import com.hzdongcheng.components.toolkits.utils.DateUtils;
import com.hzdongcheng.components.toolkits.utils.StringUtils;
import com.hzdongcheng.device.HAL;
import com.hzdongcheng.persistent.DAOFactory;
import com.hzdongcheng.persistent.dao.PTDeliverHistoryDAO;
import com.hzdongcheng.persistent.dao.PTInBoxPackageDAO;
import com.hzdongcheng.persistent.dao.TBBoxDAO;
import com.hzdongcheng.persistent.db.JDBCFieldArray;
import com.hzdongcheng.persistent.dto.PTDeliverHistory;
import com.hzdongcheng.persistent.dto.PTInBoxPackage;
import com.hzdongcheng.persistent.dto.TBBox;

import java.util.List;

public class TBOpenAllBox extends AbstractLocalBusiness {
    public String doBusiness(InParamTBOpenAllBox4Recovery inParam)
            throws DcdzSystemException {
        String result;
        result = callMethod(inParam);
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected String handleBusiness(IRequest request) throws DcdzSystemException {
        String result = "";
        TBBoxDAO boxDAO = DAOFactory.getTBBoxDAO();
        JDBCFieldArray whereCols0 = new JDBCFieldArray();
        List<TBBox> boxs = boxDAO.executeQuery(database, whereCols0);
        for (TBBox box : boxs) {
            try {
                Thread.sleep(100);
                log.info("[取] 紧急开箱 箱号 = " + box.BoxName);
                HAL.openBox(box.BoxName);
            } catch (InterruptedException | DcdzSystemException ignored) {
                log.info("[取] 紧急开箱出现异常，箱号 = " + box.BoxName + ",错误信息：" + ignored.getMessage());
            }
        }

        // 查询在箱记录 转移到历史信息表
        PTInBoxPackageDAO inBoxPackageDAO = DAOFactory.getPTInBoxPackageDAO();
        PTDeliverHistoryDAO deliverHistoryDAO = DAOFactory.getPTDeliverHistoryDAO();

        List<PTInBoxPackage> ptInBoxPackages = inBoxPackageDAO.executeQuery(database, null);
        //先删除记录，保证存件记录正常
        int count = inBoxPackageDAO.delete(database, null);

        PTDeliverHistory bean = new PTDeliverHistory();
        for (PTInBoxPackage inBoxPackage : ptInBoxPackages) {
            try {
                bean.TerminalNo = inBoxPackage.TerminalNo;
                bean.PackageID = inBoxPackage.PackageID;
                bean.BoxNo = inBoxPackage.BoxNo;
                bean.StoredTime = inBoxPackage.StoredTime;
                bean.StoredDate = inBoxPackage.StoredDate;
                bean.PostmanID = inBoxPackage.PostmanID;
                bean.OpenBoxKey = inBoxPackage.OpenBoxKey;
                bean.TakedTime = DateUtils.nowDate();
                bean.LeftFlag = inBoxPackage.LeftFlag;
                bean.PosPayFlag = inBoxPackage.PosPayFlag;
                bean.PackageStatus = SysDict.PACKAGE_STATUS_OUTNORMAL;
                bean.UploadFlag = SysDict.UPLOAD_FLAG_NO;
                bean.TradeWaterNo = inBoxPackage.TradeWaterNo;
                bean.LastModifyTime = DateUtils.nowDate(); // 服务器时间
                bean.Remark = inBoxPackage.DynamicCode + "," + inBoxPackage.TradeWaterNo;
                bean.TakedWay = SysDict.TAKEOUT_WAY_UNKNOWN;

                deliverHistoryDAO.insert(database, bean);
            } catch (SQLException e) {
                log.info("紧急取件转入历史记录异常 -->" + e.toString());
            }
        }
        return String.valueOf(count);
    }
}
