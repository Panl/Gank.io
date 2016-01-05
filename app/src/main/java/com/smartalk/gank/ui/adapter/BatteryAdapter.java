package com.smartalk.gank.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.smartalk.gank.R;
import com.smartalk.gank.model.entity.Gank;
import com.smartalk.gank.ui.activity.WebActivity;
import com.smartalk.gank.utils.StringStyleUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * BatteryAdapter
 * Created by panl on 16/1/5.
 */
public class BatteryAdapter extends RecyclerView.Adapter<BatteryAdapter.BatteryHolder> {

    List<Gank> gankList;
    Context context;

    public BatteryAdapter(List<Gank> gankList, Context context) {
        this.gankList = gankList;
        this.context = context;
    }

    @Override
    public BatteryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_battery, parent, false);
        return new BatteryHolder(view);
    }

    @Override
    public void onBindViewHolder(BatteryHolder holder, int position) {
        Gank gank = gankList.get(position);
        holder.tvBattery.setTag(gank);
        holder.tvBattery.setText(StringStyleUtil.getGankStyleStr(gank));
    }

    @Override
    public int getItemCount() {
        return gankList.size();
    }

    class BatteryHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_battery)
        TextView tvBattery;
        @OnClick(R.id.ll_battery)
        void toWebClick(){
            WebActivity.loadWebViewActivity(context,(Gank)tvBattery.getTag());
        }

        public BatteryHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
