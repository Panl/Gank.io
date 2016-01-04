package com.smartalk.gank.ui.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.smartalk.gank.R;
import com.smartalk.gank.model.entity.Meizi;
import com.smartalk.gank.presenter.MainPresenter;
import com.smartalk.gank.ui.adapter.MeiziAdapter;
import com.smartalk.gank.ui.base.BaseActivity;
import com.smartalk.gank.ui.widget.LMRecyclerView;
import com.smartalk.gank.utils.TipsUtil;
import com.smartalk.gank.view.IMainView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements
        SwipeRefreshLayout.OnRefreshListener, IMainView, LMRecyclerView.LoadMoreListener {

    private List<Meizi> meizis;
    private MeiziAdapter adapter;
    private MainPresenter presenter;
    private int page = 1;
    private boolean isRefresh = true;
    private boolean isLoading = false;

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.rv_meizi)
    LMRecyclerView rvMeizi;
    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.fab)
    FloatingActionButton fab;

    @OnClick(R.id.fab)
    void fabClick() {
        TipsUtil.showSnackTip(fab, "功能待开发...");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        presenter = new MainPresenter(this, this);
        presenter.initView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        isRefresh = true;
        page = 1;
        presenter.fetchMeiziData(page);
    }

    @Override
    public void initView() {
        setSupportActionBar(toolbar);
        meizis = new ArrayList<>();
        adapter = new MeiziAdapter(this, meizis);
        rvMeizi.setLayoutManager(new LinearLayoutManager(this));
        rvMeizi.setAdapter(adapter);
        rvMeizi.applyFloatingActionButton(fab);
        rvMeizi.setLoadMoreListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.yellow, R.color.red, R.color.blue);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                presenter.fetchMeiziData(page);
            }
        });
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rvMeizi.smoothScrollToPosition(0);
            }
        });
    }

    @Override
    public void showProgress() {
        if (!swipeRefreshLayout.isRefreshing())
            swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        if (swipeRefreshLayout.isRefreshing())
            swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showErrorView() {
        isLoading = false;
        TipsUtil.showTipWithAction(fab, getString(R.string.load_failed), getString(R.string.retry), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.fetchMeiziData(page);
            }
        });
    }

    @Override
    public void showMeiziList(List<Meizi> meiziList) {
        isLoading = false;
        page++;
        if (isRefresh) {
            meizis.clear();
            meizis.addAll(meiziList);
            adapter.notifyDataSetChanged();
            isRefresh = false;
        } else {
            meizis.addAll(meiziList);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void loadMore() {
        if (!isLoading)
            presenter.fetchMeiziData(page);
        isLoading = true;
    }
}
