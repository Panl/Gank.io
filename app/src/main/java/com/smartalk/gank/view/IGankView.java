package com.smartalk.gank.view;

import com.smartalk.gank.model.entity.Gank;

import java.util.List;

/**
 * 干货view
 * Created by panl on 15/12/25.
 */
public interface IGankView extends IBaseView {
    void showGankList(List<Gank> gankList);
    void showProgressBar();
    void hideProgressBar();
    void showErrorView();
}
