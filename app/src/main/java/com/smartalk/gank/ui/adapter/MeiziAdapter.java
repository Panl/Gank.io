package com.smartalk.gank.ui.adapter;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.smartalk.gank.PanConfig;
import com.smartalk.gank.R;
import com.smartalk.gank.ShareElement;
import com.smartalk.gank.model.entity.Meizi;
import com.smartalk.gank.ui.activity.GankActivity;
import com.smartalk.gank.ui.activity.MeizhiActivity;
import com.smartalk.gank.utils.DateUtil;

import java.io.Serializable;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 显示妹子的Adapter
 * Created by panl on 15/12/20.
 */
public class MeiziAdapter extends RecyclerView.Adapter<MeiziAdapter.MeiziHolder> {

  List<Meizi> list;
  Context context;
  int lastPosition = 0;


  public MeiziAdapter(Context context, List<Meizi> list) {
    this.list = list;
    this.context = context;
  }

  @Override
  public MeiziHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meizi, parent, false);
    return new MeiziHolder(view);
  }

  @Override
  public void onBindViewHolder(MeiziHolder holder, int position) {
    final Meizi meizi = list.get(position);
    holder.card.setTag(meizi);
    int red = (int) (Math.random() * 255);
    int green = (int) (Math.random() * 255);
    int blue = (int) (Math.random() * 255);
    holder.ivMeizi.setBackgroundColor(Color.argb(204, red, green, blue));
    Glide.with(context)
        .load(meizi.url)
        .crossFade()
        .into(holder.ivMeizi);
    holder.tvWho.setText(meizi.who);
    holder.tvDesc.setText(meizi.desc);
    holder.tvTime.setText(DateUtil.toDateTimeStr(meizi.publishedAt));
    showItemAnimation(holder, position);
  }

  @Override
  public int getItemCount() {
    return list.size();
  }


  private void showItemAnimation(MeiziHolder holder, int position) {
    if (position > lastPosition) {
      lastPosition = position;
      ObjectAnimator.ofFloat(holder.card, "translationY", 1f * holder.card.getHeight(), 0f)
          .setDuration(500)
          .start();
    }
  }

  class MeiziHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.iv_meizi)
    ImageView ivMeizi;
    @Bind(R.id.tv_who)
    TextView tvWho;
    @Bind(R.id.tv_desc)
    TextView tvDesc;
    @Bind(R.id.tv_time)
    TextView tvTime;

    @OnClick(R.id.iv_meizi)
    void meiziClick() {
      ShareElement.shareDrawable = ivMeizi.getDrawable();
      Intent intent = new Intent(context, MeizhiActivity.class);
      intent.putExtra(PanConfig.MEIZI, (Serializable) card.getTag());
      ActivityOptionsCompat optionsCompat = ActivityOptionsCompat
          .makeSceneTransitionAnimation((Activity) context, ivMeizi, PanConfig.TRANSLATE_GIRL_VIEW);
      ActivityCompat.startActivity(context, intent, optionsCompat.toBundle());
    }

    @OnClick(R.id.rl_gank)
    void gankClick() {
      ShareElement.shareDrawable = ivMeizi.getDrawable();
      Intent intent = new Intent(context, GankActivity.class);
      intent.putExtra(PanConfig.MEIZI, (Serializable) card.getTag());
      ActivityOptionsCompat optionsCompat = ActivityOptionsCompat
          .makeSceneTransitionAnimation((Activity) context, ivMeizi, PanConfig.TRANSLATE_GIRL_VIEW);
      ActivityCompat.startActivity(context, intent, optionsCompat.toBundle());
    }

    View card;

    public MeiziHolder(View itemView) {
      super(itemView);
      card = itemView;
      ButterKnife.bind(this, itemView);
    }

  }
}
