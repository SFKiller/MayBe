package com.kingleoric.maybe.http.core;

import android.os.Handler;
import android.os.Looper;

import com.kingleoric.maybe.http.base.Request;
import com.kingleoric.maybe.http.base.Response;

import java.util.concurrent.Executor;

/** Delivery the request response to UI thread
 * @author KingLeoric
 * @date 2016/08/05
 */
public class ResponseDelivery implements Executor {

    /** Main thread handler*/
    Handler responseHandler = new Handler(Looper.getMainLooper());

    /**
     *  Handle reqest result in UI thread
     * @param request
     * @param response
     */
    public void deliveryResponse(final Request<?> request, final Response response) {
        Runnable responseRunnbale = new Runnable() {
            @Override
            public void run() {
                request.deliveryResponse(response);
            }
        };

        execute(responseRunnbale);
    }

    @Override
    public void execute(Runnable runnable) {
        responseHandler.post(runnable);
    }
}
