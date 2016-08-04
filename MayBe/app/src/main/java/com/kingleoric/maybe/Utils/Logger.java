package com.kingleoric.maybe.Utils;

import android.util.Log;

/** A easy log util. Wrapper for {@link android.util.Log}
 * @author KingLeoric
 * @date 2016/8/4.
 */
public class Logger {

    /**Log switch*/
    public static final boolean DEBUGABLE = true;

    /**Main log tag*/
    private static final String MAIN_TAG = "KINGLEORIC";

    /**
     *  Wrapper for {@link android.util.Log} i;
     * @param tag Used to identify the source of a log message.  It usually identifies
     *            the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static void i(Object tag, Object msg) {
        if (null != tag && null != tag.getClass() && null != msg) {
            String subTag = tag instanceof String ? String.valueOf(tag) : tag.getClass().getSimpleName();
            String message = String.valueOf(msg);
            if (DEBUGABLE) {
                Log.i(MAIN_TAG, "[ " + subTag + " ] : " + message);
            }
        }
    }

    /**
     *  Wrapper for {@link android.util.Log} d;
     * @param tag Used to identify the source of a log message.  It usually identifies
     *            the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static void d(Object tag, Object msg) {
        if (null != tag && null != tag.getClass() && null != msg) {
            String subTag = tag instanceof String ? String.valueOf(tag) : tag.getClass().getSimpleName();
            String message = String.valueOf(msg);
            if (DEBUGABLE) {
                Log.d(MAIN_TAG, "[ " + subTag + " ] : " + message);
            }
        }
    }

    /**
     *  Wrapper for {@link android.util.Log} v;
     * @param tag Used to identify the source of a log message.  It usually identifies
     *            the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static void v(Object tag, Object msg) {
        if (null != tag && null != tag.getClass() && null != msg) {
            String subTag = tag instanceof String ? String.valueOf(tag) : tag.getClass().getSimpleName();
            String message = String.valueOf(msg);
            if (DEBUGABLE) {
                Log.v(MAIN_TAG, "[ " + subTag + " ] : " + message);
            }
        }
    }

    /**
     *  Wrapper for {@link android.util.Log} w;
     * @param tag Used to identify the source of a log message.  It usually identifies
     *            the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static void w(Object tag, Object msg) {
        if (null != tag && null != tag.getClass() && null != msg) {
            String subTag = tag instanceof String ? String.valueOf(tag) : tag.getClass().getSimpleName();
            String message = String.valueOf(msg);
            if (DEBUGABLE) {
                Log.w(MAIN_TAG, "[ " + subTag + " ] : " + message);
            }
        }
    }

    /**
     *  Wrapper for {@link android.util.Log} e;
     * @param tag Used to identify the source of a log message.  It usually identifies
     *            the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static void e(Object tag, Object msg) {
        if (null != tag && null != tag.getClass() && null != msg) {
            String subTag = tag instanceof String ? String.valueOf(tag) : tag.getClass().getSimpleName();
            String message = String.valueOf(msg);
            if (DEBUGABLE) {
                Log.e(MAIN_TAG, "[ " + subTag + " ] : " + message);
            }
        }
    }

}
