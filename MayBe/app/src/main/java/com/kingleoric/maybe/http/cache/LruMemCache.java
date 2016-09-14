package com.kingleoric.maybe.http.cache;

import android.support.v4.util.LruCache;

import com.kingleoric.maybe.http.base.Response;

/** Cache the request in the memory
 * Created by qipu on 2016/8/8.
 */
public class LruMemCache implements Cache<String, Response> {
    private LruCache<String, Response> mResponseCache;

    public LruMemCache() {
        /** Calculate the max memory could use*/
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

        /** Get 1 / 8 Max memory as http cache size*/
        final int cacheSize = maxMemory / 8;
        mResponseCache = new LruCache<String, Response>(cacheSize) {

            @Override
            protected int sizeOf(String key, Response response) {
                return response.rawData.length / 1024;
            }
        };

    }

    @Override
    public Response get(String key) {
        return mResponseCache.get(key);
    }

    @Override
    public void put(String key, Response response) {
        mResponseCache.put(key, response);
    }

    @Override
    public void remove(String key) {
        mResponseCache.remove(key);
    }
}
