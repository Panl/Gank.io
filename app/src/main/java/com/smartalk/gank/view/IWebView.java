package com.smartalk.gank.view;

/**
 * webView
 * Created by panl on 16/1/2.
 */
public interface IWebView extends IBaseView {

    void showProgressBar(int progress);

    void setWebTitle(String title);

    void openFailed();

}
