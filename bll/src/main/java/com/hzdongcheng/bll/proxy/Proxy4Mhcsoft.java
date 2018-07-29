package com.hzdongcheng.bll.proxy;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hzdongcheng.bll.basic.dto.InParamMBDeviceRegister;
import com.hzdongcheng.bll.basic.dto.InParamPTDeliveryPackage;
import com.hzdongcheng.bll.basic.dto.InParamPTPackageDetail;
import com.hzdongcheng.bll.basic.dto.InParamPTPickupPackage;
import com.hzdongcheng.bll.basic.dto.InParamPTPostmanLogin;
import com.hzdongcheng.bll.basic.dto.OutParamMBDeviceRegister;
import com.hzdongcheng.bll.basic.dto.OutParamPTDeliveryPackage;
import com.hzdongcheng.bll.basic.dto.OutParamPTExpiredPackQry;
import com.hzdongcheng.bll.basic.dto.OutParamPTPickupPackage;
import com.hzdongcheng.bll.basic.dto.OutParamPTPostmanLogin;
import com.hzdongcheng.bll.common.FaultResult;
import com.hzdongcheng.bll.common.IResponder;
import com.hzdongcheng.bll.common.JsonResult;
import com.hzdongcheng.bll.common.PacketUtils;
import com.hzdongcheng.bll.dto.InParamMBEmergencyPwdUser;
import com.hzdongcheng.bll.dto.InParamMBGetToken;
import com.hzdongcheng.bll.dto.InParamMBUploadFingerPrint;
import com.hzdongcheng.bll.dto.InParamPTPickupFailed;
import com.hzdongcheng.bll.dto.InParamPTRestoreRush;
import com.hzdongcheng.bll.dto.InParamPTCancelDelivery;
import com.hzdongcheng.bll.dto.InParamPTPickupBeforeOpen;
import com.hzdongcheng.bll.dto.InParamPTObtainPickupList;
import com.hzdongcheng.bll.dto.OutParamMBGetToken;
import com.hzdongcheng.bll.dto.OutParamMBVerifyCabinet;
import com.hzdongcheng.bll.dto.OutParamPTObtainPickupList;
import com.hzdongcheng.bll.proxy.http.HttpHelper;
import com.hzdongcheng.bll.proxy.http.JsonHttpReturn;
import com.hzdongcheng.bll.utils.EncryptHelper;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import com.hzdongcheng.components.toolkits.utils.DateUtils;
import com.hzdongcheng.components.toolkits.utils.JsonUtils;
import com.hzdongcheng.components.toolkits.utils.Log4jUtils;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

import Decoder.BASE64Encoder;

public class Proxy4Mhcsoft extends Proxy4Dcdzsoft {
    Log4jUtils log = Log4jUtils.createInstanse(Proxy4Mhcsoft.class);
    private String appSecret = "b64a5ac1f58543ab89e4cf8d07453039";

    @Override
    public String doBusiness(InParamMBDeviceRegister request, IResponder responder, int timeOut, String secretKey) throws DcdzSystemException {
        Map<String, Object> data = new TreeMap<>();
        data.put("keyCabinetId", request.InitPasswd);
        data.put("emergencyPassword", EncryptHelper.encrypt(request.remark));

        String ret = HttpHelper.postJSON("verifyCabinetInit.json", packSendData(data));

        JsonHttpReturn httpReturn = JsonUtils.toBean(ret, JsonHttpReturn.class);
        OutParamMBDeviceRegister outParam = new OutParamMBDeviceRegister();
        if (httpReturn.success) {
            outParam.MBDeviceNo = httpReturn.data;
            outParam.remark = request.remark;
            responder.result(outParam);
        } else {
            FaultResult faultResult = new FaultResult();
            faultResult.faultCode = httpReturn.code;
            faultResult.faultString = httpReturn.message;
            responder.fault(faultResult);
        }
        return "";
        //return super.doBusiness(request, null, timeOut, secretKey);
    }

