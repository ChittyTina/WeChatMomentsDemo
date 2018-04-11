package com.chitty.wechatmomentsdemo.interfaces;

import com.chitty.wechatmomentsdemo.config.UrlHolder;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

/**
 * Created by chitty on 2018/4/11.
 */
public interface MyServerInterface {

    @Headers("Content-Type: application/json")
    @GET(UrlHolder.BASE_URL + "user/jsmith")
    Call<ResponseBody> jsmith();


    @Headers("Content-Type: application/json")
    @GET(UrlHolder.BASE_URL + "user/jsmith/tweets")
    Call<ResponseBody> tweets();

}
