package com.xanthus.design;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;

import com.xanthus.design.api.LApi;
import com.xanthus.design.bean.User;
import com.xanthus.design.utils.SPHelper;

import java.lang.ref.WeakReference;

/**
 * Created by liyiheng on 2016/11/18.
 */

public class DesignApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        String token = SPHelper.getTokenToken(DesignApp.this);
//        if (!TextUtils.isEmpty(token)) {
//            LApi.INSTANCE.update(DesignApp.this);
//        }
        SPHelper.getProfile(this);

    }

    public static DesignApp app;

}
