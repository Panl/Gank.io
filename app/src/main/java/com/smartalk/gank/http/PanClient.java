package com.smartalk.gank.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * contact panlei106@gmail.com
 * Created by panl on 15/12/20.
 */
public class PanClient {
    public static final String HOST = "http://gank.io/api/";
    private static GankRetrofit gankRetrofit;
    protected static final Object monitor = new Object();

    private static Retrofit retrofit;

    private PanClient(){

    }

    static {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl(HOST)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }


    public static GankRetrofit getGankRetrofitInstance() {
        synchronized (monitor) {
            if (gankRetrofit == null) {
                gankRetrofit = retrofit.create(GankRetrofit.class);
            }
            return gankRetrofit;
        }
    }
}
