package com.smartalk.gank.http;

import com.smartalk.gank.PanConfig;
import com.smartalk.gank.model.BatteryData;
import com.smartalk.gank.model.GankData;
import com.smartalk.gank.model.MeiziData;
import com.smartalk.gank.model.休息视频Data;

import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

/**
 * get data
 * Created by panl on 15/12/20.
 */
public interface GankRetrofit {
    @GET("data/福利/" + PanConfig.MEIZI_SIZE + "/{page}")
    Observable<MeiziData> getMeiziData(@Path("page") int page);

    @GET("data/休息视频/" + PanConfig.MEIZI_SIZE + "/{page}")
    Observable<休息视频Data> get休息视频Data(@Path("page") int page);

    @GET("day/{year}/{month}/{day}")
    Observable<GankData> getDailyData(
            @Path("year") int year,
            @Path("month") int month,
            @Path("day") int day);

    @GET("data/{type}/"+PanConfig.GANK_SIZE+"/{page}")
    Observable<BatteryData> getBatteryData(@Path("type") String type,@Path("page") int page);
}
