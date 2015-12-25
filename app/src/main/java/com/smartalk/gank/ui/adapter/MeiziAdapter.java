package com.smartalk.gank.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.smartalk.gank.PanConfig;
import com.smartalk.gank.R;
import com.smartalk.gank.model.entity.Meizi;
import com.smartalk.gank.ui.activity.GankActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 显示妹子的Adapter
 * Created by panl on 15/12/20.
 */
public class MeiziAdapter extends RecyclerView.Adapter<MeiziAdapter.MeiziHolder> {

    List<Meizi> list;
    Context context;
    TouchMeiziListener listener;


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
        Glide.with(context)
                .load(meizi.url)
                .centerCrop()
                .into(holder.ivMeizi);
        holder.tvWho.setText(meizi.desc + " @" + meizi.who);
        holder.ivMeizi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if (listener != null){
                        Intent intent = new Intent(context, GankActivity.class);
                        intent.putExtra(PanConfig.MEIZI,meizi);
                        context.startActivity(intent);
                        listener.onMeiziClick();
                    }
                }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class MeiziHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.iv_meizi)
        ImageView ivMeizi;
        @Bind(R.id.tv_who)
        TextView tvWho;

        View card;

        public MeiziHolder(View itemView) {
            super(itemView);
            card = itemView;
            ButterKnife.bind(this, itemView);
        }
    }

    public void setListener(TouchMeiziListener listener) {
        this.listener = listener;
    }

    public interface TouchMeiziListener {
        void onMeiziClick();
    }
}
