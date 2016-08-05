package com.kingleoric.maybe.http.core;

import com.kingleoric.maybe.Utils.Logger;
import com.kingleoric.maybe.http.base.Request;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/** Request queue, FIFO based and request also can be execute based on priority.(Thread safe)
 * @author KingLeoric
 * @date 2016/8/5
 */
public final class RequestQueue {

    /**Request queue*/
    private BlockingQueue<Request<?>> requestQueue = new PriorityBlockingQueue<Request<?>>();
    /**Serial number generator*/
    private AtomicInteger serialNumberGenerator = new AtomicInteger(0);

    /** Default core numbers*/
    private static final int DEFAULT_CORE_NUMS = Runtime.getRuntime().availableProcessors() + 1;
    /**Dispatch thread number*/
    private int dispatchThreadNum = DEFAULT_CORE_NUMS;
    /**Executing threads for request*/
    private NetworkExecutor[] dispatchers = null;
    /**The real executor of the request*/
    private HttpStack httpStack;

    protected RequestQueue(int coreNums, HttpStack stack) {
        dispatchThreadNum = coreNums;
        httpStack = null != stack ? stack : HttpStackFatcory.createHttpStack();
    }

    /**
     * Start network executor to dispatch request
     */
    private final void startNetworkExecutors() {
        dispatchers = new NetworkExecutor[dispatchThreadNum];
        for (int i = 0; i < dispatchThreadNum; i++) {
            dispatchers[i] = new NetworkExecutor(requestQueue, httpStack);
            dispatchers[i].start();
        }
    }

    /**
     * Start
     */
    public void start() {
        stop();
        startNetworkExecutors();
    }

    /**
     * Stop all request
     */
    public void stop() {
        if (null != dispatchers && dispatchers.length > 0) {
            for (int i = 0; i < dispatchers.length; i++) {
                dispatchers[i].quit();
            }
        }
    }

    /**
     * Add a new request into request queue.
     * @param request
     */
    public void addRequest(Request<?> request) {
        if (!requestQueue.contains(request)) {
            request.setSerialNum(this.generateSerialNum());
            requestQueue.add(request);
        } else {
            Logger.d(this, "Request is already in the queue!");
        }
    }

    /**
     * Clear request queue
     */
    public void clear() {
        requestQueue.clear();
    }

    /**
     * Get all requests
     * @return
     */
    public BlockingQueue<Request<?>> getAllRequests() {
        return requestQueue;
    }

    /**
     * Generate serial number for each request
     * @return
     */
    private int generateSerialNum() {
        return serialNumberGenerator.incrementAndGet();
    }
}
