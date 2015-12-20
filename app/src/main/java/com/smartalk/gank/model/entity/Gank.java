package com.smartalk.gank.model.entity;

import java.util.Date;

/**
 * 干货model
 * Created by panl on 15/12/20.
 */
public class Gank extends Fundation {
    public boolean used;
    public String type;
    public String url;
    public String who;
    public String desc;
    public Date updatedAt;
    public Date createdAt;
    public Date publishedAt;

    @Override
    public String toString() {
        return "Gank{" +
                "used=" + used +
                ", type='" + type + '\'' +
                ", url='" + url + '\'' +
                ", who='" + who + '\'' +
                ", desc='" + desc + '\'' +
                ", updatedAt=" + updatedAt +
                ", createdAt=" + createdAt +
                ", publishedAt=" + publishedAt +
                '}';
    }
}
