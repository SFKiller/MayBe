package com.kingleoric.maybe.http.stack;

import android.net.http.AndroidHttpClient;

import com.kingleoric.maybe.http.base.Request;
import com.kingleoric.maybe.http.base.Response;
import com.kingleoric.maybe.http.config.HttpClientConfig;

import org.apache.http.client.HttpClient;

/** Use HttpClient for API under 9
 * Created by qipu on 2016/8/23.
 */
public class HttpClientStack implements HttpStack {

    HttpClientConfig config = HttpClientConfig.getConfig();
    HttpClient client = AndroidHttpClient.newInstance(config.userAgent);
    /**
     * Execute http request
     *
     * @param request Request to be executed
     * @return Response of the request
     */
    @Override
    public Response performRequest(Request<?> request) {

        return null;
    }
}
