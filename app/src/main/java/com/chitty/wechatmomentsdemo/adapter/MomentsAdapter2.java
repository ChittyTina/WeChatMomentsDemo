package com.chitty.wechatmomentsdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chitty.wechatmomentsdemo.R;
import com.chitty.wechatmomentsdemo.model.MomentsModel;
import com.chitty.wechatmomentsdemo.utils.RecyclerViewAdapterHelper;
import com.chitty.wechatmomentsdemo.views.CustomGridView;
import com.chitty.wechatmomentsdemo.views.CustomListView;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by Chitty on 18/4/11.
 */

public class MomentsAdapter2 extends RecyclerViewAdapterHelper<MomentsModel> {

    private static final int IS_NORMAL = 1;
    private static final int IS_HEADER = 2;
    private static final int IS_FOOTER = 3;

    public MomentsAdapter2(Context context, List<MomentsModel> list) {
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
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view2, parent, false);
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

                ((ViewHolder) holder).mTvNick.setText(mList.get(position).getNick());
                Glide.with(mContext).load(mList.get(position).getAvatar())
                        .placeholder(R.mipmap.avatar)
                        .into(((ViewHolder) holder).mIvAvatar);

                Glide.with(mContext).load(mList.get(position).getProfileimage())
                        .placeholder(R.mipmap.head_bg)
                        .into(((ViewHolder) holder).mIvHeadBg);

            }

            if (position == mList.size() && ((ViewHolder) holder).viewType == IS_FOOTER) {
                // TODO...
                holder.itemView.setVisibility(View.VISIBLE);

            }

            if (position > 0 && position < mList.size() && ((ViewHolder) holder).viewType == IS_NORMAL) {
                // TODO... mCgvImgs \ mClvComments
                ((ViewHolder) holder).mTvSenderNick.setText(mList.get(position).getNick());
                ((ViewHolder) holder).mTvContent.setText(mList.get(position).getContent());

                Glide.with(mContext).load(mList.get(position).getAvatar())
                        .placeholder(R.mipmap.avatar)
                        .into(((ViewHolder) holder).mIvSenderAvatar);




            }

            holder.itemView.setTag(position);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public int viewType;
        private TextView mTvNick,mTvSenderNick,mTvContent;
        private ImageView mIvHeadBg,mIvAvatar,mIvSenderAvatar;
        private CustomGridView mCgvImgs;
        private CustomListView mClvComments;

        public ViewHolder(View itemView, int viewType) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.viewType = viewType;

            if (viewType == IS_HEADER) {
                mTvNick = (TextView) itemView.findViewById(R.id.mTvNick);
                mIvHeadBg = (ImageView) itemView.findViewById(R.id.mIvHeadBg);
                mIvAvatar = (ImageView) itemView.findViewById(R.id.mIvAvatar);
            }

            if (viewType == IS_NORMAL) {
                mIvSenderAvatar = (ImageView) itemView.findViewById(R.id.mIvSenderAvatar);
                mTvSenderNick = (TextView) itemView.findViewById(R.id.mTvSenderNick);
                mTvContent = (TextView) itemView.findViewById(R.id.mTvContent);
                mCgvImgs = (CustomGridView) itemView.findViewById(R.id.mCgvImgs);
                mClvComments = (CustomListView) itemView.findViewById(R.id.mClvComments);
            }

            if (viewType == IS_FOOTER) {

            }
        }
    }
}
