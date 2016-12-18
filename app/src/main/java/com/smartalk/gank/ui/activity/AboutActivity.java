package com.smartalk.gank.ui.activity;

import android.support.design.widget.CollapsingToolbarLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.smartalk.gank.BuildConfig;
import com.smartalk.gank.R;
import com.smartalk.gank.presenter.AboutPresenter;
import com.smartalk.gank.ui.base.ToolBarActivity;
import com.smartalk.gank.utils.ShareUtil;
import com.smartalk.gank.view.IBaseView;

import butterknife.Bind;
import butterknife.OnClick;

public class AboutActivity extends ToolBarActivity<AboutPresenter> implements IBaseView {


  @Bind(R.id.toolbar_layout)
  CollapsingToolbarLayout toolbarLayout;
  @Bind(R.id.tv_app_version)
  TextView tvAppVersion;

  @OnClick(R.id.fab)
  void fabClick() {
    presenter.starInMarket();
  }

  @Override
  protected int provideContentViewId() {
    return R.layout.activity_about;
  }

  @Override
  protected void initPresenter() {
    presenter = new AboutPresenter(this, this);
    presenter.init();
  }

  @Override
  public void init() {
    toolbarLayout.setTitle(getString(R.string.about_app));
    tvAppVersion.setText(String.format(getString(R.string.version), BuildConfig.VERSION_NAME));
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_about, menu);
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_share:
        ShareUtil.shareApp(this);
        break;
    }
    return super.onOptionsItemSelected(item);
  }
}
