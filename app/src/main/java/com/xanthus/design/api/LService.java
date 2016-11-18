package com.xanthus.design.api;

import com.xanthus.design.bean.User;
import com.xanthus.design.bean.Wrapper;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by liyiheng on 16/11/11.
 */

public interface LService {
    @FormUrlEncoded
    @POST("user/auth/")
    Observable<Wrapper<User>> login(@Field("username") String userName, @Field("password") String pwd);

    @FormUrlEncoded
    @POST("user/")
    Observable<Wrapper<Long>> regist(@Field("username") String userName, @Field("password") String pwd);

    @FormUrlEncoded
    @POST("topic/")
    Observable<Wrapper<Long>> addTopic(@Field("content") String content);
}
