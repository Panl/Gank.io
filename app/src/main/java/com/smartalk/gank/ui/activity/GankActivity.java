package com.smartalk.gank.ui.activity;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

import com.smartalk.gank.PanConfig;
import com.smartalk.gank.R;
import com.smartalk.gank.ShareElement;
import com.smartalk.gank.model.entity.Gank;
import com.smartalk.gank.model.entity.Meizi;
import com.smartalk.gank.presenter.GankPresenter;
import com.smartalk.gank.ui.adapter.GankAdapter;
import com.smartalk.gank.ui.base.ToolBarActivity;
import com.smartalk.gank.utils.DateUtil;
import com.smartalk.gank.utils.TipsUtil;
import com.smartalk.gank.view.IGankView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;

public class GankActivity extends ToolBarActivity<GankPresenter> implements IGankView {

    private Meizi meizi;
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
    @Bind(R.id.progressbar)
    SmoothProgressBar progressbar;

    @OnClick(R.id.fab)
    void fabClick() {
        TipsUtil.showSnackTip(fab, "功能待开发...");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_gank;
    }

    @Override
    protected void initPresenter() {
        presenter = new GankPresenter(this, this);
        presenter.init();
    }

    @Override
    public void init() {
        getIntentData();
        initGankView();
    }

    private void initGankView() {
        list = new ArrayList<>();
        adapter = new GankAdapter(list, this);
        rvGank.setLayoutManager(new LinearLayoutManager(this));
        rvGank.setItemAnimator(new DefaultItemAnimator());
        rvGank.setAdapter(adapter);
        setTitle(DateUtil.toDateString(meizi.publishedAt));
        ivHead.setImageDrawable(ShareElement.shareDrawable);
        ViewCompat.setTransitionName(ivHead, PanConfig.TRANSLATE_GIRL_VIEW);
    }

    private void getIntentData() {
        meizi = (Meizi) getIntent().getSerializableExtra(PanConfig.MEIZI);
        calendar = Calendar.getInstance();
        calendar.setTime(meizi.publishedAt);
        presenter.fetchGankData(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void showGankList(List<Gank> gankList) {
        list.addAll(gankList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showProgressBar() {
        progressbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressbar.setVisibility(View.GONE);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_gank, menu);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
