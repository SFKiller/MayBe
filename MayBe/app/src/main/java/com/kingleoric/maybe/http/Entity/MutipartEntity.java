package com.kingleoric.maybe.http.Entity;

import org.apache.http.Header;
import org.apache.http.HttpEntity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by qipu on 2016/9/12.
 */

public class MutipartEntity implements HttpEntity {

    private final static char[] MUTIPART_CHARS = "-_1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    private final String NEW_LINE_STR = "\r\n";
    private final String CONTENT_TYPE = "Content-type";
    private final String CONTENT_DISPOSITION = "Content-Disposition";
    private final String TYPE_TEXT_CHARSET = "text/plain; charset=UTF-8";
    private final String TYPE_OCTET_STREAM = "application/octet-stream";
    private final byte[] BINARY_ENCODING = "Content-Transfer-Encoding: binary\r\n\r\n".getBytes();
    private final byte[] BIT_ENCODING = "Content-Transfer-Encoding: 8bit\r\n\r\n".getBytes();

    @Override
    public boolean isRepeatable() {
        return false;
    }

    @Override
    public boolean isChunked() {
        return false;
    }

    @Override
    public long getContentLength() {
        return 0;
    }

    @Override
    public Header getContentType() {
        return null;
    }

    @Override
    public Header getContentEncoding() {
        return null;
    }

    @Override
    public InputStream getContent() throws IOException, IllegalStateException {
        return null;
    }

    @Override
    public void writeTo(OutputStream outputStream) throws IOException {

    }

    @Override
    public boolean isStreaming() {
        return false;
    }

    @Override
    public void consumeContent() throws IOException {

    }
}
