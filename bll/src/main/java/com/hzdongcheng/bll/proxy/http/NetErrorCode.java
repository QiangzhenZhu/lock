package com.hzdongcheng.bll.proxy.http;

import com.hzdongcheng.components.toolkits.exception.error.ErrorCode;
import com.hzdongcheng.components.toolkits.exception.error.ErrorTitle;

/**
 * Created by Peace on 2017/9/15.
 * Modify by zxy on 2017/9/16.
 */

public class NetErrorCode extends ErrorCode {
    public static int SUCCESS = 0;
    public final static int ERR_NETWORK_NETWORKLAYER = 40000;
    public final static int ERR_NETWORK_OPENLINK = 40011;
    public final static int ERR_NETWORK_SENDMSGFAIL = 40021;//发送消息失败
    public final static int ERR_NETWORK_SENDMSGTIMEOUT = 40022;//发送消息超时

    static {
        ErrorTitle.putErrorTitle(ERR_NETWORK_NETWORKLAYER, "network layer error");
        ErrorTitle.putErrorTitle(ERR_NETWORK_OPENLINK, "network open failed");
        ErrorTitle.putErrorTitle(ERR_NETWORK_SENDMSGFAIL, "Send message failed");
        ErrorTitle.putErrorTitle(ERR_NETWORK_SENDMSGTIMEOUT, "Send message timeout");
    }
}


