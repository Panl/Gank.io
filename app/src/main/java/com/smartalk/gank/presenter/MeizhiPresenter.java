package com.smartalk.gank.presenter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;

import com.smartalk.gank.utils.FileUtil;
import com.smartalk.gank.view.IMeizhiView;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by panl on 16/1/3.
 */
public class MeizhiPresenter extends BasePresenter<IMeizhiView> {

    public MeizhiPresenter(Context context, IMeizhiView iView) {
        super(context, iView);
    }

    @Override
    public void release() {
        if (subscription != null)
            subscription.unsubscribe();
    }

    public void saveMeizhiImage(final Bitmap bitmap, final String title) {
        subscription = Observable.create(new Observable.OnSubscribe<Uri>() {
            @Override
            public void call(Subscriber<? super Uri> subscriber) {
                Uri uri = FileUtil.saveBitmapToSDCard(bitmap, title);
                if (uri == null) {
                    subscriber.onError(new Exception("禽兽,妹子拒绝了您的请求(failed)!"));
                } else {
                    subscriber.onNext(uri);
                    subscriber.onCompleted();
                }

            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Uri>() {
                    @Override
                    public void call(Uri uri) {
                        Intent scannerIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
                        context.sendBroadcast(scannerIntent);
                        iView.showSaveGirlResult("该妹子已经躺在您的图库中了!");
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        iView.showSaveGirlResult("禽兽,妹子拒绝了您的请求(failed)!");
                    }
                });
    }


}
