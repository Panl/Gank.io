package com.smartalk.gank.presenter;

import android.content.Context;
import android.media.MediaPlayer;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.smartalk.gank.view.IWebVideo;

/**
 * Created by panl on 16/1/17.
 */
public class WebVideoPresenter extends BasePresenter<IWebVideo> {

  public WebVideoPresenter(Context context, IWebVideo iView) {
    super(context, iView);
  }

  public void loadWebVideo(WebView webView, String url) {
    webView.setWebChromeClient(new Chrome());
    webView.loadUrl(url);
  }

  private class Chrome extends WebChromeClient
      implements MediaPlayer.OnCompletionListener {

    @Override
    public void onCompletion(MediaPlayer player) {
      if (player != null) {
        if (player.isPlaying()) player.stop();
        player.reset();
        player.release();
      }
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
      super.onProgressChanged(view, newProgress);
      iView.showProgressBar(newProgress);
    }

    @Override
    public void onReceivedTitle(WebView view, String title) {
      super.onReceivedTitle(view, title);
      iView.setWebTitle(title);
    }
  }

  @Override
  public void release() {

  }
}
