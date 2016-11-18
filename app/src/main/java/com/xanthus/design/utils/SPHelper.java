package com.xanthus.design.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

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
        edit.commit();
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
            tokenStr = getString(context, TOKEN_KEY);
        }
        return tokenStr;
    }
}
