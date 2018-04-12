package com.chitty.wechatmomentsdemo.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chitty.wechatmomentsdemo.R;
import com.chitty.wechatmomentsdemo.base.BaseAdapterHelper;
import com.chitty.wechatmomentsdemo.model.MomentsModel;
import com.chitty.wechatmomentsdemo.views.CustomGridView;
import com.chitty.wechatmomentsdemo.views.CustomListView;

import java.util.List;

/**
 * Created by Chitty on 18/4/11.
 */

public class MomentsAdapter extends BaseAdapterHelper<MomentsModel> {

    private MomentsImgsAdapter momentsImgsAdapter = null;
    private MomentsCommentAdapter momentsCommentAdapter = null;

    public MomentsAdapter(Context context, List<MomentsModel> list) {
        super(context, list);
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<MomentsModel> list, LayoutInflater inflater) {
        MyViewHolder mHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_view, parent, false);
            mHolder = new MyViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (MyViewHolder) convertView.getTag();
        }


        mHolder.mTvSenderNick.setText(mList.get(position).getSender().getNick());
        if (TextUtils.isEmpty(mList.get(position).getContent())){
            mHolder.mTvContent.setVisibility(View.VISIBLE);
            mHolder.mTvContent.setText(mList.get(position).getContent());
        }else {
            mHolder.mTvContent.setVisibility(View.GONE);
        }

        Glide.with(mContext).load(mList.get(position).getSender().getAvatar())
                .placeholder(R.mipmap.avatar)
                .into(mHolder.mIvSenderAvatar);

        if (mList.get(position).getImages() != null && mList.get(position).getImages().size() > 0){
            mHolder.mCgvImgs.setVisibility(View.VISIBLE);
            momentsImgsAdapter = new MomentsImgsAdapter(mContext,mList.get(position).getImages());
            mHolder.mCgvImgs.setAdapter(momentsImgsAdapter);
            if (mList.get(position).getImages().size() == 1){
                mHolder.mCgvImgs.setNumColumns(1);
            }else if (mList.get(position).getImages().size() == 2 || mList.get(position).getImages().size() == 4){
                mHolder.mCgvImgs.setNumColumns(2);
            }else {
                mHolder.mCgvImgs.setNumColumns(3);
            }

        }else {
            mHolder.mCgvImgs.setVisibility(View.GONE);
        }

        if (mList.get(position).getComments() != null && mList.get(position).getComments().size() > 0){
            mHolder.mClvComments.setVisibility(View.VISIBLE);
            momentsCommentAdapter = new MomentsCommentAdapter(mContext,mList.get(position).getComments());
            mHolder.mClvComments.setAdapter(momentsCommentAdapter);
        }else {
            mHolder.mClvComments.setVisibility(View.GONE);
        }

        return convertView;
    }

    class MyViewHolder {
        private TextView mTvNick,mTvSenderNick,mTvContent;
        private ImageView mIvHeadBg,mIvAvatar,mIvSenderAvatar;
        private CustomGridView mCgvImgs;
        private CustomListView mClvComments;

        public MyViewHolder(View itemView) {
            mIvSenderAvatar = (ImageView) itemView.findViewById(R.id.mIvSenderAvatar);
            mTvSenderNick = (TextView) itemView.findViewById(R.id.mTvSenderNick);
            mTvContent = (TextView) itemView.findViewById(R.id.mTvContent);
            mCgvImgs = (CustomGridView) itemView.findViewById(R.id.mCgvImgs);
            mClvComments = (CustomListView) itemView.findViewById(R.id.mClvComments);
        }
    }

}
