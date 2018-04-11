package com.chitty.wechatmomentsdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chitty.wechatmomentsdemo.R;
import com.chitty.wechatmomentsdemo.model.MomentsBean;
import com.chitty.wechatmomentsdemo.utils.RecyclerViewAdapterHelper;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by Chitty on 18/4/11.
 */

public class MomentsAdapter extends RecyclerViewAdapterHelper<MomentsBean> {

    private static final int IS_NORMAL = 1;
    private static final int IS_HEADER = 2;
    private static final int IS_FOOTER = 3;

    public MomentsAdapter(Context context, List<MomentsBean> list) {
        super(context, list);
    }

    @Override
    public RecyclerView.ViewHolder onCreateMyViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder;
        //对不同的flag创建不同的Holder
        if (viewType == IS_HEADER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_view, parent, false);
            holder = new ViewHolder(view, IS_HEADER);
            return holder;

        } else if (viewType == IS_FOOTER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer_view, parent, false);
            holder = new ViewHolder(view, IS_FOOTER);
            return holder;

        } else if (viewType == IS_NORMAL) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);
            holder = new ViewHolder(view, IS_NORMAL);
            return holder;
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return mList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return IS_HEADER;
        } else if (position == mList.size()) {
            return IS_FOOTER;
        } else {
            return IS_NORMAL;
        }
    }

    @Override
    public void onBindMyViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            if (position == 0 && ((ViewHolder) holder).viewType == IS_HEADER) {
                // TODO..

            }


            if (position == mList.size() && ((ViewHolder) holder).viewType == IS_FOOTER) {
                // TODO...
                holder.itemView.setVisibility(View.VISIBLE);

            }

            if (position > 0 && position < mList.size() && ((ViewHolder) holder).viewType == IS_NORMAL) {
                // TODO...

            }

            holder.itemView.setTag(position);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public int viewType;

        public ViewHolder(View itemView, int viewType) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.viewType = viewType;

            if (viewType == IS_HEADER) {

            }

            if (viewType == IS_NORMAL) {

            }

            if (viewType == IS_FOOTER) {

            }
        }
    }
}
