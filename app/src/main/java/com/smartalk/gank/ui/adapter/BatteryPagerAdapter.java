package com.smartalk.gank.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.smartalk.gank.ui.fragment.BatteryFragment;

/**
 * Created by panl on 16/1/5.
 */
public class BatteryPagerAdapter extends FragmentStatePagerAdapter {

  String[] title = {"Android", "iOS", "前端", "瞎推荐", "拓展资源", "App"};

  public BatteryPagerAdapter(FragmentManager fm) {
    super(fm);
  }

  @Override
  public Fragment getItem(int position) {
    return BatteryFragment.newInstance(title[position]);
  }

  @Override
  public int getCount() {
    return title.length;
  }

  @Override
  public CharSequence getPageTitle(int position) {
    return title[position];
  }
}
