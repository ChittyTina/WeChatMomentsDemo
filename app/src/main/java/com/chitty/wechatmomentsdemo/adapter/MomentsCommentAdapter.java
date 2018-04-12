package com.chitty.wechatmomentsdemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chitty.wechatmomentsdemo.R;
import com.chitty.wechatmomentsdemo.base.BaseAdapterHelper;
import com.chitty.wechatmomentsdemo.model.MomentsModel;

import java.util.List;


/**
 * Created by chitty on 2018/4/11.
 */

public class MomentsCommentAdapter extends BaseAdapterHelper<MomentsModel.CommentsBean> {

    public MomentsCommentAdapter(Context context, List<MomentsModel.CommentsBean> list) {
        super(context, list);
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<MomentsModel.CommentsBean> list, LayoutInflater inflater) {
        MyViewHolder mHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_comment, parent, false);
            mHolder = new MyViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (MyViewHolder) convertView.getTag();
        }

        mHolder.mTvComment.setText(mList.get(position).getSender().getNick()+":"+mList.get(position).getContent());
        return convertView;
    }

    class MyViewHolder {
        private TextView mTvComment;


        public MyViewHolder(View itemView) {
            mTvComment = itemView.findViewById(R.id.mTvComment);
        }
    }
}
