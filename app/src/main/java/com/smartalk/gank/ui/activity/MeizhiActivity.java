package com.smartalk.gank.ui.activity;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.smartalk.gank.PanConfig;
import com.smartalk.gank.R;
import com.smartalk.gank.ShareElement;
import com.smartalk.gank.model.entity.Meizi;
import com.smartalk.gank.presenter.MeizhiPresenter;
import com.smartalk.gank.ui.base.ToolBarActivity;
import com.smartalk.gank.utils.DateUtil;
import com.smartalk.gank.utils.FileUtil;
import com.smartalk.gank.utils.TipsUtil;
import com.smartalk.gank.view.IMeizhiView;

import butterknife.Bind;
import butterknife.ButterKnife;
import uk.co.senab.photoview.PhotoViewAttacher;

public class MeizhiActivity extends ToolBarActivity<MeizhiPresenter> implements IMeizhiView {

  Meizi meizi;
  PhotoViewAttacher attacher;
  MeizhiPresenter presenter;
  Bitmap girl;

  @Bind(R.id.iv_meizhi)
  ImageView ivMeizhi;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ButterKnife.bind(this);
  }

  @Override
  protected int provideContentViewId() {
    return R.layout.activity_meizhi;
  }

  @Override
  protected void initPresenter() {
    presenter = new MeizhiPresenter(this, this);
    presenter.init();
  }

  private void getIntentData() {
    meizi = (Meizi) getIntent().getSerializableExtra(PanConfig.MEIZI);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    ShareElement.shareDrawable = null;
    presenter.release();
    attacher.cleanup();
  }

  @Override
  public void init() {
    appBar.setAlpha(0.6f);
    getIntentData();
    initMeizhiView();
  }

  private void initMeizhiView() {
    setTitle(DateUtil.toDateTimeStr(meizi.publishedAt));
    ivMeizhi.setImageDrawable(ShareElement.shareDrawable);
    ViewCompat.setTransitionName(ivMeizhi, PanConfig.TRANSLATE_GIRL_VIEW);
    attacher = new PhotoViewAttacher(ivMeizhi);
    Glide.with(this).load(meizi.url).asBitmap().into(new SimpleTarget<Bitmap>() {
      @Override
      public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
        ivMeizhi.setImageBitmap(resource);
        attacher.update();
        girl = resource;
      }

      @Override
      public void onLoadFailed(Exception e, Drawable errorDrawable) {
        ivMeizhi.setImageDrawable(errorDrawable);
      }
    });
    attacher.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
      @Override
      public void onPhotoTap(View view, float x, float y) {
        hideOrShowToolBar();
      }
    });
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_girl, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_save:
        if (!FileUtil.isSDCardEnable() || girl == null) {
          TipsUtil.showSnackTip(ivMeizhi, getString(R.string.girl_reject_your_request));
        } else {
          presenter.saveMeizhiImage(girl, DateUtil.toDateString(meizi.publishedAt).toString());
        }
        break;
      case R.id.action_share:
        presenter.shareGirlImage(girl, DateUtil.toDateString(meizi.publishedAt).toString());
        break;
    }
    return super.onOptionsItemSelected(item);
  }


  @Override
  public void showSaveGirlResult(String result) {
    TipsUtil.showSnackTip(ivMeizhi, result);
  }
}
