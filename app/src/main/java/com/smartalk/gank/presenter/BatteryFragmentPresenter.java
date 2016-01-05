package com.smartalk.gank.presenter;

import android.content.Context;

import com.smartalk.gank.http.PanClient;
import com.smartalk.gank.model.BatteryData;
import com.smartalk.gank.view.IBatteryView;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Battery
 * Created by panl on 16/1/5.
 */
public class BatteryFragmentPresenter extends BasePresenter<IBatteryView> {


    public BatteryFragmentPresenter(Context context, IBatteryView iView) {
        super(context, iView);
    }

    @Override
    public void release() {
        subscription.unsubscribe();
    }

    public void loadGank(String type,int page){
        iView.showProgressBar();
        subscription = PanClient.getGankRetrofitInstance().getBatteryData(type,page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<BatteryData>() {
                    @Override
                    public void call(BatteryData batteryData) {
                        iView.hideProgressBar();
                        if (batteryData.results.size() == 0){
                            iView.showNoMoreData();
                        }else {
                            iView.showListView(batteryData.results);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        iView.hideProgressBar();
                        iView.showErrorView();
                    }
                });
    }
}
