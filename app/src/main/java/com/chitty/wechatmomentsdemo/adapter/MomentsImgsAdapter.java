package com.chitty.wechatmomentsdemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chitty.wechatmomentsdemo.R;
import com.chitty.wechatmomentsdemo.base.BaseAdapterHelper;
import com.chitty.wechatmomentsdemo.model.MomentsModel;

import java.util.List;


/**
 * Created by chitty on 2018/4/11.
 */

public class MomentsImgsAdapter extends BaseAdapterHelper<MomentsModel.ImagesBean> {

    public MomentsImgsAdapter(Context context, List<MomentsModel.ImagesBean> list) {
        super(context, list);
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<MomentsModel.ImagesBean> list, LayoutInflater inflater) {
        MyViewHolder mHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_img, parent, false);
            mHolder = new MyViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (MyViewHolder) convertView.getTag();
        }

        Glide.with(mContext).load(mList.get(position).getUrl())
                .placeholder(R.mipmap.img_default)
                .into(mHolder.mIvImg);


        return convertView;
    }

    class MyViewHolder {
        private ImageView mIvImg;



        public MyViewHolder(View itemView) {
            mIvImg = itemView.findViewById(R.id.mIvImg);

        }
    }
}
