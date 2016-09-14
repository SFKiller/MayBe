package com.kingleoric.maybe;

import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

import com.kingleoric.maybe.Utils.ClipboardUtil;
import com.kingleoric.maybe.Utils.Logger;
import com.kingleoric.maybe.ui.KingLeoricActivity;

public class MainActivity extends KingLeoricActivity {

    /**For handle data in Clipboard*/
    private ClipboardManager clipboardManager;
    private ClipboardManager.OnPrimaryClipChangedListener onPrimaryClipChangedListener;

    private TextView hello;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hello = (TextView) findViewById(R.id.hello);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                if (view instanceof TextView) {
                    ((TextView) view).setText("Hello, my name is Robert.");
                }
            }
        };
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
