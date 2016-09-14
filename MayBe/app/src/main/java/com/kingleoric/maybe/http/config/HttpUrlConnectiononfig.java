package com.kingleoric.maybe.http.config;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;

/**
 * Created by qipu on 2016/8/23.
 */

public class HttpUrlConnectiononfig extends HttpConfig {

    private static HttpUrlConnectiononfig sConfig = new HttpUrlConnectiononfig();

    private SSLSocketFactory sslSocketFactory = null;
    private HostnameVerifier hostnameVerifier = null;

    private HttpUrlConnectiononfig() {

    }

    public static HttpUrlConnectiononfig getsConfig() {
        return sConfig;
    }

    public void setHttpsConfig(SSLSocketFactory socketFactory, HostnameVerifier nameVerifier) {
        sslSocketFactory = socketFactory;
        hostnameVerifier = nameVerifier;
    }

    public SSLSocketFactory getSocketFactory() {
        return sslSocketFactory;
    }
}
