package com.chitty.wechatmomentsdemo.utils;

import android.text.TextUtils;

import com.chitty.wechatmomentsdemo.BuildConfig;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by chitty on 2018/4/11.
 */
public class OkHttp3Utils {
    private static OkHttpClient okHttpClient = null;
    private static final int DEFAULT_TIMEOUT = 20;

    public  static OkHttpClient getOkHttpSingletonInstance() {
        if (okHttpClient == null) {
            synchronized (OkHttpClient.class) {
                if (okHttpClient == null) {
                    okHttpClient = new OkHttpClient();
                    //设置合理的超时
                    OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder()
                            .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                            .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                            .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                            .addInterceptor(new LoggingInterceptor())//添加请求拦截
                            .retryOnConnectionFailure(true);

                    //如果不是在正式包，添加拦截 打印响应json
                    if (BuildConfig.DEBUG) {
                        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(
                                new HttpLoggingInterceptor.Logger() {
                                    @Override
                                    public void log(String message) {
                                        if (TextUtils.isEmpty(message)) return;
                                        String s = message.substring(0, 1);
                                        //如果收到响应是json才打印
                                        if ("{".equals(s) || "[".equals(s)) {
                                            LogUtils.i("收到响应: " + message);
                                        }
                                    }
                                });
                        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
                        httpBuilder.addInterceptor(logging);
                    }
                    okHttpClient = httpBuilder
                            .protocols(Collections.singletonList(Protocol.HTTP_1_1))
                            .build();
                }
            }
        }
        return okHttpClient;
    }
}
