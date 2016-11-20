package com.xanthus.design.api;

import com.xanthus.design.DesignApp;
import com.xanthus.design.bean.FileBean;
import com.xanthus.design.bean.Topic;
import com.xanthus.design.bean.User;
import com.xanthus.design.bean.Wrapper;
import com.xanthus.design.bean.Wrapper2;
import com.xanthus.design.utils.SPHelper;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
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
       update();
    }

    public void update() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(LConstants.DOMAIN)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(genericClient())
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

    public Observable<Wrapper<User>> modifyNickname(String nickname) {
        return lService
                .modifyNickname(nickname)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Wrapper<User>> modifyPhone(String phone) {
        return lService
                .modifyPhone(phone)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * @param gender 1.male 2.female
     * @return
     */
    public Observable<Wrapper<User>> modifyGender(int gender) {
        return lService
                .modifyGender(gender)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Wrapper<Long>> addTopic(String content) {
        return lService
                .addTopic(content)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Wrapper2<Topic>> getTopics(int page, int pageSize) {
        return lService.getTopicList(page, pageSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Wrapper2<FileBean>> getFiles(int page, int pageSize) {
        return lService.getFileList(page, pageSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     *
     * @param type 0.common files; 1. avatar
     * @return
     */
    public Observable<Wrapper<FileBean>> upload(RequestBody desc, MultipartBody.Part file,int type) {
        return lService.uploadFile(desc, file,type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private OkHttpClient genericClient() {
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
                                //.addHeader("Cookie", "add cookies here")
                                .addHeader("auth", SPHelper.getTokenToken(DesignApp.app))
                                .build();
                        return chain.proceed(request);
                    }

                })
                .build();

        return httpClient;
    }

}
