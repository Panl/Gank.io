package com.smartalk.gank.presenter;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;

import com.smartalk.gank.view.IMeizhiView;

import java.io.File;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by panl on 16/1/3.
 */
public class MeizhiPresenter extends BasePresenter<IMeizhiView> {

    public MeizhiPresenter(Context context, IMeizhiView iView) {
        super(context, iView);
    }

    public void saveMeizhiImage(Bitmap bitmap) {
        Observable.just(bitmap)
                .flatMap(new Func1<Bitmap, Observable<?>>() {
                    @Override
                    public Observable<?> call(Bitmap bitmap) {
                        File gankDir = new File(Environment.getExternalStorageDirectory(), "gank");
                        if (!gankDir.exists()) {
                            gankDir.mkdirs();
                        }
                        return null;
                    }
                });
    }
}
