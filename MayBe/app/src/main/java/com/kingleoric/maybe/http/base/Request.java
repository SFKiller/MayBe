package com.kingleoric.maybe.http.base;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/** Http request
 * @author KingLeoric
 * @date 2016/8/5.
 */
public abstract class Request<T> implements Comparable<Request<T>> {

    /**
     * Http method enum: GET, POST, PUT, DELETE
     */
    public static enum HttpMethod {
        GET("GET"),
        POST("POST"),
        PUT("PUT"),
        DELETE("DELETE");

        private String httpMethod = "";

        private HttpMethod(String method) {
            httpMethod = method;
        }

        @Override
        public String toString() {
            return httpMethod;
        }
    }

    /**
     * Priority enum
     */
    public static enum Priority {
        LOW,
        NORMAL,
        HIGH,
        IMMEDIATE
    }

    /**Default encoding for POST and PUT paramters*/
    private static final String DEFAULT_PARAMS_ENCODING_FORMAT = "UTF-8";
    /**Request serial number*/
    protected int serialNum = 0;
    /**Default priority*/
    protected Priority priority = Priority.NORMAL;
    /**If cancel the request*/
    protected boolean isCancel = false;
    /**If cache the request*/
    private boolean shouldCache = true;
    /**Listener for request*/
    protected RequestListener<T> requestListener;
    /**Request url*/
    private String url = "";
    /**Request method*/
    HttpMethod httpMethod = HttpMethod.GET;
    /**Request header*/
    private Map<String, String> headers = new HashMap<String, String>();
    /**Request body*/
    private Map<String, String> bodyParams = new HashMap<String, String>();

    public Request(HttpMethod method, String requestUrl, RequestListener<T> listener) {
        httpMethod = method;
        url = requestUrl;
        requestListener = listener;
    }

    /**
     * Abstract method for parsing result of request
     * @param response Original result of request
     * @return
     */
    public abstract T parseResponse(Response response);

    /**
     * Handle response. This method is running in UI thread
     * @param response
     */
    public final void deliveryResponse(Response response) {
        T result = parseResponse(response);
        if (null != requestListener) {
            int statusCode = null != response ? response.getStatusCode() : -1;
            String msg = null != response ? response.getMessage() : "unknow error";
            requestListener.onComplete(statusCode, result, msg);
        }
    }

    /**
     * Set url
     * @param requestUrl
     */
    public void setUrl(String requestUrl) {
        this.url = requestUrl;
    }

    /**
     * Get request url
     * @return
     */
    public String getUrl() {
        return this.url;
    }

    /**
     * Set if cache the request
     * @param cache
     */
    public void setShouldCache(boolean cache) {
        this.shouldCache = cache;
    }
    /**
     *
     */
    public boolean isShouldCache() {
        return shouldCache;
    }
    /** Get serial number
     * @return
     */
    public int getSerialNum() {
        return this.serialNum;
    }

    /**
     * Set Serial number
     * @param num
     */
    public void setSerialNum(int num) {
        this.serialNum = num;
    }

    /** Get param encoding format
     * @return
     */
    protected String getParamsEncodingFormat() {
        return DEFAULT_PARAMS_ENCODING_FORMAT;
    }

    /** Get body content type
     * @return
     */
    public String getBodyContentType() {
        return "application/x-www-form-urlencoded; charset=" + getParamsEncodingFormat();
    }

    /** Get http method
     * @return
     */
    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    /** Get header
     * @return
     */
    public Map<String, String> getHeader() {
        return headers;
    }

    /** Get priority
     * @return
     */
    public Priority getPriority() {
        return priority;
    }

    /** Get body params
     * @return
     */
    public Map<String, String> getBodyParam() {
        return bodyParams;
    }

    /**
     * Cancel request
     */
    public void cancel() {
        isCancel = true;
    }

    /**
     * @return
     */
    public boolean isCanceled() {
        return isCancel;
    }

    /** Get body parameter's bytes for GET and PUT
     * @return
     */
    public byte[] getBodyBytes() {
        Map<String, String> params = getBodyParam();
        if (null != params && params.size() > 0) {
            return encodeBodyParameters(params, getParamsEncodingFormat());
        } else {
            return null;
        }
    }

    /** Convert the params into URL parameter format
     * @param params Params to transfer
     * @param paramEncodingFormat Encoding format
     * @return Encoded params
     */
    private byte[] encodeBodyParameters(Map<String, String> params, String paramEncodingFormat) {
        StringBuffer encodingParams = new StringBuffer();
        try {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                encodingParams.append(URLEncoder.encode(entry.getKey(), paramEncodingFormat));
                encodingParams.append('=');
                encodingParams.append(URLEncoder.encode(entry.getValue(), paramEncodingFormat));
                encodingParams.append('&');
            }
            return encodingParams.toString().getBytes(paramEncodingFormat);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Encoding not support: " + paramEncodingFormat, e);
        }
    }

    /**
     * Sort requests by its priority and serial number
     * @param request
     * @return
     */
    @Override
    public int compareTo(Request<T> request) {
        Priority thisPriority = this.getPriority();
        Priority anotherPriority = request.getPriority();
        /** Compare with serial number if priority is same*/
        return thisPriority.equals(anotherPriority) ? (this.getSerialNum() - request.getSerialNum())
                : (thisPriority.ordinal() - anotherPriority.ordinal());
    }

    /**
     * Listener for http request
     * @param <T> request type
     */
    public static interface RequestListener<T> {
        /**
         * Callback when request complete
         * @param statusCode Status code
         * @param response Response result
         * @param errMessage Error message
         */
        public void onComplete(int statusCode, T response, String errMessage);
    }
}
