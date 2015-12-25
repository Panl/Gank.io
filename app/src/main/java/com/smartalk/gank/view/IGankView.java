package com.smartalk.gank.view;

import com.smartalk.gank.model.entity.Gank;

import java.util.List;

/**
 * Created by panl on 15/12/25.
 */
public interface IGankView extends IBaseView {
    void initGankView();
    void showGankList(List<Gank> gankList);
}
