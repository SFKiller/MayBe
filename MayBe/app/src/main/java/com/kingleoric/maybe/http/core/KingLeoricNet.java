package com.kingleoric.maybe.http.core;

import com.kingleoric.maybe.http.stack.HttpStack;

/**
 * Created by qipu on 2016/9/12.
 */
public final class KingLeoricNet {

    /**
     *  Create a request queue. The count of executors is default.
     * @return
     */
    public static RequestQueue newRequestQueue() {
        return newRequestQueue(RequestQueue.DEFAULT_CORE_NUMS);
    }

    /**
     * Create a request queue.
     * @param executorsNums Executor counts.
     * @return
     */
    public static RequestQueue newRequestQueue(int executorsNums) {
        return newRequestQueue(executorsNums, null);
    }

    /**
     * Create a request queue.
     * @param executorNum Executor counts.
     * @param httpStack The actually executor of the requests
     * @return
     */
    public static RequestQueue newRequestQueue(int executorNum, HttpStack httpStack) {
        RequestQueue queue = new RequestQueue(Math.max(0, executorNum), httpStack);
        queue.start();
        return queue;
    }
}
