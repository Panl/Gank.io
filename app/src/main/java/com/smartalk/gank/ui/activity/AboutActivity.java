package com.smartalk.gank.ui.activity;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.smartalk.gank.BuildConfig;
import com.smartalk.gank.R;
import com.smartalk.gank.presenter.AboutPresenter;
import com.smartalk.gank.ui.base.ToolBarActivity;
import com.smartalk.gank.view.IBaseView;

import butterknife.Bind;

public class AboutActivity extends ToolBarActivity<AboutPresenter> implements IBaseView {


    @Bind(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.tv_app_version)
    TextView tvAppVersion;
    @Bind(R.id.rl_introduce)
    RelativeLayout rlIntroduce;
    @Bind(R.id.rl_developer)
    RelativeLayout rlDeveloper;
    @Bind(R.id.rl_open_source)
    RelativeLayout rlOpenSource;
    @Bind(R.id.rl_thanks)
    RelativeLayout rlThanks;

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
        toolbarLayout.setTitle("关于" + getString(R.string.app_name));
        tvAppVersion.setText("Version: " + BuildConfig.VERSION_NAME);
        presenter.clipViewToCornerCard(rlIntroduce);
        presenter.clipViewToCornerCard(rlDeveloper);
        presenter.clipViewToCornerCard(rlOpenSource);
        presenter.clipViewToCornerCard(rlThanks);
    }

}
