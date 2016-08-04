package com.kingleoric.maybe;

import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;

import com.kingleoric.maybe.Utils.ClipboardUtil;
import com.kingleoric.maybe.Utils.Logger;
import com.kingleoric.maybe.ui.KingLeoricActivity;

public class MainActivity extends KingLeoricActivity {

    /**For handle data in Clipboard*/
    private ClipboardManager clipboardManager;
    private ClipboardManager.OnPrimaryClipChangedListener onPrimaryClipChangedListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initClipboardManager();
    }

    /**
     * Init Clipboard Manager for later using
     */
    private void initClipboardManager() {
        clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        onPrimaryClipChangedListener = new ClipboardManager.OnPrimaryClipChangedListener() {
            @Override
            public void onPrimaryClipChanged() {
                Logger.d(MainActivity.this, "onPrimaryClipChanged");

            }
        };
        clipboardManager.addPrimaryClipChangedListener(onPrimaryClipChangedListener);
    }

    @Override
    public void onResume() {
        super.onResume();
//        ClipboardUtil clipboardUtil = new ClipboardUtil(this);
//        String text = clipboardUtil.getTextFromClipboard();
//        Logger.d(this, " text = " + text);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        /**Remove clipboard change listener*/
        clipboardManager.removePrimaryClipChangedListener(onPrimaryClipChangedListener);
    }
}
