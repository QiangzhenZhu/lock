package com.hzdongcheng.bll.local;

import android.database.SQLException;

import com.hzdongcheng.bll.AbstractLocalBusiness;
import com.hzdongcheng.bll.common.CommonDAO;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.constant.DBSErrorCode;
import com.hzdongcheng.bll.constant.SysDict;
import com.hzdongcheng.bll.dto.InParamTBAllotUsableBox;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import com.hzdongcheng.components.toolkits.utils.StringUtils;
import com.hzdongcheng.device.HAL;
import com.hzdongcheng.device.bean.BoxStatus;
import com.hzdongcheng.persistent.db.DbUtils;
import com.hzdongcheng.persistent.db.JDBCFieldArray;
import com.hzdongcheng.persistent.dto.TBBox;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 本地查找一个可用的格口
 */
public class TBAllotUsableBox extends AbstractLocalBusiness {
    public String doBusiness(InParamTBAllotUsableBox inParam)
            throws DcdzSystemException {
        String result;
        result = callMethod(inParam);
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected String handleBusiness(IRequest request)
            throws DcdzSystemException {
        InParamTBAllotUsableBox inParam = (InParamTBAllotUsableBox) request;

        List<String> usableBoxes = new ArrayList();
        String[] boxRightList = inParam.BoxList.split(",");
        try {
            // 查询业务层可用箱体(根据输入的箱类型)
            JDBCFieldArray whereSQL = new JDBCFieldArray();
            List<TBBox> boxList = DbUtils.executeQuery(database, "V_FreeBox", whereSQL, TBBox.class, "DeskNo,DeskBoxNo");

            for (TBBox tbBox : boxList) {
                usableBoxes.add(tbBox.BoxNo);
            }
        } catch (SQLException e) {
            throw new DcdzSystemException(DBSErrorCode.ERR_BUSINESS_NOFREEDBOX);
        }
        if (usableBoxes.size() == 0)
            throw new DcdzSystemException(DBSErrorCode.ERR_BUSINESS_NOFREEDBOX);

        //打乱顺序
        Collections.shuffle(usableBoxes);
        int tryCount = 0;
        for (String boxName : usableBoxes) {
            if (tryCount++ > 3) {
                break;
            }
            log.info("[存] 分配到空闲格口 [" + boxName + "] 开始查询格口状态");
            try {
                BoxStatus boxStatus = HAL.getBoxStatus(boxName);
                if (boxStatus.getGoodsStatus() == 0 && boxStatus.getOpenStatus() == 0) {
                    return boxName;
                }
                log.info("[存] 格口不满足分配条件 -->" + boxStatus.getGoodsStatus() + ":" + boxStatus.getOpenStatus());
            } catch (Exception e) {
                //出现异常可能是驱动程序配置的格口布局和APP配置的不一致
                log.info("[存] 分配格口时查询格口状态异常 -->" + e.getMessage());
            }
        }
        throw new DcdzSystemException(DBSErrorCode.ERR_DRIVER_NOFREE);
    }
}

