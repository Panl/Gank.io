package com.smartalk.gank.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

  /**
   * 判断网络是否连接
   *
   * @param context
   * @return false
   */
  public static boolean isWIFIConnected(Context context) {
    ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo info = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
    return info != null && info.isConnected();
  }
}
