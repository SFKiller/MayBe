package com.kingleoric.maybe.http.base;

import org.apache.http.HttpEntity;
import org.apache.http.ProtocolVersion;
import org.apache.http.ReasonPhraseCatalog;
import org.apache.http.StatusLine;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Locale;

/** Request result. Storage the result in rawData
 * @author KingLeoric
 * @date 2016/8/5
 */
public class Response extends BasicHttpResponse {

    public byte[] rawData = new byte[0];

    public Response(StatusLine statusline, ReasonPhraseCatalog catalog, Locale locale) {
        super(statusline, catalog, locale);
    }

    public Response(StatusLine statusline) {
        super(statusline);
    }

    public Response(ProtocolVersion ver, int code, String reason) {
        super(ver, code, reason);
    }

    @Override
    public void setEntity(HttpEntity entity) {
        super.setEntity(entity);
        rawData = entityToBytes(getEntity());
    }

    /**
     * Get response data
     * @return
     */
    public byte[] getRawData() {
        return rawData;
    }

    /**
     * Get status code
     * @return
     */
    public int getStatusCode() {
        return getStatusLine().getStatusCode();
    }

    /**
     * Get response message
     * @return
     */
    public String getMessage() {
        return getStatusLine().getReasonPhrase();
    }

    /**
     * Convert response data to bytes
     * @param entity
     * @return
     */
    private byte[] entityToBytes(HttpEntity entity) {
        try {
            return EntityUtils.toByteArray(entity);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }
}
