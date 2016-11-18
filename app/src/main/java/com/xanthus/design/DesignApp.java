package com.xanthus.design;

import android.app.Application;
import android.text.TextUtils;

import com.xanthus.design.api.LApi;
import com.xanthus.design.utils.SPHelper;

/**
 * Created by liyiheng on 2016/11/18.
 */

public class DesignApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        String token = SPHelper.getTokenToken(DesignApp.this);
        if (!TextUtils.isEmpty(token)){
            LApi.INSTANCE.update(DesignApp.this);
        }
    }
}
