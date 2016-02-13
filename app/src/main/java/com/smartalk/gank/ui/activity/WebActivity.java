package com.smartalk.gank.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.smartalk.gank.PanConfig;
import com.smartalk.gank.R;
import com.smartalk.gank.model.entity.Gank;
import com.smartalk.gank.presenter.WebViewPresenter;
import com.smartalk.gank.ui.base.ToolBarActivity;
import com.smartalk.gank.utils.TipsUtil;
import com.smartalk.gank.view.IWebView;

import butterknife.Bind;

/**
 * webView not destroy yet?
 */
public class WebActivity extends ToolBarActivity<WebViewPresenter> implements IWebView {


    private Gank gank;
    WebViewPresenter presenter;
    LinearLayout contentView;

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.web_view)
    WebView webView;
    @Bind(R.id.progressbar)
    NumberProgressBar progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_web;
    }

    @Override
    protected void initPresenter() {
        presenter = new WebViewPresenter(this, this);
        presenter.init();
    }

    public static void loadWebViewActivity(Context from, Gank gank) {
        Intent intent = new Intent(from, WebActivity.class);
        intent.putExtra(PanConfig.GANK, gank);
        from.startActivity(intent);
    }

    @Override
    public void showProgressBar(int progress) {
        if (progressbar == null) return;
        progressbar.setProgress(progress);
        if (progress == 100) {
            progressbar.setVisibility(View.GONE);
        } else {
            progressbar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setWebTitle(String title) {
        setTitle(title);
    }

    @Override
    public void openFailed() {
        TipsUtil.showSnackTip(webView, getString(R.string.open_url_failed));
    }


    @Override
    public void init() {
        gank = (Gank) getIntent().getSerializableExtra(PanConfig.GANK);
        setTitle(gank.desc);
        presenter.setWebViewSettings(webView, gank.url);
        contentView = (LinearLayout) findViewById(R.id.web_content);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (webView.canGoBack()) {
                        webView.goBack();
                    } else {
                        finish();
                    }
                    return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_web, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                presenter.refresh(webView);
                break;
            case R.id.action_copy_url:
                presenter.copyUrl(gank.url);
                break;
            case R.id.action_open_in_browser:
                presenter.openInBrowser(gank.url);
                break;
            case R.id.action_share_gank:
                presenter.moreOperation(gank);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        if (webView != null) webView.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        if (webView != null) webView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webView != null) {
            contentView.removeView(webView);
            webView.removeAllViews();
            webView.destroy();
            webView = null;
        }
        presenter.release();
    }
}
