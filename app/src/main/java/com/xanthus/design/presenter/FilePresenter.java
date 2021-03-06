package com.xanthus.design.presenter;

import com.xanthus.design.api.LApi;
import com.xanthus.design.api.LSubscriber;
import com.xanthus.design.bean.FileBean;
import com.xanthus.design.bean.User;
import com.xanthus.design.bean.Wrapper;
import com.xanthus.design.bean.Wrapper2;
import com.xanthus.design.constract.FileConstract;

import org.json.JSONObject;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by liyiheng on 16/11/9.
 */

public class FilePresenter implements FileConstract.FilePresenter {
    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {
        mView = null;
        compositeSubscription.unsubscribe();
    }

    private FileConstract.FileView mView;
    private CompositeSubscription compositeSubscription;

    public FilePresenter(FileConstract.FileView mView) {
        this.mView = mView;
        this.compositeSubscription = new CompositeSubscription();
    }

    @Override
    public void initData() {
        Subscription subscribe1 = LApi.INSTANCE.getFiles(0, 20).subscribe(new LSubscriber<Wrapper2<FileBean>>() {

            @Override
            public void onNext(Wrapper2<FileBean> fileBeanWrapper2) {
                mView.refreshUI(fileBeanWrapper2.getResult());
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mView.refreshUI(null);
                mView.showToast(e.getMessage());
            }
        });
        compositeSubscription.add(subscribe1);

    }

    @Override
    public void uploadFile(final String path) {
        final File file = new File(path);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/otcet-stream"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("avatar", file.getName(), requestFile);
        String descriptionString = "This is a description.";
        RequestBody description = RequestBody.create(MediaType.parse("multipart/form-data"), descriptionString);


        Subscription subscribe1 = LApi.INSTANCE.upload(description, body, 0).subscribe(new LSubscriber<Wrapper<FileBean>>() {
            @Override
            public void onNext(Wrapper<FileBean> userWrapper) {
                FileBean result = userWrapper.getResult();

                mView.showToast(userWrapper.getMessage());
            }
        });
        compositeSubscription.add(subscribe1);

    }
}
