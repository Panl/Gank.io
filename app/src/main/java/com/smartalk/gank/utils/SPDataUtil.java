package com.smartalk.gank.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.smartalk.gank.model.entity.Meizi;

import java.util.List;

/**
 * SharePreferenceDataUtils
 * Created by panl on 16/1/7.
 */
public class SPDataUtil {
  private static final String GANK = "gank";
  private static final String GIRLS = "girls";
  private static final String IS_FIRST_OPEN = "is_first_open";
  private static Gson gson = new Gson();

  public static boolean saveFirstPageGirls(Context context, List<Meizi> girls) {
    SharedPreferences preferences = context.getSharedPreferences(GANK, Context.MODE_PRIVATE);
    return preferences.edit().putString(GIRLS, gson.toJson(girls)).commit();
  }

  public static List<Meizi> getFirstPageGirls(Context context) {
    SharedPreferences preferences = context.getSharedPreferences(GANK, Context.MODE_PRIVATE);
    return gson.fromJson(preferences.getString(GIRLS, ""), new TypeToken<List<Meizi>>() {
    }.getType());
  }
}
