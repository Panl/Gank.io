package com.smartalk.gank.presenter;

import android.content.Context;

import com.smartalk.gank.http.PanClient;
import com.smartalk.gank.model.MeiziData;
import com.smartalk.gank.view.IMainView;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * 主界面presenter
 * Created by panl on 15/12/24.
 */
public class MainPresenter extends BasePresenter<IMainView> {

    public MainPresenter(Context context, IMainView iView) {
        super(context, iView);
        iView.initMainView();
    }

    public void fetchMeiziData(int page) {
        iView.showProgress();
        PanClient.getGankRetrofitInstance().getMeiziData(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<MeiziData>() {
                    @Override
                    public void call(MeiziData meiziData) {
                        iView.showMeiziList(meiziData.results);
                        iView.hideProgress();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        iView.showErrorView();
                        iView.hideProgress();
                    }
                });

    }


}
