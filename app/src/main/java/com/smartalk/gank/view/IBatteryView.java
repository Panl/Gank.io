package com.smartalk.gank.view;

import com.smartalk.gank.model.entity.Gank;

import java.util.List;

/**
 * BatteryView
 * Created by panl on 16/1/5.
 */
public interface IBatteryView extends IBaseView {
  void showProgressBar();

  void hideProgressBar();

  void showErrorView();

  void showNoMoreData();

  void showListView(List<Gank> gankList);
}
