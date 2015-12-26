package com.smartalk.gank.utils;

import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * 用于显示提示信息
 * Created by panl on 15/12/26.
 */
public class TipsUtil {
    public static void showTipWithAction(View view, String tipText, String actionText, View.OnClickListener listener) {
        Snackbar.make(view, tipText, Snackbar.LENGTH_INDEFINITE).setAction(actionText, listener).show();
    }
}
