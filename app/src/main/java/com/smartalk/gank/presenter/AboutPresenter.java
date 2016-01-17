package com.smartalk.gank.presenter;

import android.content.Context;
import android.view.View;

import com.smartalk.gank.view.IBaseView;

import io.github.mthli.slice.Slice;

/**
 * Created by panl on 16/1/17.
 */
public class AboutPresenter extends BasePresenter<IBaseView> {

    public AboutPresenter(Context context, IBaseView iView) {
        super(context, iView);
    }

    public void clipViewToCornerCard(View view){
        Slice slice = new Slice(view);
        slice.setElevation(1.5f);
        slice.setRadius(5.0f);
    }

    @Override
    public void release() {

    }
}
