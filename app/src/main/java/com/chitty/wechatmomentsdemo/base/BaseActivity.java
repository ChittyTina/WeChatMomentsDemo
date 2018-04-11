package com.chitty.wechatmomentsdemo.base;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;


/**
 * Created by chitty on 2018/4/14.
 */
public abstract class BaseActivity extends AppCompatActivity {

    private static final String TAG = "BaseActivity";
    private Context mContext = this;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.getInstance().addActivity(this);

        loadView();
        ButterKnife.bind(this);
        initView();
        process();
        setAllClick();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.bind(this).unbind();
        MyApplication.getInstance().finishActivity(this);
    }

    /**
     * 设置所有监听
     */
    protected abstract void setAllClick();

    /**
     * 逻辑过程
     */
    protected abstract void process();

    /**
     * 初始化视图
     */
    protected abstract void initView();

    /**
     * 加载视图
     */
    protected abstract void loadView();

}
