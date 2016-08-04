package com.kingleoric.maybe.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.kingleoric.maybe.Utils.Logger;

/** Base Activity, do some common things.
 * @author KingLeoric
 * @date 2016/7/27
 */
public class KingLeoricActivity extends FragmentActivity {

    protected String BASE_TAG = getClass().getSimpleName();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.d(BASE_TAG, "******** onCreate ********");
    }

    @Override
    public void onResume() {
        super.onResume();
        Logger.d(BASE_TAG, "******** onResume ********");
    }

    @Override
    public void onStart() {
        super.onStart();
        Logger.d(BASE_TAG, "******** onStart ********");
    }

    @Override
    public void onRestart() {
        super.onRestart();
        Logger.d(BASE_TAG, "******** onRestart ********");
    }

    @Override
    public void onStop() {
        super.onStop();
        Logger.d(BASE_TAG, "******** onStop ********");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Logger.d(BASE_TAG, "******** onDestroy ********");
    }
}
