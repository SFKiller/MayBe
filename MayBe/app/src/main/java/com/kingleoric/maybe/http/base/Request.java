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

    private static final String AND = "&";
    private static final String EQ = "=";
    private static final String HTTPS = "https";

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
    /** Default Content-type*/
    private static final String DEFAULT_HEADER_CONTENT_TYPE = "Content-type";
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
     * Add header param
     * @param name
     * @param value
     */
    public void addHeader(String name, String value) {
        headers.put(name, value);
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
     * @return
     */
    public RequestListener<T> getRequestListener() {
        return requestListener;
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

    /**
     * @return if the request url is a https request
     */
    public boolean isHttps() {
        return url.startsWith(HTTPS);
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
                encodingParams.append(EQ);
                encodingParams.append(URLEncoder.encode(entry.getValue(), paramEncodingFormat));
                encodingParams.append(AND);
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
     * @return
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((null == headers) ? 0 : headers.hashCode());
        result = prime * result + ((null == httpMethod) ? 0 : httpMethod.hashCode());
        result = prime * result + ((null == bodyParams) ? 0 : bodyParams.hashCode());
        result = prime * result + ((null == priority) ? 0 : priority.hashCode());
        result = prime * result + ((shouldCache ? 1231 : 1237));
        result = prime * result + ((null == url) ? 0 : url.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (null == object) {
            return false;
        }
        if (getClass() != object.getClass()) {
            return false;
        }
        Request<?> another = (Request<?>) object;
        if (null == headers) {
            if (null != another.headers) {
                return false;
            }
        } else if (!headers.equals(another.headers)) {
            return false;
        }

        if (httpMethod != another.httpMethod) {
            return false;
        }

        if (null == bodyParams) {
            if (null != another.bodyParams) {
                return false;
            }
        } else if (!bodyParams.equals(another.bodyParams)) {
            return false;
        }

        if (priority != another.priority) {
            return false;
        }

        if (!(shouldCache && another.shouldCache)) {
            return false;
        }

        if (null == url) {
            if (null != another.url) {
                return false;
            }
        } else if (!url.equals(another.url)) {
            return false;
        }

        return true;
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
