package com.xanthus.design.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.xanthus.design.bean.User;

/**
 * Created by liyiheng on 2016/11/18.
 */

public class SPHelper {
    private static final String SP_NAME = "user_info";
    private static final String TOKEN_KEY = "auth";


    public static void putString(Context context, String k, String v) {
        SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(k, v);
        edit.apply();
    }

    public static String getString(Context context, String k) {
        SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        return sp.getString(k, "");
    }

    public static void saveToken(Context context, String token) {
        tokenStr = token;
        putString(context, TOKEN_KEY, token);
    }

    private static String tokenStr;

    public static String getTokenToken(Context context) {
        if (TextUtils.isEmpty(tokenStr)) {
            if (context==null)return "";
            tokenStr = getString(context, TOKEN_KEY);
        }
        return tokenStr;
    }

    private static User _user;

    public static void saveProfile(Context context, User user) {
        _user = user;
        String s = new Gson().toJson(user, User.class);
        putString(context, "profile", s);
    }

    public static User getProfile(Context context) {
        if (_user == null) {
            String profile = getString(context, "profile");
            _user = new Gson().fromJson(profile, User.class);
        }
        if (_user == null) {
            _user = new User();
        }
        return _user;
    }
}
