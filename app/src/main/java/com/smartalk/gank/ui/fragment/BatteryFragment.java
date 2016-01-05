package com.smartalk.gank.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.smartalk.gank.R;
import com.smartalk.gank.model.entity.Gank;
import com.smartalk.gank.presenter.BatteryFragmentPresenter;
import com.smartalk.gank.ui.adapter.BatteryAdapter;
import com.smartalk.gank.ui.base.BaseFragment;
import com.smartalk.gank.ui.widget.LMRecyclerView;
import com.smartalk.gank.utils.TipsUtil;
import com.smartalk.gank.view.IBatteryView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class BatteryFragment extends BaseFragment<BatteryFragmentPresenter> implements IBatteryView,
        LMRecyclerView.LoadMoreListener,SwipeRefreshLayout.OnRefreshListener {

    private static final String TYPE = "type";
    private String type;
    private BatteryAdapter adapter;
    private List<Gank> gankList;
    private int page = 1;
    private boolean isRefresh = true;
    private boolean canLoading = true;


    @Bind(R.id.recycler_view)
    LMRecyclerView recyclerView;
    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;



    public BatteryFragment() {
        // Required empty public constructor
    }

    public static BatteryFragment newInstance(String type) {
        BatteryFragment fragment = new BatteryFragment();
        Bundle args = new Bundle();
        args.putString(TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getString(TYPE);
        }
    }

    @Override
    protected int provideViewLayoutId() {
        return R.layout.fragment_ganks;
    }


    @Override
    protected void initPresenter() {
        presenter = new BatteryFragmentPresenter(getContext(),this);
        presenter.init();
    }

    @Override
    public void showProgressBar() {
        if (!swipeRefreshLayout.isRefreshing()){
            swipeRefreshLayout.setRefreshing(true);
        }
    }

    @Override
    public void hideProgressBar() {
        if (swipeRefreshLayout.isRefreshing()){
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void showErrorView() {
        canLoading = true;
        TipsUtil.showTipWithAction(recyclerView, getString(R.string.load_failed), getString(R.string.retry), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.loadGank(type,page);
            }
        });
    }

    @Override
    public void showNoMoreData() {
        canLoading = false;
        TipsUtil.showSnackTip(recyclerView, "全部加载完啦！");
    }

    @Override
    public void showListView(List<Gank> gankList) {
        canLoading = true;
        page++;
        if (isRefresh) {
            this.gankList.clear();
            this.gankList.addAll(gankList);
            adapter.notifyDataSetChanged();
            isRefresh = false;
        } else {
            this.gankList.addAll(gankList);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void init() {
        gankList = new ArrayList<>();
        adapter = new BatteryAdapter(gankList,getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        recyclerView.setLoadMoreListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.yellow, R.color.red, R.color.blue);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                presenter.loadGank(type,page);
            }
        });
    }

    @Override
    public void loadMore() {
        if (canLoading){
            presenter.loadGank(type,page);
            canLoading = false;
        }
    }

    @Override
    public void onRefresh() {
        isRefresh = true;
        page = 1;
        presenter.loadGank(type,page);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.release();
    }
}
