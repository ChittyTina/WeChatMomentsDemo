package com.chitty.wechatmomentsdemo.activity;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.chitty.wechatmomentsdemo.R;
import com.chitty.wechatmomentsdemo.adapter.MomentsAdapter;
import com.chitty.wechatmomentsdemo.base.BaseActivity;
import com.chitty.wechatmomentsdemo.base.MyApplication;
import com.chitty.wechatmomentsdemo.model.MomentsBean;
import com.chitty.wechatmomentsdemo.views.DividerItemDecoration;
import com.chitty.wechatmomentsdemo.views.EmptyLayout;
import com.chitty.wechatmomentsdemo.views.PullToRefreshLayout;
import com.chitty.wechatmomentsdemo.views.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements View.OnClickListener{

    private static final String TAG = "MainActivity";
    @BindView(R.id.mRlBack)
    RelativeLayout mRlBack;
    @BindView(R.id.mRlCamera)
    RelativeLayout mRlCamera;
    @BindView(R.id.mRlTitle)
    RelativeLayout mRlTitle;
    @BindView(R.id.mRecyclerView)
    XRecyclerView mRecyclerView;
    @BindView(R.id.mErrorLayout)
    EmptyLayout mErrorLayout;
    @BindView(R.id.mPtrLayout)
    PullToRefreshLayout mPtrLayout;

    private Context mContext = this;
    private long exitTime = 0;
    private MomentsAdapter momentsAdapter = null;
    private List<MomentsBean> list = new ArrayList<>();

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                MyApplication.getInstance().ExitApp();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void setAllClick() {
        mRlBack.setOnClickListener(this);
        mRlCamera.setOnClickListener(this);

        mPtrLayout.setListener(new PullToRefreshLayout.RefreshListener() {
            @Override
            public void onRefresh() {
                mPtrLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // TODO
                        mPtrLayout.stopRefresh();
                        Toast.makeText(MainActivity.this,"刷新完成",Toast.LENGTH_SHORT).show();
                    }
                },2000);
            }
        });
    }

    @Override
    protected void process() {
        loadDatas();

    }

    private void loadDatas() {
        // TODO ..

    }

    @Override
    protected void initView() {
        initRecyclerView();
        mRecyclerView.setFocusable(false);// 防止头部被顶上去
        momentsAdapter = new MomentsAdapter(MainActivity.this, list);

    }

    private LinearLayoutManager layoutManager;
    private void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        //添加自定义的分隔线
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.HORIZONTAL_LIST));
        layoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setNestedScrollingEnabled(false);
    }

    @Override
    protected void loadView() {
        setContentView(R.layout.activity_main);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.mRlBack:
                Toast.makeText(MainActivity.this, getResources().getString(R.string.go_back), Toast.LENGTH_SHORT).show();
                break;
            case R.id.mRlCamera:
                Toast.makeText(MainActivity.this, getResources().getString(R.string.open_camera), Toast.LENGTH_SHORT).show();
                break;

        }
    }
}
