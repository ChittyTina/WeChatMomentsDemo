package com.chitty.wechatmomentsdemo.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.bumptech.glide.Glide;
import com.bumptech.glide.integration.okhttp.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.chitty.wechatmomentsdemo.config.UrlHolder;
import com.chitty.wechatmomentsdemo.interfaces.MyServerInterface;
import com.chitty.wechatmomentsdemo.utils.ACache;
import com.chitty.wechatmomentsdemo.utils.LogUtils;
import com.chitty.wechatmomentsdemo.utils.OkHttp3Utils;
import com.chitty.wechatmomentsdemo.utils.OkHttpClientHelper;
import com.google.gson.Gson;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import java.io.InputStream;
import java.util.Stack;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by chitty on 2018/4/11.
 */
public final class MyApplication extends Application {

    private static final String TAG = MyApplication.class.getSimpleName();

    private static MyApplication singleton;
    private static Retrofit retrofit;
    private static Gson gson;
    private static ACache mACache;
    private static Stack<Activity> activityStack;

    private Context mContext = this;
    private MyServerInterface serverInterface = null;
    private RefWatcher mRefWatcher;

    public static ACache getACache() {
        return mACache;
    }

    public static Gson getGson() {
        return gson;
    }

  @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base); MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        mRefWatcher = LeakCanary.install(this);
        mACache=ACache.get(this);
        gson = new Gson();
        //初始化Retrofit
        initRetrofit();

    }

    public static RefWatcher getRefWatcher(Context context) {
        MyApplication application = (MyApplication) context.getApplicationContext();
        return application.mRefWatcher;
    }

    private void initRetrofit() {
        OkHttpClient client = OkHttp3Utils.getOkHttpSingletonInstance();
        LogUtils.i(TAG, "-- MyApplication --->initRetrofit: " + client.toString());

        retrofit = new Retrofit.Builder()
                .baseUrl(UrlHolder.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//添加rxjava转换器
                .build();

        serverInterface = retrofit.create(MyServerInterface.class);
    }

    public MyServerInterface getMyServerInterface() {
        return serverInterface;
    }

//    private void initGlide() {
//        //设置Glide网络访问方式
//        Glide.get(this).register(GlideUrl.class, InputStream.class,
//                new OkHttpUrlLoader.Factory(OkHttpClientHelper.getOkHttpSingletonInstance()));
//    }

    public static MyApplication getInstance() {
        if (singleton == null) {
            singleton = new MyApplication();
        }
        return singleton;
    }

    /**
     * 把Activity添加到栈中
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
        activityStack.add(activity);
        LogUtils.e(activity.getClass().getName(), "当前回退栈的Activity数量:" + activityStack.size());
    }

    /**
     * 当前Activity
     */
    public Activity currentActivity() {
        Activity activity = activityStack.lastElement();
        return activity;
    }

    /**
     * 结束当前Activity
     */
    public void finishCurrentActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                LogUtils.e("gnifeifeiing", "已结束：" + activityStack.get(i).getClass().getName());
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    /**
     * 退出应用
     */
    public void ExitApp() {
        try {
            finishAllActivity();
        } catch (Exception e) {

        }
    }

    public static Retrofit retrofit() {
        return retrofit;
    }
}
