package com.smartalk.gank.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.widget.Toast;

/**
 * Created by panl(panlei106@gmail.com)
 * Date:16/1/17.
 */
public class AndroidUtil {
    public static void copyToClipBoard(Context context, String text, String success) {
        ClipData clipData = ClipData.newPlainText("girl_copy", text);
        ClipboardManager manager = (ClipboardManager) context.getSystemService(
                Context.CLIPBOARD_SERVICE);
        manager.setPrimaryClip(clipData);
        Toast.makeText(context, success, Toast.LENGTH_SHORT).show();
    }
}
