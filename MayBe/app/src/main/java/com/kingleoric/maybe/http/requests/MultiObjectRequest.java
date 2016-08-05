package com.kingleoric.maybe.http.requests;

import com.kingleoric.maybe.Utils.Logger;
import com.kingleoric.maybe.http.base.Request;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/** Multipart request(only for POST), can transfer multi type parameters,
 *  like text, file and so on, but it has a limit size of file to avoid
 *  OOM.
 *  @author KingLeoric
 *  @date 2016/8/5
 */
public class MultipartRequest extends Request {

    MultipartEntity multipartEntity = new MultipartEntity();

    public MultipartRequest(String requestUrl, RequestListener listener) {
        super(HttpMethod.POST, requestUrl, listener);
    }

    public MultipartEntity getMultipartEntity() {
        return multipartEntity;
    }

    /**
     * Get body content type
     * @return
     */
    public String getBodyContentType() {
        return multipartEntity.getContentType.getValue();
    }

    /**
     * Override getBodyBytes. See {@link com.kingleoric.maybe.http.base.Request}
     * @return
     */
    @Override
    public byte[] getBodyBytes() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            multipartEntity.writeTo(baos);
        } catch (IOException e) {
            Logger.e(this, "IOException writing to ByteArrayOutputStream");
        }
        return baos.toByteArray();
    }

    /**
     * Convert multipart response result into String
     * @param response Original result of request
     * @return
     */
    @Override
    public String parseResponse(Response response) {
        if (null != response && null != response.getRawData()) {
            return new String(response.getRawData());
        }
        return "";
    }
}
