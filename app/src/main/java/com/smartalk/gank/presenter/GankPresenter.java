package com.smartalk.gank.presenter;

import android.content.Context;
import android.widget.Toast;

import com.smartalk.gank.http.PanClient;
import com.smartalk.gank.model.GankData;
import com.smartalk.gank.model.entity.Gank;
import com.smartalk.gank.view.IGankView;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by panl on 15/12/25.
 */
public class GankPresenter extends BasePresenter<IGankView> {

    public GankPresenter(Context context, IGankView iView) {
        super(context, iView);
    }

    public void initGankView(){
        iView.initGankView();
    }

    public void fetchGankData(int year,int month,int day){
        PanClient.getGankRetrofitInstance().getDailyData(year,month,day)
                .map(new Func1<GankData, List<Gank>>() {
                    @Override
                    public List<Gank> call(GankData gankData) {
                        return addAllResults(gankData.results);
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Gank>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(context,"error",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(List<Gank> gankList) {
                        iView.showGankList(gankList);
                    }
                });
    }

    private List<Gank> addAllResults(GankData.Result results){
        List<Gank> mGankList = new ArrayList<>();
        if (results.androidList != null) mGankList.addAll(results.androidList);
        if (results.iOSList != null) mGankList.addAll(results.iOSList);
        if (results.appList != null) mGankList.addAll(results.appList);
        if (results.拓展资源List != null) mGankList.addAll(results.拓展资源List);
        if (results.瞎推荐List != null) mGankList.addAll(results.瞎推荐List);
        if (results.休息视频List != null) mGankList.addAll(0, results.休息视频List);
        return mGankList;
    }

}
