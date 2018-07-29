package com.hzdongcheng.bll;

import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.common.JsonPacket;
import com.hzdongcheng.bll.proxy.Proxy4Mhcsoft;
import com.hzdongcheng.bll.proxy.ProxyManager;
import com.hzdongcheng.bll.websocket.SocketClient;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;

public class MHCContext {

    public static LocalWrapper localContext = new LocalWrapper();
    public static RemoteWrapper remoteContext = new RemoteWrapper();

    public static void initContext() {
        DBSContext.localContext = localContext;
        DBSContext.remoteContext = remoteContext;

        ProxyManager.getInstance().setProxy(new Proxy4Mhcsoft());
        SocketClient.setOnInvokeCallback(new SocketClient.PredicateServiceType() {

            @Override
            public Class<?> predicate(String serviceName) {
                String type = "com.hzdongcheng.bll.dto." + serviceName;
                try {
                    return Class.forName(type);
                } catch (ClassNotFoundException e) {
                    return null;
                }
            }
        });
    }

    // 处理服务器推送消息-删除指纹
    public Runnable deleteFinger = null;

}
