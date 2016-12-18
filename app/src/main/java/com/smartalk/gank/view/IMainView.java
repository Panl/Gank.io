package com.smartalk.gank.view;

import com.smartalk.gank.model.entity.Meizi;

import java.util.List;

/**
 * 主界面的接口
 * Created by panl on 15/12/22.
 */
public interface IMainView extends IBaseView {
  void showProgress();

  void hideProgress();

  void showErrorView();

  void showNoMoreData();

  void showMeiziList(List<Meizi> meiziList);
}
