package com.smartalk.gank.ui.adapter;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.smartalk.gank.R;
import com.smartalk.gank.model.entity.Gank;
import com.smartalk.gank.utils.StringStyleUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by panl on 15/12/25.
 */
public class GankAdapter extends RecyclerView.Adapter<GankAdapter.GankHolder> {
    List<Gank> list;
    Context context;

    public GankAdapter(List<Gank> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public GankHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gank, parent, false);
        return new GankHolder(view);
    }

    @Override
    public void onBindViewHolder(GankHolder holder, int position) {
        Gank gank = list.get(position);
        holder.llGank.setTag(gank);
        if (position == 0) {
            showCategory(true, holder.tvCategory);
        } else {
            if (list.get(position).type.equals(list.get(position - 1).type)) {
                showCategory(false, holder.tvCategory);
            } else {
                showCategory(true, holder.tvCategory);
            }
        }
        holder.tvCategory.setText(gank.type);
        holder.tvGankDesc.setText(StringStyleUtil.getGankStyleStr(gank));
    }

    private void showCategory(boolean show, TextView tvCategory) {
        if (show) {
            tvCategory.setVisibility(View.VISIBLE);
        } else {
            tvCategory.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class GankHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_category)
        TextView tvCategory;
        @Bind(R.id.tv_gank_desc)
        TextView tvGankDesc;
        @Bind(R.id.ll_gank)
        LinearLayout llGank;

        @OnClick(R.id.ll_gank)
        void gankClick() {
            Snackbar.make(llGank,"Gank.io",Snackbar.LENGTH_SHORT).setAction("gank",null).show();
        }

        public GankHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
