package com.xanthus.design;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;

import com.xanthus.design.api.LApi;
import com.xanthus.design.bean.User;
import com.xanthus.design.utils.GlideImageLoader;
import com.xanthus.design.utils.SPHelper;

import java.lang.ref.WeakReference;

import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ImageLoader;
import cn.finalteam.galleryfinal.ThemeConfig;

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

        ThemeConfig theme = new ThemeConfig.Builder()
                .build();
        FunctionConfig functionConfig = new FunctionConfig.Builder()
                .setEnableCamera(false)
                .setEnableEdit(false)
                .setEnableCrop(true)
                .setEnableRotate(false)
                .setCropSquare(true)
                .setEnablePreview(false)
                .build();

        ImageLoader imageloader = new GlideImageLoader();
        CoreConfig coreConfig = new CoreConfig.Builder(this, imageloader, theme)
                .setFunctionConfig(functionConfig)
                .build();
        GalleryFinal.init(coreConfig);
    }

    public static DesignApp app;

}
