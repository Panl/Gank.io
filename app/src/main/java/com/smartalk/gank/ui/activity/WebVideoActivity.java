package com.smartalk.gank.ui.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.smartalk.gank.PanConfig;
import com.smartalk.gank.R;
import com.smartalk.gank.model.entity.Gank;
import com.smartalk.gank.presenter.WebVideoPresenter;
import com.smartalk.gank.ui.base.ToolBarActivity;
import com.smartalk.gank.ui.widget.LoveVideoView;
import com.smartalk.gank.utils.ShareUtil;
import com.smartalk.gank.view.IWebVideo;

import butterknife.Bind;

public class WebVideoActivity extends ToolBarActivity<WebVideoPresenter> implements IWebVideo {
  @Bind(R.id.progressbar)
  NumberProgressBar progressbar;
  @Bind(R.id.web_video)
  LoveVideoView webVideo;
  private Gank gank;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
    super.onCreate(savedInstanceState);
  }

  @Override
  protected int provideContentViewId() {
    return R.layout.activity_web_video;
  }

  @Override
  protected void initPresenter() {
    presenter = new WebVideoPresenter(this, this);
    presenter.init();
  }

  @Override
  public void showProgressBar(int progress) {
    if (progressbar == null) return;
    progressbar.setProgress(progress);
    if (progress == 100) {
      progressbar.setVisibility(View.GONE);
    } else {
      progressbar.setVisibility(View.VISIBLE);
    }
  }

  @Override
  public void setWebTitle(String title) {
    setTitle(title);
  }

  @Override
  public void openFailed() {

  }

  @Override
  public void init() {
    toolbar.setBackgroundColor(Color.TRANSPARENT);
    toolbar.setBackgroundResource(R.drawable.transparent_bg);
    appBar.setBackgroundResource(R.drawable.transparent_bg);
    appBar.setBackgroundColor(Color.TRANSPARENT);
    hideToolBarElevation();
    gank = (Gank) getIntent().getSerializableExtra(PanConfig.GANK);
    setTitle(gank.desc);
    presenter.loadWebVideo(webVideo, gank.url);
  }

  private void hideToolBarElevation() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      appBar.setElevation(0);
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_video, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_share:
        if (gank != null)
          ShareUtil.shareVideo(this, gank);
        break;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  protected void onResume() {
    super.onResume();
    if (webVideo != null) {
      webVideo.resumeTimers();
      webVideo.onResume();
    }
  }

  @Override
  protected void onPause() {
    super.onPause();
    if (webVideo != null) {
      webVideo.onPause();
      webVideo.pauseTimers();
    }
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    if (webVideo != null) {
      webVideo.setWebViewClient(null);
      webVideo.setWebChromeClient(null);
      webVideo.destroy();
      webVideo = null;
    }
  }
}
