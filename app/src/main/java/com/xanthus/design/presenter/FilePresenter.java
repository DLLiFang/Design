package com.xanthus.design.presenter;

import com.xanthus.design.api.LApi;
import com.xanthus.design.api.LSubscriber;
import com.xanthus.design.bean.User;
import com.xanthus.design.bean.Wrapper;
import com.xanthus.design.constract.FileConstract;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
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
        Subscription subscribe = Observable.timer(2000, TimeUnit.MILLISECONDS) //todo http Request
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        mView.refreshUI(null);
                    }
                });
        compositeSubscription.add(subscribe);
    }

    @Override
    public void uploadFile(final String path) {

        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), new File(path));

        Subscription subscribe1 = LApi.INSTANCE.upload(requestBody).subscribe(new LSubscriber<Wrapper<User>>() {
            @Override
            public void onNext(Wrapper<User> userWrapper) {
                mView.showToast(userWrapper.getMessage());
            }
        });
        compositeSubscription.add(subscribe1);

    }
}
