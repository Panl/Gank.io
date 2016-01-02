package com.smartalk.gank.ui.activity;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.smartalk.gank.PanConfig;
import com.smartalk.gank.R;
import com.smartalk.gank.model.entity.Gank;
import com.smartalk.gank.model.entity.Meizi;
import com.smartalk.gank.presenter.GankPresenter;
import com.smartalk.gank.ui.adapter.GankAdapter;
import com.smartalk.gank.ui.base.BaseActivity;
import com.smartalk.gank.utils.DateUtil;
import com.smartalk.gank.utils.TipsUtil;
import com.smartalk.gank.view.IGankView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GankActivity extends BaseActivity implements IGankView {

    private Meizi meizi;
    private GankPresenter presenter;
    private List<Gank> list;
    private GankAdapter adapter;
    private Calendar calendar;

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;
    @Bind(R.id.app_bar)
    AppBarLayout appBar;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.iv_head)
    ImageView ivHead;
    @Bind(R.id.rv_gank)
    RecyclerView rvGank;
    @OnClick(R.id.fab)
    void fabClick(){
        TipsUtil.showSnackTip(fab,"功能待开发...");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gank);
        ButterKnife.bind(this);
        presenter = new GankPresenter(this, this);
        presenter.initView();
        presenter.fetchGankData(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void initView() {
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.setDisplayHomeAsUpEnabled(true);
        list = new ArrayList<>();
        adapter = new GankAdapter(list, this);
        rvGank.setLayoutManager(new LinearLayoutManager(this));
        rvGank.setAdapter(adapter);
        getIntentData();
        setTitle(DateUtil.toDateString(meizi.publishedAt));
        Glide.with(this).load(meizi.url).centerCrop().into(ivHead);
        calendar = Calendar.getInstance();
        calendar.setTime(meizi.publishedAt);
    }

    private void getIntentData() {
        meizi = (Meizi) getIntent().getSerializableExtra(PanConfig.MEIZI);
    }

    @Override
    public void showGankList(List<Gank> gankList) {
        list.addAll(gankList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showErrorView() {
        TipsUtil.showTipWithAction(fab, getString(R.string.load_failed), getString(R.string.retry), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.fetchGankData(calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH) + 1,
                        calendar.get(Calendar.DAY_OF_MONTH));
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
