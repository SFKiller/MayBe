package com.kingleoric.maybe.http.stack;

import android.os.Build;

/**
 * Created by qipu on 2016/8/5.
 */

public final class HttpStackFactory {

    /**
     * Create different http stack based on SDK version.
     * @return
     */
    public static HttpStack createHttpStack() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            return new HttpUrlConnStack();
        } else {
            //return new HttpClientStack();
            return null;
        }
    }
}
