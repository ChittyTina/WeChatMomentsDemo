package com.chitty.wechatmomentsdemo.activity;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chitty.wechatmomentsdemo.R;
import com.chitty.wechatmomentsdemo.adapter.MomentsAdapter;
import com.chitty.wechatmomentsdemo.base.BaseActivity;
import com.chitty.wechatmomentsdemo.base.MyApplication;
import com.chitty.wechatmomentsdemo.interfaces.MyServerInterface;
import com.chitty.wechatmomentsdemo.model.MomentsModel;
import com.chitty.wechatmomentsdemo.utils.LogUtils;
import com.chitty.wechatmomentsdemo.utils.Tools;
import com.chitty.wechatmomentsdemo.views.EmptyLayout;
import com.chitty.wechatmomentsdemo.views.PullToRefreshLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class MainActivity extends BaseActivity implements View.OnClickListener{

    private static final String TAG = "朋友圈";
    @BindView(R.id.mRlBack)
    RelativeLayout mRlBack;
    @BindView(R.id.mRlCamera)
    RelativeLayout mRlCamera;
    @BindView(R.id.mRlTitle)
    RelativeLayout mRlTitle;
    @BindView(R.id.mListView)
    ListView mListView;
    @BindView(R.id.mErrorLayout)
    EmptyLayout mErrorLayout;
    @BindView(R.id.mPtrLayout)
    PullToRefreshLayout mPtrLayout;

    private Context mContext = this;
    private long exitTime = 0;
    private MomentsAdapter momentsAdapter = null;
    private List<MomentsModel> momentsModels =new ArrayList<>();
    private List<String> jsonList = new ArrayList<>();
    private LinearLayoutManager layoutManager;
    private MyServerInterface serverInterface = null;
    private Call<ResponseBody> callProfile = null;
    private String profileJson = "";
    private String momentsJson = "";

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
        loadProfileData();

    }
    private TextView mTvNick;
    private ImageView mIvHeadBg,mIvAvatar;
    @Override
    protected void initView() {

        View headView = LayoutInflater.from(this).inflate(R.layout.header_view,null);

        mTvNick = (TextView) headView.findViewById(R.id.mTvNick);
        mIvHeadBg = (ImageView) headView.findViewById(R.id.mIvHeadBg);
        mIvAvatar = (ImageView) headView.findViewById(R.id.mIvAvatar);

        mListView.addHeaderView(headView);

        momentsAdapter = new MomentsAdapter(MainActivity.this, momentsModels);

    }

    @Override
    protected void loadView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (callProfile != null){
            callProfile.cancel();
        }

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

    private void loadProfileData() {
        if (!Tools.checkConnectStatus(mContext)) {
            return;
        }

        if (jsonList != null){
            jsonList.clear();
        }
        serverInterface = ((MyApplication) MainActivity.this.getApplicationContext()).getMyServerInterface();
        Observable observableProfile = serverInterface.getProfile();
        Observable observableMomentsMsg = serverInterface.getMomentsMsg();

        Observable.merge(observableProfile, observableMomentsMsg).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<ResponseBody>>() {
                               @Override
                               public void onSubscribe(Disposable d) {

                               }

                               @Override
                               public void onNext(Response<ResponseBody> responseBodyResponse) {
                                   LogUtils.i(TAG,"--> merge - onSubscribe - onNext ------------------------------ ");

                                   if (responseBodyResponse.isSuccessful() && responseBodyResponse != null) {
                                       String jsonHead = null;

                                       try {

                                           String jsons = responseBodyResponse.body().string();
                                           jsonList.add(jsons);

                                           LogUtils.i(TAG,"--> merge - onSubscribe - jsonList = "+jsonList);

                                           if (jsonList.size() == 2) {
                                               for (int i = 0; i < jsonList.size(); i++) {
                                                   if (i == 0) {
                                                       profileJson = jsonList.get(0);
                                                       LogUtils.i(TAG,"--> merge - onSubscribe - profileJson = "+profileJson);
                                                   } else if (i == 1) {
                                                        momentsJson = jsonList.get(1);
                                                       LogUtils.i(TAG,"--> merge - onSubscribe - momentsJson = "+momentsJson);
                                                   }
                                               }
                                               handleJsons(jsonList);
                                           }

                                       } catch (IOException e) {
                                           e.printStackTrace();
                                       }
                                   }
                               }

                               @Override
                               public void onError(Throwable e) {

                               }

                               @Override
                               public void onComplete() {

                               }
                           });

    }

    private void handleJsons(List<String> jsonList) {
        if (jsonList != null && jsonList.size() == 2){

            if (momentsModels != null){
                momentsModels.clear();
            }

            try {
                JSONObject jsonObject = new JSONObject(profileJson);
                if (jsonObject.has("profile-image")) {
                    Glide.with(mContext).load(jsonObject.optString("profile-image"))
                            .placeholder(R.mipmap.head_bg)
                            .into(mIvHeadBg);
                }

                if (jsonObject.has("avatar")) {
                    Glide.with(mContext).load(jsonObject.optString("avatar"))
                            .placeholder(R.mipmap.avatar)
                            .into(mIvAvatar);
                }

                if (jsonObject.has("nick")){
                    mTvNick.setText(jsonObject.optString("nick"));
                }

                // 列表
                JSONArray jsonArray = new JSONArray(momentsJson);
                for (int i = 0; i < jsonArray.length(); i++){
                    JSONObject weChat = jsonArray.getJSONObject(i);
                    String content = "";
                    String nick = "";
                    String avatar = "";
                    List<MomentsModel.ImagesBean> imagesBeanList = new ArrayList<>();
                    List<MomentsModel.CommentsBean> commentsBeanList = new ArrayList<>();

                    if (weChat.has("error")){
                        content = weChat.optString("error");

                    }else if (weChat.has("unknown error")){
                        content = weChat.optString("unknown error");

                    } else {
                        if (weChat.has("content")){
                            content = weChat.optString("content");
                        }

                        if (weChat.has("images")){
                            JSONArray images = weChat.getJSONArray("images");
                            for (int j = 0; j < images.length(); j++){
                                JSONObject jsonObject1 = images.getJSONObject(j);
                                MomentsModel.ImagesBean imagesBean = new MomentsModel.ImagesBean();
                                imagesBean.setUrl(jsonObject1.optString("url"));
                                imagesBeanList.add(imagesBean);
                            }
                        }

                        if (weChat.has("comments")){
                            JSONArray comments = weChat.getJSONArray("comments");
                            for (int k = 0; k < comments.length(); k++){
                                JSONObject jsonObject1 = comments.getJSONObject(k);
                                MomentsModel.CommentsBean commentsBean = new MomentsModel.CommentsBean();
                                commentsBean.setContent(jsonObject1.optString("content"));
                                if (jsonObject1.has("sender")){
                                    JSONObject sender = jsonObject1.getJSONObject("sender");
                                    MomentsModel.CommentsBean.SenderBeanX senderBean = new MomentsModel.CommentsBean.SenderBeanX();
                                    senderBean.setAvatar(sender.optString("avatar"));
                                    senderBean.setNick(sender.optString("nick"));
                                    commentsBean.setSender(senderBean);
                                }
                                commentsBeanList.add(commentsBean);
                            }
                        }

                        if (weChat.has("sender")){
                            JSONObject sender = weChat.optJSONObject("sender");
                            nick = sender.optString("nick");
                            avatar = sender.optString("avatar");

                        }
                    }

                    MomentsModel momentsModel = new MomentsModel();
                    MomentsModel.SenderBean senderBean = new MomentsModel.SenderBean();
                    senderBean.setAvatar(avatar);
                    senderBean.setNick(nick);
                    momentsModel.setSender(senderBean);
                    momentsModel.setContent(content);
                    momentsModel.setComments(commentsBeanList);
                    momentsModel.setImages(imagesBeanList);
                    momentsModels.add(momentsModel);
                }

                if (momentsModels != null && momentsModels.size() > 0){
                    mErrorLayout.setVisibility(View.GONE);
                }
                mListView.setAdapter(momentsAdapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }

}
