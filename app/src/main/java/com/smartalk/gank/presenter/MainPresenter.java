package com.smartalk.gank.presenter;

import com.smartalk.gank.http.PanClient;
import com.smartalk.gank.model.MeiziData;
import com.smartalk.gank.view.IMainView;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 主界面presenter
 * Created by panl on 15/12/24.
 */
public class MainPresenter {
    IMainView mainView;

    public MainPresenter(IMainView mainView) {
        this.mainView = mainView;
        mainView.initMainView();
    }

    public void fetchMeiziData(){
        mainView.showProgress();
        PanClient.getGankRetrofitInstance().getMeiziData(1)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MeiziData>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mainView.hideProgress();
                    }

                    @Override
                    public void onNext(MeiziData meiziData) {
                        mainView.showMeiziList(meiziData.results);
                        mainView.hideProgress();
                    }
                });

    }
}
