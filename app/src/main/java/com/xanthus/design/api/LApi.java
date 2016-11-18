package com.xanthus.design.api;

import android.content.Context;

import com.xanthus.design.bean.User;
import com.xanthus.design.bean.Wrapper;
import com.xanthus.design.utils.SPHelper;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
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

public enum LApi {
    INSTANCE;
    private LService lService;

    LApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.123.232:8080/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                //.client(genericClient())
                .build();
        lService = retrofit.create(LService.class);
    }

    public void update(Context context) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.123.232:8080/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(genericClient(context))
                .build();
        lService = retrofit.create(LService.class);
    }

    public Observable<Wrapper<User>> login(String uname, String pwd) {
        return lService
                .login(uname, pwd)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Wrapper<Long>> regist(String uname, String pwd) {
        return lService
                .regist(uname, pwd)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Wrapper<Long>> addTopic(String content) {
        return lService
                .addTopic(content)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private OkHttpClient genericClient(final Context context) {
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request()
                                .newBuilder()
                                .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                                .addHeader("Accept-Encoding", "gzip, deflate")
                                .addHeader("Connection", "keep-alive")
                                .addHeader("Accept", "*/*")
                                .addHeader("Cookie", "add cookies here")
                                .addHeader("auth", SPHelper.getTokenToken(context))
                                .build();
                        return chain.proceed(request);
                    }

                })
                .build();

        return httpClient;
    }
}
