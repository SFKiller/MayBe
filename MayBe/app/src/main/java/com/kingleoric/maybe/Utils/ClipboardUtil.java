package com.kingleoric.maybe.Utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;

/** A util class for writing and reading text to Clipboard
 * Created by qipu on 2016/8/4.
 */

public class ClipboardUtil {

    /**Clipboard text label*/
    private static final String CLIP_LABEL = "KINGLEORIC";

    private static final String INSTRUCTION_TAG = "KingLeoric";
    private Context context;

    public ClipboardUtil(Context ctx) {
        this.context = ctx;
    }

    public String getTextFromClipboard() {
        Logger.d(this, "getTextFromClipboard context : " + context);
        if (null != context) {
            ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            if (null != clipboardManager) {
                String text = clipboardManager.getPrimaryClip().getItemAt(0).getText().toString();
                Logger.d(this, "clipboard text : " + text);
                //if (isKingLeoricInstruction(text)) {
                    String showText = generateShowText(text);
                    createDialog(showText).show();
                //}
                return text;
            } else {
                Logger.w("QIPU", "ClipboardManager is not ready!!!");
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * Method to judge if the text in the system clipboard is KingLeoric instruction
     * @param instruction Text get from system clipboard
     * @return True is KingLeoric instruction; False is not KingLeoric instruction
     */
    private boolean isKingLeoricInstruction(final String instruction) {
        if (TextUtils.isEmpty(instruction)) {
            return false;
        } else {
            if (instruction.contains(INSTRUCTION_TAG)) {
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * Generate showing text with original clipboard text
     * @param originText Original text in the clipboard;
     *                   originText always not be null for it has checked in {@see isKingLeoricInstruction()}
     * @return Showing text
     */
    private String generateShowText(final String originText) {
        //TODO: Implement logic to generate showing text
        return originText;
    }

    /**
     * Dialog for showing the message
     * @param msg
     * @return
     */
    private Dialog createDialog(final String msg) {
        Dialog dialog = new AlertDialog.Builder(context)
                .setMessage(msg)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //TODO: Go to detail page
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .create();
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                clearClipboard();
            }
        });
        return dialog;
    }

    /**
     * Clear clipboard
     */
    private void clearClipboard() {
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData data = ClipData.newPlainText("", "");
        clipboardManager.setPrimaryClip(data);
    }
    /**
     * Set text to system clipboard
     * @param str Text to set
     */
    public void setTextToClipboard(final String str) {
        if (null != context) {
            ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboardManager.setPrimaryClip(ClipData.newPlainText(CLIP_LABEL, str));
        } else {
            Logger.w("QIPU", "ClipboardManager is not ready!!!");
        }
    }

}
