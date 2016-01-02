package com.smartalk.gank.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.smartalk.gank.PanConfig;
import com.smartalk.gank.R;
import com.smartalk.gank.model.entity.Gank;
import com.smartalk.gank.presenter.WebViewPresenter;
import com.smartalk.gank.ui.base.BaseActivity;
import com.smartalk.gank.view.IWebView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class WebActivity extends BaseActivity implements IWebView {


    private Gank gank;
    WebViewPresenter presenter;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.web_view)
    WebView webView;
    @Bind(R.id.progressbar)
    NumberProgressBar progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        ButterKnife.bind(this);
        presenter = new WebViewPresenter(this, this);
        presenter.initView();
    }

    public static void loadWebViewActivity(Context from, Gank gank) {
        Intent intent = new Intent(from, WebActivity.class);
        intent.putExtra(PanConfig.GANK, gank);
        from.startActivity(intent);
    }

    @Override
    public void showProgressBar(int progress) {
        progressbar.setProgress(progress);
        if (progress == 100) {
            progressbar.setVisibility(View.GONE);
        } else {
            progressbar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void initView() {
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        gank = (Gank) getIntent().getSerializableExtra(PanConfig.GANK);
        setTitle(gank.desc);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setAppCacheEnabled(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setSupportZoom(true);
        webView.setWebChromeClient(new ChromeClient());
        webView.setWebViewClient(new GankClient());
        webView.loadUrl(gank.url);
    }

    private class ChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            presenter.showProgress(newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            setTitle(title);
        }
    }

    private class GankClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url != null) view.loadUrl(url);
            return true;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN){
            switch (keyCode){
                case KeyEvent.KEYCODE_BACK:
                    if (webView.canGoBack()){
                        webView.goBack();
                    }else {
                        finish();
                    }
                    return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webView.destroy();
    }
}
