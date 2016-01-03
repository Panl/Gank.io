package com.smartalk.gank.ui.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.smartalk.gank.PanConfig;
import com.smartalk.gank.R;
import com.smartalk.gank.model.entity.Meizi;
import com.smartalk.gank.presenter.MeizhiPresenter;
import com.smartalk.gank.ui.base.BaseActivity;
import com.smartalk.gank.utils.DateUtil;
import com.smartalk.gank.view.IMeizhiView;

import butterknife.Bind;
import butterknife.ButterKnife;
import uk.co.senab.photoview.PhotoViewAttacher;

public class MeizhiActivity extends BaseActivity implements IMeizhiView {

    public static final String TRANSLATE_VIEW = "meizhi";

    Meizi meizi;
    PhotoViewAttacher attacher;
    MeizhiPresenter presenter;
    @Bind(R.id.iv_meizhi)
    ImageView ivMeizhi;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.app_bar)
    AppBarLayout appBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meizhi);
        ButterKnife.bind(this);
        presenter = new MeizhiPresenter(this, this);
        presenter.initView();
    }

    private void getIntentData() {
        meizi = (Meizi) getIntent().getSerializableExtra(PanConfig.MEIZI);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void initView() {
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.setDisplayHomeAsUpEnabled(true);
        appBar.setAlpha(0.6f);
        getIntentData();
        setTitle(DateUtil.toDateTimeStr(meizi.publishedAt));
        ViewCompat.setTransitionName(ivMeizhi, TRANSLATE_VIEW);
        Glide.with(this).load(meizi.url).fitCenter().into(new SimpleTarget<GlideDrawable>() {
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                ivMeizhi.setImageDrawable(resource);
                attacher = new PhotoViewAttacher(ivMeizhi);
            }

            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                ivMeizhi.setImageDrawable(errorDrawable);
            }
        });
    }
}
