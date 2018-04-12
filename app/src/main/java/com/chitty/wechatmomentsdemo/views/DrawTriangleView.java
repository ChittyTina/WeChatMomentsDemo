package com.chitty.wechatmomentsdemo.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;

import com.chitty.wechatmomentsdemo.R;


/**
 * Created by chitty on 2018/4/12.
 * 画 倒三角
 */
public class DrawTriangleView extends View {

    public DrawTriangleView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(getResources().getColor(R.color.gray_bg));

        // 绘制这个三角形,你可以绘制任意多边形
        Path path = new Path();
        path.moveTo(0, 16);// 此点为多边形的起点
        path.lineTo(40, 16);
        path.lineTo(20, 0);
        path.close(); // 使这些点构成封闭的多边形
        canvas.drawPath(path, p);

    }
}
