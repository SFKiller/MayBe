package com.kingleoric.maybe.http.config;

import org.apache.http.conn.ssl.SSLSocketFactory;

/**
 * Created by qipu on 2016/8/23.
 */

public class HttpClientConfig extends HttpConfig {
    private static HttpClientConfig sSonfig = new HttpClientConfig();

    SSLSocketFactory mSslSocketFactory;

    private HttpClientConfig() {

    }

    public static HttpClientConfig getConfig() {
        return sSonfig;
    }

    public void setHttpsConfig(SSLSocketFactory sslSocketFactory) {
        this.mSslSocketFactory = sslSocketFactory;
    }

    public SSLSocketFactory getSocketFactory() {
        return mSslSocketFactory;
    }
}
