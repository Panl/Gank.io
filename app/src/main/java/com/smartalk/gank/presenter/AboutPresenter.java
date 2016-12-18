package com.smartalk.gank.presenter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.smartalk.gank.R;
import com.smartalk.gank.view.IBaseView;

/**
 * Created by panl on 16/1/17.
 */
public class AboutPresenter extends BasePresenter<IBaseView> {

  public AboutPresenter(Context context, IBaseView iView) {
    super(context, iView);
  }


  public void starInMarket() {
    Uri uri = Uri.parse(String.format(context.getString(R.string.market), context.getPackageName()));
    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    if (intent.resolveActivity(context.getPackageManager()) != null)
      context.startActivity(intent);
  }

  @Override
  public void release() {

  }
}
