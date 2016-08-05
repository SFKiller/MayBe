package com.kingleoric.maybe.http.stack;

import com.kingleoric.maybe.http.base.Request;
import com.kingleoric.maybe.http.base.Response;

/** Use HttpURLConnection execute http stack
 * @author KingLeoric
 * @date 2016/8/5
 */
public class HttpUrlConnStack implements HttpStack {

    @Override
    public Response performRequest(Request<?> request) {
        return null;
    }
}
