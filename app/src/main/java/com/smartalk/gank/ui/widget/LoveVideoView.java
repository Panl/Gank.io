/*
 * Copyright (C) 2015 Drakeet <drakeet.me@gmail.com>
 *
 * This file is part of Meizhi
 *
 * Meizhi is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Meizhi is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Meizhi.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.smartalk.gank.ui.widget;

import android.content.Context;
import android.os.Build;
import android.support.v7.appcompat.BuildConfig;
import android.util.AttributeSet;
import android.util.Base64;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.InputStream;

/**
 * 来自drakeet的LoveVideoView
 *
 * @link https://github.com/drakeet/Meizhi/blob/master/app%2Fsrc%2Fmain%2Fjava%2Fme%2Fdrakeet%2Fmeizhi%2Fwidget%2FLoveVideoView.java
 * It's a lovely adult video view.
 * Created by drakeet on 8/14/15.
 */
public class LoveVideoView extends WebView {

  private final Context mContext;


  public LoveVideoView(Context context) {
    this(context, null);
  }


  public LoveVideoView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }


  public LoveVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    mContext = context;
    init();
  }


  void init() {
    setWebViewClient(new LoveClient());
    WebSettings webSettings = getSettings();
    webSettings.setJavaScriptEnabled(true);
    webSettings.setAllowFileAccess(true);
    webSettings.setDatabaseEnabled(true);
    webSettings.setDomStorageEnabled(true);
    webSettings.setSaveFormData(false);
    webSettings.setAppCacheEnabled(true);
    webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
    webSettings.setLoadWithOverviewMode(false);
    webSettings.setUseWideViewPort(true);

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
      if (BuildConfig.DEBUG) {
        WebView.setWebContentsDebuggingEnabled(true);
      }
    }
  }


  private class LoveClient extends WebViewClient {

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
      view.loadUrl(url);
      return true;
    }


    @Override
    public void onPageFinished(WebView view, String url) {
      super.onPageFinished(view, url);
      // 这些视频需要hack CSS才能达到全屏播放的效果
      if (url.contains("www.vmovier.com")) {
        injectCSS("vmovier.css");
      } else if (url.contains("video.weibo.com")) {
        injectCSS("weibo.css");
      } else if (url.contains("m.miaopai.com")) {
        injectCSS("miaopai.css");
      }
    }
  }


  // Inject CSS method: read style.css from assets folder
  // Append stylesheet to document head
  private void injectCSS(String filename) {
    try {
      InputStream inputStream = mContext.getAssets().open(filename);
      byte[] buffer = new byte[inputStream.available()];
      inputStream.read(buffer);
      inputStream.close();
      String encoded = Base64.encodeToString(buffer, Base64.NO_WRAP);
      loadUrl("javascript:(function() {" +
          "var parent = document.getElementsByTagName('head').item(0);" +
          "var style = document.createElement('style');" +
          "style.type = 'text/css';" +
          // Tell the browser to BASE64-decode the string into your script !!!
          "style.innerHTML = window.atob('" + encoded + "');" +
          "parent.appendChild(style)" +
          "})()");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
