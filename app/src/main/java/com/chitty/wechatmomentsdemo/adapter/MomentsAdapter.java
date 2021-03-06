package com.chitty.wechatmomentsdemo.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chitty.wechatmomentsdemo.R;
import com.chitty.wechatmomentsdemo.base.BaseAdapterHelper;
import com.chitty.wechatmomentsdemo.model.MomentsModel;
import com.chitty.wechatmomentsdemo.views.CustomListView;
import com.chitty.wechatmomentsdemo.views.DrawTriangleView;
import com.chitty.wechatmomentsdemo.views.NineGridTestLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chitty on 18/4/11.
 */

public class MomentsAdapter extends BaseAdapterHelper<MomentsModel> {

    private static final String TAG = "MomentsAdapter";
    private MomentsCommentAdapter momentsCommentAdapter = null;
    private List<String> imgUrls = new ArrayList<>();

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
        if (!TextUtils.isEmpty(mList.get(position).getContent())){
            mHolder.mTvContent.setVisibility(View.VISIBLE);
            mHolder.mTvContent.setText(mList.get(position).getContent());
        }else {
            mHolder.mTvContent.setVisibility(View.GONE);
        }

        Glide.with(mContext).load(mList.get(position).getSender().getAvatar())
                .placeholder(R.mipmap.avatar)
                .centerCrop()
                .into(mHolder.mIvSenderAvatar);

        imgUrls.clear();
        if (mList.get(position).getImages() != null && mList.get(position).getImages().size() > 0){
            mHolder.mNineGridLayout.setVisibility(View.VISIBLE);
            for (int i = 0; i < mList.get(position).getImages().size(); i++) {
                imgUrls.add(mList.get(position).getImages().get(i).getUrl());
            }
            mHolder.mNineGridLayout.setIsShowAll(true);
            mHolder.mNineGridLayout.setUrlList(imgUrls);
        }else {
            mHolder.mNineGridLayout.setVisibility(View.GONE);
        }

        if (mList.get(position).getComments() != null && mList.get(position).getComments().size() > 0){
            mHolder.mClvComments.setVisibility(View.VISIBLE);
            mHolder.mLlTriangle.setVisibility(View.VISIBLE);
            momentsCommentAdapter = new MomentsCommentAdapter(mContext,mList.get(position).getComments());
            mHolder.mClvComments.setAdapter(momentsCommentAdapter);

            mHolder.mLlTriangle.removeAllViews();
            drawTriangle(mHolder.mLlTriangle);

        }else {
            mHolder.mClvComments.setVisibility(View.GONE);
            mHolder.mLlTriangle.setVisibility(View.GONE);
        }

        return convertView;
    }

    private void drawTriangle(LinearLayout layout) {
        final DrawTriangleView view = new DrawTriangleView(mContext);
        view.setMinimumHeight(16);
        view.setMinimumWidth(40);
        //通知view组件重绘
        view.invalidate();
        layout.addView(view);
    }

    class MyViewHolder {
        private TextView mTvSenderNick,mTvContent;
        private ImageView mIvSenderAvatar;
        private CustomListView mClvComments;
        private NineGridTestLayout mNineGridLayout;
        private LinearLayout mLlTriangle;

        public MyViewHolder(View itemView) {
            mIvSenderAvatar = (ImageView) itemView.findViewById(R.id.mIvSenderAvatar);
            mTvSenderNick = (TextView) itemView.findViewById(R.id.mTvSenderNick);
            mTvContent = (TextView) itemView.findViewById(R.id.mTvContent);
            mClvComments = (CustomListView) itemView.findViewById(R.id.mClvComments);
            mNineGridLayout = (NineGridTestLayout) itemView.findViewById(R.id.mNineGridLayout);
            mLlTriangle = (LinearLayout) itemView.findViewById(R.id.mLlTriangle);
        }
    }
}
