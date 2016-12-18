package com.smartalk.gank.ui.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smartalk.gank.GankApp;
import com.smartalk.gank.presenter.BasePresenter;

import butterknife.ButterKnife;

/**
 * 基础Fragment
 * Created by panl on 16/1/5.
 */
public abstract class BaseFragment<T extends BasePresenter> extends Fragment {

  protected T presenter;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(provideViewLayoutId(), container, false);
    ButterKnife.bind(this, view);
    initPresenter();
    return view;
  }

  protected abstract int provideViewLayoutId();

  protected abstract void initPresenter();

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    ButterKnife.unbind(this);
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    GankApp.getRefWatcher(getActivity()).watch(this);
  }
}
