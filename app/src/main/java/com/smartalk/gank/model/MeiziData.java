package com.smartalk.gank.model;

import com.smartalk.gank.model.entity.Meizi;

import java.util.List;

/**
 * Created by panl on 15/12/20.
 */
public class MeiziData extends BaseData {
  public List<Meizi> results;

  @Override
  public String toString() {
    return "MeiziData{" +
        "results=" + results +
        '}';
  }
}
