package com.kingleoric.maybe.http.core;

import com.kingleoric.maybe.Utils.Logger;
import com.kingleoric.maybe.http.base.Request;
import com.kingleoric.maybe.http.base.Response;
import com.kingleoric.maybe.http.cache.Cache;
import com.kingleoric.maybe.http.cache.LruMemCache;
import com.kingleoric.maybe.http.stack.HttpStack;

import java.util.concurrent.BlockingQueue;

/**
 * Created by qipu on 2016/8/5.
 */

public class NetworkExecutor extends Thread {

    private static final int HTTP_RESPONSE_SUCCESS_CODE = 200;
    /** Request queue*/
    private BlockingQueue<Request<?>> requestQueue;
    /** Request stack*/
    private HttpStack httpStack;
    /** Response dispatcher. Dispatch response to UI thread*/
    private static ResponseDelivery responseDelivery = new ResponseDelivery();
    /** Request cache*/
    private static Cache<String, Response> requestCache = new LruMemCache();
    /**If stop*/
    private boolean isStop = false;

    public NetworkExecutor(BlockingQueue<Request<?>> queue, HttpStack stack) {
        requestQueue = queue;
        httpStack = stack;
    }

    @Override
    public void run() {
        try {
            while (!isStop) {
                final Request<?> request = requestQueue.take();
                if (request.isCanceled()) {
                    Logger.d(this, "Request canceled");
                    continue;
                }

                Response response = null;
                if (isUseCache(request)) {
                    //Get response from cache
                    response = requestCache.get(request.getUrl());
                } else {
                    //Get response from network
                    response = httpStack.performRequest(request);
                    //If the request need cache, cache this response while success
                    if (request.isShouldCache() && isSuccess(response)) {
                        requestCache.put(request.getUrl(), response);
                    }
                }

                //Dispatch response result
                responseDelivery.deliveryResponse(request, response);
            }
        } catch (InterruptedException e) {
            Logger.e(this, "Request dispatcher exist");
        }
    }

    /**
     * Judge if request response success
     * @param response
     * @return
     */
    private boolean isSuccess(Response response) {
        return null != response && HTTP_RESPONSE_SUCCESS_CODE == response.getStatusCode();
    }

    /**
     * Judge if use cache
     * @param request
     * @return
     */
    private boolean isUseCache(Request<?> request) {
        return request.isShouldCache() && null != requestCache.get(request.getUrl());
    }

    /**
     * Quit executor
     */
    public void quit() {
        isStop = true;
        interrupt();
    }
}
