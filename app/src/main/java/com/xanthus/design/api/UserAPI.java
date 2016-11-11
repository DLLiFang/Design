package com.xanthus.design.api;

import com.xanthus.design.bean.User;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Singleton by enumeration.
 * Created by liyiheng on 16/11/11.
 */

public enum UserAPI {
    INSTANCE;
    private UserService userService;

    UserAPI() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("domain")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        userService = retrofit.create(UserService.class);
    }

    public Observable<User> login(String uname, String pwd) {
        return userService
                .login(uname, pwd)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
