package com.xanthus.design.api;

import android.util.Log;

import rx.Subscriber;

/**
 * Created by liyiheng on 2016/11/19.
 */

public abstract class LSubscriber<T> extends Subscriber<T> {
    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        Log.e("LSubscriber","onError:" + e.getMessage());
    }


}
