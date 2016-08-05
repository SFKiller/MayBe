package com.kingleoric.maybe.http.requests;

import com.kingleoric.maybe.http.base.Request;

/** For the response result is String
 * @author KingLeoric
 * @date 2016/8/5
 */
public class StringRequst extends Request<String> {

    public StringRequst(HttpMethod method, String requestUrl, RequestListener listener) {
        super(method, requestUrl, listener);
    }

    /**
     * Convert response into String
     * @param response Original result of request
     * @return
     */
    @Override
    public String parseResponse(Response response) {
        return new String(response.getRawData());
    }
}