    @Override
    public String doBusiness(InParamPTPostmanLogin request, IResponder responder, int timeOut, String secretKey) throws DcdzSystemException {
        Map<String, Object> data = new TreeMap<>();
        data.put("keyCabinetId", request.TerminalNo);
        data.put("loginCode", request.PostmanID);
        data.put("password", EncryptHelper.encrypt(request.Password));

        String ret = HttpHelper.postJSON("verifyLogin.json", packSendData(data));
        log.info("[MHC] 仓管员登录--" + ret);
        if (!ret.equals("!")) {
            OutParamPTPostmanLogin outParam = new OutParamPTPostmanLogin();
            JsonHttpReturn httpReturn = JsonUtils.toBean(ret, JsonHttpReturn.class);
            if (responder != null) {
                if (httpReturn.success) {
                    outParam.PostmanName = parseJson(httpReturn.data, "nickName");
                    outParam.PostmanID = parseJson(httpReturn.data, "staffId");
                    responder.result(outParam);
                } else {
                    FaultResult faultResult = new FaultResult();
                    faultResult.faultCode = httpReturn.code;
                    faultResult.faultString = httpReturn.message;
                    responder.fault(faultResult);
                }
            }
        }
        return "";
    }

    @Override
    public String doBusiness(InParamPTPackageDetail request, IResponder responder, int timeOut, String secretKey) throws DcdzSystemException {
        log.debug("[MHC] boxName=" + request.boxName + ",code=" + request.DynamicCode);
        Map<String, Object> data = new TreeMap<>();
        data.put("boxNum", Integer.valueOf(request.boxName));
        data.put("boxPassword", EncryptHelper.encrypt(request.DynamicCode));
        data.put("keyCabinetId", request.TerminalNo);
        data.put("keyId", request.PackageID);
        data.put("staffId", Long.valueOf(request.PostmanID));
        data.put("applyInterBoxDate", DateUtils.datetimeToString(request.OccurTime));

        String ret = HttpHelper.postJSON("beforeInterBoxKey.json", packSendData(data));
        log.info("[MHC] 钥匙详细信息查询--" + ret);
        JsonHttpReturn httpReturn = JsonUtils.toBean(ret, JsonHttpReturn.class);
        if (responder != null) {
            if (httpReturn.success) {
                responder.result(httpReturn.data);
            } else {
                FaultResult faultResult = new FaultResult();
                faultResult.faultCode = httpReturn.code;
                faultResult.faultString = httpReturn.message;
                responder.fault(faultResult);
            }
        }
        return "";
    }

    @Override
    public String doBusiness(InParamPTDeliveryPackage request, IResponder responder, int timeOut, String secretKey) throws DcdzSystemException {
        Map<String, Object> data = new TreeMap<>();
        data.put("keyCabinetId", request.TerminalNo);
        data.put("staffId", Long.valueOf(request.PostmanID));
        data.put("keyId", request.PackageID);
        data.put("boxPassword", EncryptHelper.encrypt(request.OpenBoxKey));
        data.put("boxNum", Integer.valueOf(request.BoxNo));
        data.put("videoUrl", "www.baidu.com");
        data.put("interBoxDate", DateUtils.datetimeToString(request.StoredTime));

        String ret = HttpHelper.postJSON("afterInterBoxKey.json", packSendData(data));
        log.info("[MHC] 存件记录上传--" + ret);
        OutParamPTDeliveryPackage outParam = new OutParamPTDeliveryPackage();
        JsonHttpReturn httpReturn = JsonUtils.toBean(ret, JsonHttpReturn.class);
        if (responder != null) {
            if (httpReturn.success) {
                outParam.BoxNo = request.BoxNo;
                outParam.OpenBoxKey = request.OpenBoxKey;
                outParam.PackageID = request.PackageID;
                outParam.ServerTime = request.StoredTime;
                responder.result(PacketUtils.buildLocalJsonResult(outParam));
            } else {
                FaultResult faultResult = new FaultResult();
                faultResult.faultCode = httpReturn.code;
                faultResult.faultString = httpReturn.message;
                responder.fault(faultResult);
            }
        }
        return "";
    }

