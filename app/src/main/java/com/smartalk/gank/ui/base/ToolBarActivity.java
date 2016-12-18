package com.smartalk.gank.ui.base;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.animation.DecelerateInterpolator;

import com.smartalk.gank.R;
import com.smartalk.gank.presenter.BasePresenter;

import butterknife.Bind;

/**
 * 带Toolbar的基础Activity
 * Created by panl on 16/1/5.
 */
public abstract class ToolBarActivity<T extends BasePresenter> extends BaseActivity {
  protected ActionBar actionBar;
  protected T presenter;
  protected boolean isToolBarHiding = false;

  @Bind(R.id.toolbar)
  protected Toolbar toolbar;
  @Bind(R.id.app_bar)
  protected AppBarLayout appBar;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    initToolBar();
  }

  protected boolean canBack() {
    return true;
  }

  protected void initToolBar() {
    setSupportActionBar(toolbar);
    actionBar = getSupportActionBar();
    if (actionBar != null) actionBar.setDisplayHomeAsUpEnabled(canBack());

  }

  protected void hideOrShowToolBar() {
    appBar.animate()
        .translationY(isToolBarHiding ? 0 : -appBar.getHeight())
        .setInterpolator(new DecelerateInterpolator(2))
        .start();
    isToolBarHiding = !isToolBarHiding;
  }
}
