package com.smartalk.gank.model.entity;

import java.util.Date;

/**
 * 妹子model
 * Created by panl on 15/12/20.
 */
public class Meizi extends Fundation {
  public String who;
  public String url;
  public String type;
  public String desc;
  public boolean used;
  public Date createdAt;
  public Date updatedAt;
  public Date publishedAt;

  @Override
  public String toString() {
    return "Meizi{" +
        "who='" + who + '\'' +
        ", url='" + url + '\'' +
        ", type='" + type + '\'' +
        ", desc='" + desc + '\'' +
        ", used=" + used +
        ", createdAt=" + createdAt +
        ", updatedAt=" + updatedAt +
        ", publishedAt=" + publishedAt +
        '}';
  }
}