    @Override
    public String doBusiness(InParamPTPickupPackage request, IResponder responder, int timeOut, String secretKey) throws DcdzSystemException {

        if (!Objects.equals("0", request.TakedWay)) {
            Map<String, Object> data = new TreeMap<>();
            data.put("keyCabinetId", request.TerminalNo);
            data.put("staffId", Long.valueOf(request.PostmanID));
            data.put("keyId", request.PackageID);
            data.put("boxNum", Integer.valueOf(request.BoxNo));
            data.put("videoUrl", "www.baidu.com");
            data.put("takeOutBoxKeyDate", DateUtils.datetimeToString(request.OccurTime));

            String ret = HttpHelper.postJSON("afterTakeOutBoxKey.json", packSendData(data));
            log.info("[MHC] 指纹取件记录上传 --" + ret);
            JsonHttpReturn httpReturn = JsonUtils.toBean(ret, JsonHttpReturn.class);
            OutParamPTPickupPackage outParam = new OutParamPTPickupPackage();
            if (responder != null) {
                if (httpReturn.success) {
                    outParam.ServerTime = request.OccurTime;
                    responder.result(PacketUtils.buildLocalJsonResult(outParam));
                } else {
                    FaultResult faultResult = new FaultResult();
                    faultResult.faultCode = httpReturn.code;
                    faultResult.faultString = httpReturn.message;
                    responder.fault(faultResult);
                }
            }
        } else {
            Map<String, Object> data = new TreeMap<>();
            data.put("keyCabinetId", request.TerminalNo);
            data.put("staffId", Long.valueOf(request.PostmanID));
            data.put("keyId", request.PackageID);
            data.put("boxNum", Integer.valueOf(request.BoxNo));
            data.put("videoUrl", "www.baidu.com");
            data.put("takeOutBoxKeyDate", DateUtils.datetimeToString(request.OccurTime));

            String ret = HttpHelper.postJSON("passwordTakeOutBoxKey.json", packSendData(data));
            log.info("[MHC] 密码取件记录上传 --" + ret);
            JsonHttpReturn httpReturn = JsonUtils.toBean(ret, JsonHttpReturn.class);
            OutParamPTPickupPackage outParam = new OutParamPTPickupPackage();
            if (responder != null) {
                if (httpReturn.success) {
                    outParam.ServerTime = request.OccurTime;
                    responder.result(PacketUtils.buildLocalJsonResult(outParam));
                } else {
                    FaultResult faultResult = new FaultResult();
                    faultResult.faultCode = httpReturn.code;
                    faultResult.faultString = httpReturn.message;
                    responder.fault(faultResult);
                }
            }
        }
        return "";
    }

    /**
     * 重置紧急密码
     */
    public String doBusiness(InParamPTRestoreRush request, IResponder responder, int timeOut, String secretKey) throws DcdzSystemException {
        Map<String, Object> data = new TreeMap<>();
        data.put("keyCabinetId", request.TerminalNo);
        data.put("emergencyPassword", EncryptHelper.encrypt(request.EmergenciesPwd));

        String ret = HttpHelper.postJSON("initEmergencyPassword.json", packSendData(data));
        log.info("[MHC] 重置紧急密码--" + ret);
        JsonHttpReturn httpReturn = JsonUtils.toBean(ret, JsonHttpReturn.class);
        OutParamMBVerifyCabinet outParam = new OutParamMBVerifyCabinet();
        if (responder != null) {
            if (httpReturn.success) {
                outParam.EmergenciesPwd = request.EmergenciesPwd;
                responder.result(httpReturn.data);
            } else {
                FaultResult faultResult = new FaultResult();
                faultResult.faultCode = httpReturn.code;
                faultResult.faultString = httpReturn.message;
                responder.fault(faultResult);
            }
        }
        return "";
    }

