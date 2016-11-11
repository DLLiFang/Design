package com.xanthus.design.presenter;

import com.xanthus.design.constract.FileConstract;

import java.util.concurrent.TimeUnit;

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
        Subscription subscribe = Observable.timer(1, TimeUnit.SECONDS)//todo http Request
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        mView.showToast(path);
                    }
                });
        compositeSubscription.add(subscribe);
    }
}
