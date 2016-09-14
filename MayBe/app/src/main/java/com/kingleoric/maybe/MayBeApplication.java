package com.kingleoric.maybe;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.kingleoric.maybe.Utils.Logger;

/**
 * Created by qipu on 2016/8/8.
 */

public class MayBeApplication extends Application {

    public void onCreate() {
        super.onCreate();
        this.registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {

            @Override
            public void onActivityStopped(Activity activity) {
                Logger.v(activity, "onActivityStopped");
            }

            @Override
            public void onActivityStarted(Activity activity) {
                Logger.v(activity, "onActivityStarted");
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
                Logger.v(activity, "onActivitySaveInstanceState");
            }

            @Override
            public void onActivityResumed(Activity activity) {
                if (activity instanceof MainActivity) {
                    Logger.v(this, "MainActivity");
                }
                Logger.v(activity, "onActivityResumed");
            }

            @Override
            public void onActivityPaused(Activity activity) {
                Logger.v(activity, "onActivityPaused");
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                Logger.v(activity, "onActivityDestroyed");
            }

            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                Logger.v(activity, "onActivityCreated");
            }
        });
    };
}
