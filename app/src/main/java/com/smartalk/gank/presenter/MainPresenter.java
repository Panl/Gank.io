package com.smartalk.gank.presenter;

import android.content.Context;
import android.content.Intent;

import com.smartalk.gank.http.PanClient;
import com.smartalk.gank.model.MeiziData;
import com.smartalk.gank.model.休息视频Data;
import com.smartalk.gank.ui.activity.AboutActivity;
import com.smartalk.gank.ui.activity.BatteryActivity;
import com.smartalk.gank.view.IMainView;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

/**
 * 主界面presenter
 * Created by panl on 15/12/24.
 */
public class MainPresenter extends BasePresenter<IMainView> {

    public MainPresenter(Context context, IMainView iView) {
        super(context, iView);
    }

    @Override
    public void release() {
        subscription.unsubscribe();
    }

    public void fetchMeiziData(int page) {
        iView.showProgress();
        subscription = Observable.zip(PanClient.getGankRetrofitInstance().getMeiziData(page),
                PanClient.getGankRetrofitInstance().get休息视频Data(page),
                new Func2<MeiziData, 休息视频Data, MeiziData>() {
                    @Override
                    public MeiziData call(MeiziData meiziData, 休息视频Data 休息视频Data) {
                        return createMeiziDataWith休息视频Desc(meiziData, 休息视频Data);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<MeiziData>() {
                    @Override
                    public void call(MeiziData meiziData) {
                        if (meiziData.results.size() == 0){
                            iView.showNoMoreData();
                        }else {
                            iView.showMeiziList(meiziData.results);
                        }
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

    private MeiziData createMeiziDataWith休息视频Desc(MeiziData meiziData, 休息视频Data data) {
        int size = Math.min(meiziData.results.size(),data.results.size());
        for (int i = 0; i < size; i++) {
            meiziData.results.get(i).desc = meiziData.results.get(i).desc + "，" + data.results.get(i).desc;
            meiziData.results.get(i).who = data.results.get(i).who;
        }
        return meiziData;
    }

    public void toBatteryActivity(){
        Intent intent = new Intent(context, BatteryActivity.class);
        context.startActivity(intent);
    }

    public void toAboutActivity(){
        Intent intent = new Intent(context, AboutActivity.class);
        context.startActivity(intent);
    }

}
