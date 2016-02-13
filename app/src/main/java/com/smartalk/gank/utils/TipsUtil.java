package com.smartalk.gank.utils;

import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * 用于显示提示信息
 * Created by panl on 15/12/26.
 */
public class TipsUtil {

    private TipsUtil() {

    }

    public static void showTipWithAction(View view, String tipText, String actionText, View.OnClickListener listener) {
        Snackbar.make(view, tipText, Snackbar.LENGTH_INDEFINITE).setAction(actionText, listener).show();
    }

    public static void showTipWithAction(View view, String tipText, String actionText, View.OnClickListener listener,int duration){
        Snackbar.make(view, tipText, duration).setAction(actionText, listener).show();
    }

    public static void showSnackTip(View view, String tipText) {
        Snackbar.make(view, tipText, Snackbar.LENGTH_SHORT).show();
    }
}
