package com.smartalk.gank.ui.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.smartalk.gank.R;
import com.smartalk.gank.presenter.BatteryPresenter;
import com.smartalk.gank.ui.adapter.BatteryPagerAdapter;
import com.smartalk.gank.ui.base.ToolBarActivity;
import com.smartalk.gank.view.IBaseView;

import butterknife.Bind;

public class BatteryActivity extends ToolBarActivity<BatteryPresenter> implements IBaseView{

    @Bind(R.id.tab_layout)
    TabLayout tabLayout;
    @Bind(R.id.container)
    ViewPager container;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_battery;
    }

    @Override
    protected void initPresenter() {
        presenter = new BatteryPresenter(this,this);
        presenter.init();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_battery, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void init() {
        BatteryPagerAdapter pagerAdapter = new BatteryPagerAdapter(getSupportFragmentManager());
        container.setAdapter(pagerAdapter);
        container.setOffscreenPageLimit(4);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setupWithViewPager(container);
        tabLayout.setTabsFromPagerAdapter(pagerAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.release();
    }
}
