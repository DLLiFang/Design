package com.xanthus.design.api;

import com.xanthus.design.bean.FileBean;
import com.xanthus.design.bean.Topic;
import com.xanthus.design.bean.User;
import com.xanthus.design.bean.Wrapper;
import com.xanthus.design.bean.Wrapper2;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;
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


    @FormUrlEncoded
    @PUT("user/")
    Observable<Wrapper<User>> modifyNickname(@Field("nickname") String nickname);

    @FormUrlEncoded
    @PUT("user/")
    Observable<Wrapper<User>> modifyPhone(@Field("phone") String phone);

    @FormUrlEncoded
    @PUT("user/")
    Observable<Wrapper<User>> modifyGender(@Field("gender") int gender);//"1 male，2 female"

    @GET("topic")
    Observable<Wrapper2<Topic>> getTopicList(@Query("page") int page, @Query("pageSize") int pageSize);


    @Multipart
    @POST("file/")
    Observable<Wrapper<FileBean>> uploadFile(@Part("description") RequestBody descript,
                                             @Part MultipartBody.Part file,
                                             @Part("type") int type);//0. common 1.avatar

    @GET("file/list/")
    Observable<Wrapper2<FileBean>> getFileList(@Query("page") int page, @Query("pageSize") int pageSize);


}
