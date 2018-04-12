//package com.chitty.wechatmomentsdemo.activity;
//
//import android.content.Context;
//import android.support.v7.widget.LinearLayoutManager;
//import android.view.KeyEvent;
//import android.view.View;
//import android.widget.RelativeLayout;
//import android.widget.Toast;
//
//import com.chitty.wechatmomentsdemo.R;
//import com.chitty.wechatmomentsdemo.adapter.MomentsAdapter;
//import com.chitty.wechatmomentsdemo.base.BaseActivity;
//import com.chitty.wechatmomentsdemo.base.MyApplication;
//import com.chitty.wechatmomentsdemo.interfaces.MyServerInterface;
//import com.chitty.wechatmomentsdemo.model.MomentsModel;
//import com.chitty.wechatmomentsdemo.utils.LogUtils;
//import com.chitty.wechatmomentsdemo.utils.Tools;
//import com.chitty.wechatmomentsdemo.views.DividerItemDecoration;
//import com.chitty.wechatmomentsdemo.views.EmptyLayout;
//import com.chitty.wechatmomentsdemo.views.PullToRefreshLayout;
//import com.chitty.wechatmomentsdemo.views.XRecyclerView;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//import butterknife.BindView;
//import io.reactivex.Observable;
//import io.reactivex.Observer;
//import io.reactivex.android.schedulers.AndroidSchedulers;
//import io.reactivex.disposables.Disposable;
//import io.reactivex.schedulers.Schedulers;
//import okhttp3.ResponseBody;
//import retrofit2.Call;
//import retrofit2.Response;
//
//public class MainActivity2 extends BaseActivity implements View.OnClickListener{
//
//    private static final String TAG = "朋友圈";
//    @BindView(R.id.mRlBack)
//    RelativeLayout mRlBack;
//    @BindView(R.id.mRlCamera)
//    RelativeLayout mRlCamera;
//    @BindView(R.id.mRlTitle)
//    RelativeLayout mRlTitle;
//    @BindView(R.id.mRecyclerView)
//    XRecyclerView mRecyclerView;
//    @BindView(R.id.mErrorLayout)
//    EmptyLayout mErrorLayout;
//    @BindView(R.id.mPtrLayout)
//    PullToRefreshLayout mPtrLayout;
//
//    private Context mContext = this;
//    private long exitTime = 0;
//    private MomentsAdapter momentsAdapter = null;
//    private List<MomentsModel> MomentsModels=new ArrayList<>();
//    private List<String> jsonList = new ArrayList<>();
//    private LinearLayoutManager layoutManager;
//    private MyServerInterface serverInterface = null;
//    private Call<ResponseBody> callProfile = null;
//
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
//            if ((System.currentTimeMillis() - exitTime) > 2000) {
//                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
//                exitTime = System.currentTimeMillis();
//            } else {
//                MyApplication.getInstance().ExitApp();
//                System.exit(0);
//            }
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }
//
//    @Override
//    protected void setAllClick() {
//        mRlBack.setOnClickListener(this);
//        mRlCamera.setOnClickListener(this);
//
//        mPtrLayout.setListener(new PullToRefreshLayout.RefreshListener() {
//            @Override
//            public void onRefresh() {
//                mPtrLayout.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        // TODO
//                        mPtrLayout.stopRefresh();
//                        Toast.makeText(MainActivity2.this,"刷新完成",Toast.LENGTH_SHORT).show();
//                    }
//                },2000);
//            }
//        });
//
//
//    }
//
//    @Override
//    protected void process() {
//        loadProfileData();
//
//    }
//
//    @Override
//    protected void initView() {
//        initRecyclerView();
//        mRecyclerView.setFocusable(false);// 防止头部被顶上去
//        momentsAdapter = new MomentsAdapter(MainActivity2.this, MomentsModels);
//
//    }
//
//    @Override
//    protected void loadView() {
//        setContentView(R.layout.activity_main2);
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        if (callProfile != null){
//            callProfile.cancel();
//        }
//
//    }
//
//    @Override
//    public void onClick(View view) {
//        switch (view.getId()){
//            case R.id.mRlBack:
//                Toast.makeText(MainActivity2.this, getResources().getString(R.string.go_back), Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.mRlCamera:
//                Toast.makeText(MainActivity2.this, getResources().getString(R.string.open_camera), Toast.LENGTH_SHORT).show();
//                break;
//
//        }
//    }
//
//    private void initRecyclerView() {
//        mRecyclerView.setHasFixedSize(true);
//        //添加自定义的分隔线
//        mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.HORIZONTAL_LIST));
//        layoutManager = new LinearLayoutManager(mContext);
//        mRecyclerView.setLayoutManager(layoutManager);
//        mRecyclerView.setNestedScrollingEnabled(false);
//    }
//
//    private void loadProfileData() {
//        if (!Tools.checkConnectStatus(mContext)) {
//            return;
//        }
//
//        if (jsonList != null){
//            jsonList.clear();
//        }
//        serverInterface = ((MyApplication) MainActivity2.this.getApplicationContext()).getMyServerInterface();
//        Observable observableProfile = serverInterface.getProfile();
//        Observable observableMomentsMsg = serverInterface.getMomentsMsg();
//
//        Observable.merge(observableProfile, observableMomentsMsg).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<Response<ResponseBody>>() {
//                               @Override
//                               public void onSubscribe(Disposable d) {
//
//                               }
//
//                               @Override
//                               public void onNext(Response<ResponseBody> responseBodyResponse) {
//                                   LogUtils.i(TAG,"--> merge - onSubscribe - onNext ------------------------------ ");
//
//                                   if (responseBodyResponse.isSuccessful() && responseBodyResponse != null) {
//                                       String jsonHead = null;
//
//                                       try {
////                                           LogUtils.i(TAG,"--> merge - onSubscribe - responseBodyResponse = "+responseBodyResponse);
////                                           LogUtils.i(TAG,"--> merge - onSubscribe - responseBodyResponse.body().string() = "+responseBodyResponse.body().string());
//
//                                           String jsons = responseBodyResponse.body().string();
//                                           jsonList.add(jsons);
//
//                                           LogUtils.i(TAG,"--> merge - onSubscribe - jsonList = "+jsonList);
//
//                                           if (jsonList.size() == 2) {
//                                               for (int i = 0; i < jsonList.size(); i++) {
//                                                   if (i == 0) {
//                                                       profileJson = jsonList.get(0);
//                                                       LogUtils.i(TAG,"--> merge - onSubscribe - profileJson = "+profileJson);
//                                                   } else if (i == 1) {
//                                                        momentsJson = jsonList.get(1);
//                                                       LogUtils.i(TAG,"--> merge - onSubscribe - momentsJson = "+momentsJson);
//                                                   }
//                                               }
//                                               handleJsons(jsonList);
//                                           }
//
//                                       } catch (IOException e) {
//                                           e.printStackTrace();
//                                       }
//                                   }
//                               }
//
//                               @Override
//                               public void onError(Throwable e) {
//
//                               }
//
//                               @Override
//                               public void onComplete() {
//
//                               }
//                           });
//
//    }
//
//    private String profileJson = "";
//    private String momentsJson = "";
//    private void handleJsons(List<String> jsonList) {
//        LogUtils.i(TAG,"--> handleJsons - ------------------ start -------  jsonList.size() = "+jsonList.size());
//        if (jsonList != null && jsonList.size() == 2){
//
//            if (MomentsModels != null){
//                MomentsModels.clear();
//            }
//
//            try {
//                MomentsModel momentsModel = new MomentsModel();
//                JSONObject jsonObject = new JSONObject(profileJson);
//                if (jsonObject.has("profile-image")) {
//                    momentsModel.setProfileimage(jsonObject.optString("profile-image"));
//                }
//
//                if (jsonObject.has("avatar")) {
//                    momentsModel.setAvatar(jsonObject.optString("avatar"));
//                }
//
//                if (jsonObject.has("nick")){
//                    momentsModel.setNick(jsonObject.optString("nick"));
//                }
//                MomentsModels.add(momentsModel);
//                LogUtils.i(TAG,"--> handleJsons - 1 - MomentsModels.size()) = "+MomentsModels.size());
//
//                // 列表
//                JSONArray jsonArray = new JSONArray(momentsJson);
//                for (int i = 0; i < jsonArray.length(); i++){
//                    JSONObject weChat = jsonArray.getJSONObject(i);
//                    String content = "";
//                    String nick = "";
//                    String avatar = "";
//                    List<MomentsModel.ImagesBean> imagesBeanList = new ArrayList<>();
//                    List<MomentsModel.CommentsBean> commentsBeanList = new ArrayList<>();
//
//                    if (weChat.has("error")){
//                        content = weChat.optString("error");
//
//                    }else if (weChat.has("unknown error")){
//                        content = weChat.optString("unknown error");
//
//                    } else {
//                        if (weChat.has("content")){
//                            content = weChat.optString("content");
//                        }
//
//                        if (weChat.has("images")){
//                            JSONArray images = weChat.getJSONArray("images");
//                            for (int j = 0; j < images.length(); j++){
//                                JSONObject jsonObject1 = images.getJSONObject(j);
//                                MomentsModel.ImagesBean imagesBean = new MomentsModel.ImagesBean();
//                                imagesBean.setUrl(jsonObject1.optString("url"));
//                                imagesBeanList.add(imagesBean);
//                            }
//                        }
//
//                        if (weChat.has("comments")){
//                            JSONArray comments = weChat.getJSONArray("comments");
//                            for (int k = 0; k < comments.length(); k++){
//                                JSONObject jsonObject1 = comments.getJSONObject(k);
//                                MomentsModel.CommentsBean commentsBean = new MomentsModel.CommentsBean();
//                                commentsBean.setContent(jsonObject1.optString("content"));
//                                if (jsonObject1.has("sender")){
//                                    JSONObject sender = jsonObject1.getJSONObject("sender");
//                                    MomentsModel.CommentsBean.SenderBeanX senderBean = new MomentsModel.CommentsBean.SenderBeanX();
//                                    senderBean.setAvatar(sender.optString("avatar"));
//                                    senderBean.setNick(sender.optString("nick"));
//                                    commentsBean.setSender(senderBean);
//                                }
//                                commentsBeanList.add(commentsBean);
//                            }
//                        }
//
//                        if (weChat.has("sender")){
//                            JSONObject sender = weChat.optJSONObject("sender");
//                            nick = sender.optString("nick");
//                            avatar = sender.optString("avatar");
//
//                        }
//                    }
//
//                    MomentsModel momentsModel2 = new MomentsModel();
//                    MomentsModel.SenderBean senderBean = new MomentsModel.SenderBean();
//                    senderBean.setAvatar(avatar);
//                    senderBean.setNick(nick);
//                    momentsModel2.setSender(senderBean);
//                    momentsModel2.setContent(content);
//                    momentsModel2.setComments(commentsBeanList);
//                    momentsModel2.setImages(imagesBeanList);
//                    MomentsModels.add(momentsModel2);
//                }
//
//                LogUtils.i(TAG,"--> handleJsons - MomentsModels = "+MomentsModels);
//                LogUtils.i(TAG,"--> handleJsons - MomentsModels.size() = "+MomentsModels.size());
//
//                mRecyclerView.setAdapter(momentsAdapter);
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//        }
//
//    }
//
//}
