package com.xanthus.design.api;

import com.xanthus.design.bean.User;

import retrofit2.http.Field;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by liyiheng on 16/11/11.
 */

public interface UserService {
    @POST("/user")
    Observable<User> login(@Field("username") String userName, @Field("password") String pwd);
}