    /**
     * 指纹上传
     */
    public String doBusiness(InParamMBUploadFingerPrint request, IResponder responder, int timeOut, String secretKey) throws DcdzSystemException {

        Map<String, Object> data = new TreeMap<>();
        data.put("keyCabinetId", request.TerminalNo);
        data.put("warehouseId", Long.valueOf(request.WarehouseId));
        data.put("staffId", Long.valueOf(request.OperID));

        String ret = HttpHelper.postJSON("fingerprintSuccess.json", packSendData(data));
        log.info("[MHC] 指纹录入上传--" + ret);
        JsonHttpReturn httpReturn = JsonUtils.toBean(ret, JsonHttpReturn.class);
        if (responder != null) {
            if (httpReturn.success) {
                responder.result(httpReturn.data);
            } else {
                FaultResult faultResult = new FaultResult();
                faultResult.faultCode = httpReturn.code;
                faultResult.faultString = httpReturn.message;
                responder.fault(faultResult);
            }
        }
        return null;
    }

    /**
     * 应急密码被使用
     */
    public String doBusiness(InParamMBEmergencyPwdUser request, IResponder responder, int timeOut, String secretKey) throws DcdzSystemException {

        Map<String, Object> data = new TreeMap<>();
        data.put("keyCabinetId", request.TerminalNo);
        data.put("emergencyPasswordUsedDate", DateUtils.datetimeToString(DateUtils.nowDate()));

        String ret = HttpHelper.postJSON("emergencyPasswordUsed.json", packSendData(data));

        JsonHttpReturn httpReturn = JsonUtils.toBean(ret, JsonHttpReturn.class);
        if (responder != null) {
            if (httpReturn.success) {
                responder.result(httpReturn.data);
            } else {
                FaultResult faultResult = new FaultResult();
                faultResult.faultCode = httpReturn.code;
                faultResult.faultString = httpReturn.message;
                responder.fault(faultResult);
            }
        }
        return null;
    }

    /**
     * 获取七牛token
     */
    public String doBusiness(InParamMBGetToken request, IResponder responder, int timeOut, String secretKey) throws DcdzSystemException {

        Map<String, Object> data = new TreeMap<>();
        data.put("keyCabinetId", request.TerminalNo);

        String ret = HttpHelper.postJSON("getToken.json", packSendData(data));
        JsonHttpReturn httpReturn = JsonUtils.toBean(ret, JsonHttpReturn.class);
        if (responder != null) {
            if (httpReturn.success) {
                OutParamMBGetToken outParam = new OutParamMBGetToken();
                outParam.Token = parseJson(httpReturn.data, "token");
                outParam.EffectiveTime = parseJson(httpReturn.data, "activityTime");
                responder.result(outParam);
            } else {
                FaultResult faultResult = new FaultResult();
                faultResult.faultCode = httpReturn.code;
                faultResult.faultString = httpReturn.message;
                responder.fault(faultResult);
            }
        }
        return null;
    }

    /**
     * 钥匙取消入库
     */
    public String doBusiness(InParamPTCancelDelivery request, IResponder responder, int timeOut, String secretKey) throws DcdzSystemException {

        Map<String, Object> data = new TreeMap<>();
        data.put("keyCabinetId", request.TerminalNo);
        data.put("keyId", request.KeyId);

        String ret = HttpHelper.postJSON("cancelBeforeInterBoxKey.json", packSendData(data));
        JsonHttpReturn httpReturn = JsonUtils.toBean(ret, JsonHttpReturn.class);
        if (responder != null) {
            if (httpReturn.success) {
                responder.result(httpReturn.data);
            } else {
                FaultResult faultResult = new FaultResult();
                faultResult.faultCode = httpReturn.code;
                faultResult.faultString = httpReturn.message;
                responder.fault(faultResult);
            }
        }
        return "";
    }

