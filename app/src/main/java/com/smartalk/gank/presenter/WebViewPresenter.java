package com.smartalk.gank.presenter;

import android.content.Context;

import com.smartalk.gank.view.IWebView;

/**
 * Created by panl on 16/1/2.
 */
public class WebViewPresenter extends BasePresenter<IWebView> {

    public WebViewPresenter(Context context, IWebView iView) {
        super(context, iView);
    }

    public void showProgress(int progress){
        iView.showProgressBar(progress);
    }
}
