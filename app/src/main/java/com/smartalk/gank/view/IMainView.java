package com.smartalk.gank.view;

import com.smartalk.gank.model.entity.Meizi;

import java.util.List;

/**
 * 主界面的接口
 * Created by panl on 15/12/22.
 */
public interface IMainView {
    void initMainView();
    void showProgress();
    void hideProgress();
    void showMeiziList(List<Meizi> meiziList);
}
