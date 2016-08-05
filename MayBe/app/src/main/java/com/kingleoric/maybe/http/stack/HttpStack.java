package com.kingleoric.maybe.http.stack;

import com.kingleoric.maybe.http.base.Request;
import com.kingleoric.maybe.http.base.Response;

/** Interface for execute http request
 * @author KingLeoric
 * @date 2016/8/5
 */
public interface HttpStack {

    /**
     * Execute http request
     * @param request Request to be executed
     * @return Response of the request
     */
    public Response performRequest(Request<?> request);
}
