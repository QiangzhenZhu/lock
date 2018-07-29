package com.hzdongcheng.bll.proxy.http;


import com.google.gson.GsonBuilder;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import com.hzdongcheng.components.toolkits.utils.Log4jUtils;
import com.hzdongcheng.components.toolkits.utils.StringUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.CertificatePinner;
import okhttp3.CipherSuite;
import okhttp3.ConnectionSpec;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.TlsVersion;

/**
 * Created by zhengxy on 2017/9/16.
 */

public class HttpHelper {
    private static String Url = "https://wms.haimaiche.net/keyCabinet/";
    static ConnectionSpec spec = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
            .tlsVersions(TlsVersion.TLS_1_2)
            .cipherSuites(
                    CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
                    CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
                    CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256)
            .build();
    static Log4jUtils log = Log4jUtils.createInstanse(HttpHelper.class);


    static OkHttpClient httpClient = new OkHttpClient.Builder()
            .certificatePinner(new CertificatePinner.Builder()
                    .add("https://wms.haimaiche.net", "sha1/wSjO2uiGmp73BbCxR0yx+m78oJE=")
                    .build())
            .build();

    //static OkHttpClient httpClient = getUnsafeOkHttpClient();

    private static String executePost(String url, RequestBody requestBody) throws DcdzSystemException {
        Request request = new Request.Builder()
                .url(Url + url)
                .post(requestBody)
                .build();
        try {
            Response response = httpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            }
            throw new DcdzSystemException(NetErrorCode.ERR_NETWORK_SENDMSGFAIL, response.code() + response.message());
        } catch (IOException e) {
            throw new DcdzSystemException(NetErrorCode.ERR_NETWORK_NETWORKLAYER);
        }
    }

    private static final Charset charset = Charset.forName("utf8");

    public static String post(String url, String data, String no, String action) throws DcdzSystemException {
        if (StringUtils.isEmpty(action)) {
            action = "";
        }
        RequestBody requestBody = new FormBody.Builder(charset)
                .add("TERMINALNO", no)
                .add("RECDATA", data)
                .add("ACTION", action)
                .build();
        return executePost(url, requestBody);
    }

    public static String post(String url, Map<String, String> data) throws DcdzSystemException {

        FormBody.Builder builder = new FormBody.Builder(charset);
        for (Map.Entry<String, String> entry : data.entrySet()) {
            builder.add(entry.getKey(), entry.getValue());
        }
        log.info("[Network info]:<JsonResult_info>" + builder.toString());
        RequestBody requestBody = builder.build();
        return executePost(url, requestBody);
    }

    static final MediaType JSON = MediaType.parse("application/json;charset=utf-8");

    public static String postJSON(String url, String json) throws DcdzSystemException {
        RequestBody requestBody = RequestBody.create(JSON, json);
        return executePost(url, requestBody);
    }

    public static String get(String url) throws DcdzSystemException {
        Request request = new Request.Builder().url(url).build();
        try {
            Response response = httpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            }
            throw new DcdzSystemException(NetErrorCode.ERR_NETWORK_NETWORKLAYER);
        } catch (IOException e) {
            throw new DcdzSystemException(NetErrorCode.ERR_NETWORK_NETWORKLAYER);
        }
    }

    private static OkHttpClient getUnsafeOkHttpClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            OkHttpClient okHttpClient = builder.build();
            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
