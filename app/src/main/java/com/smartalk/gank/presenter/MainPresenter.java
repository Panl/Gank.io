package com.smartalk.gank.presenter;

import android.content.Context;

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
public class MainPresenter extends BasePresenter<IMainView>{

    public MainPresenter(Context context, IMainView iView) {
        super(context, iView);
        iView.initMainView();
    }

    public void fetchMeiziData(int page){
        iView.showProgress();
        PanClient.getGankRetrofitInstance().getMeiziData(page)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MeiziData>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        iView.hideProgress();
                    }

                    @Override
                    public void onNext(MeiziData meiziData) {
                        iView.showMeiziList(meiziData.results);
                        iView.hideProgress();
                    }
                });

    }


}
