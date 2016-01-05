package com.smartalk.gank.presenter;

import android.content.Context;

import com.smartalk.gank.view.IBaseView;

import rx.Subscription;

/**
 * 基础presenter
 * Created by panl on 15/12/24.
 */
public abstract class BasePresenter<T extends IBaseView> {
    protected Subscription subscription;
    protected Context context;
    protected T iView;

    public BasePresenter(Context context, T iView) {
        this.context = context;
        this.iView = iView;
    }

    public void init(){
        iView.init();
    }

    public abstract void release();

}