    /**
     * 获取出库钥匙信息
     */
    public String doBusiness(InParamPTObtainPickupList request, IResponder responder, int timeOut, String secretKey) throws DcdzSystemException {

        Map<String, Object> data = new TreeMap<>();
        data.put("keyCabinetId", request.TerminalNo);
        data.put("staffId", Long.valueOf(request.OperID));

        String ret = HttpHelper.postJSON("canOutBoxKeyList.json", packSendData(data));
        JsonHttpReturn httpReturn = JsonUtils.toBean(ret, JsonHttpReturn.class);
        if (responder != null) {
            if (httpReturn.success) {
                List<OutParamPTObtainPickupList> outParams = JsonUtils.toList(httpReturn.data, OutParamPTObtainPickupList.class);
                responder.result(outParams);
            } else {
                FaultResult faultResult = new FaultResult();
                faultResult.faultCode = httpReturn.code;
                faultResult.faultString = httpReturn.message;
                responder.fault(faultResult);
            }
        }
        return "";
    }

    /**
     * 取件开箱前调用此接口
     */
    public String doBusiness(InParamPTPickupBeforeOpen request, IResponder responder, int timeOut, String secretKey) throws DcdzSystemException {
        ArrayList list = new ArrayList();
        for (InParamPTPickupPackage inParam : request.pickupPackageList) {
            Map<String, Object> data = new TreeMap<>();
            data.put("keyCabinetId", request.TerminalNo);
            data.put("staffId", Long.valueOf(inParam.PostmanID));
            data.put("keyId", inParam.PackageID);
            data.put("boxNum", Integer.valueOf(inParam.BoxNo));
            data.put("applyTakeOutBoxKeyDate", DateUtils.datetimeToString(inParam.OccurTime));
            list.add(data);
        }
        String ret = HttpHelper.postJSON("applyTakeOutBoxKey.json", packSendData(list));
        JsonHttpReturn httpReturn = JsonUtils.toBean(ret, JsonHttpReturn.class);
        if (responder != null) {
            if (httpReturn.success) {
                responder.result(httpReturn.data);
            } else {
                FaultResult faultResult = new FaultResult();
                faultResult.faultCode = httpReturn.code;
                faultResult.faultString = httpReturn.message;
                responder.fault(faultResult);
            }
        }
        return "";
    }

    /**
     * 取件开门失败 退回取件状态
     */
    public String doBusiness(InParamPTPickupFailed request, IResponder responder, int timeOut, String secretKey) throws DcdzSystemException {
        Map<String, Object> data = new TreeMap<>();
        data.put("keyCabinetId", request.terminalNo);
        data.put("keyId", request.keyId);

        String ret = HttpHelper.postJSON("cancelBeforeOutBoxKey.json", packSendData(data));
        JsonHttpReturn httpReturn = JsonUtils.toBean(ret, JsonHttpReturn.class);

        if (responder != null) {
            if (httpReturn.success) {
                responder.result(httpReturn.data);
            } else {
                FaultResult faultResult = new FaultResult();
                faultResult.faultCode = httpReturn.code;
                faultResult.faultString = httpReturn.message;
                responder.fault(faultResult);
            }
        }
        return "";
    }


    private String generateSign(String appsecret, Map<String, Object> sortedParamMap) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String paramJson = new Gson().toJson(sortedParamMap);
        String digest = paramJson + appsecret;
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        BASE64Encoder base64En = new BASE64Encoder();
        return base64En.encode(md5.digest(digest.getBytes("utf-8")));
    }

    private String packSendData(Map<String, Object> data) {
        Map<String, Object> SendData = new TreeMap<>();
        SendData.put("appKey", "wms_ysg_api");
        try {
            SendData.put("sign", generateSign(appSecret, data));
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            Log.e("mhc", "[generateSign]" + e.getMessage());
        }
        SendData.put("data", data);
        return JsonUtils.toJSONString(SendData);
    }

    private String packSendData(List list) {
        Map<String, Object> SendData = new TreeMap<>();
        SendData.put("appKey", "wms_ysg_api");
        SendData.put("sign", appSecret);
        SendData.put("data", list);
        return JsonUtils.toJSONString(SendData);
    }

    private String parseJson(String jsonString, String name) {
        return JsonUtils.toJSONObject(jsonString).getString(name);
    }

}
