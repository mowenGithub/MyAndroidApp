package com.vincent.shaka.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.vincent.shaka.App;

/**
 * Created by Administrator on 2015/8/9 0009.
 */
public class SharedCacheUtils {
    //用户id
    public static final String GLOBAL_UID = "global_uid";

    private static SharedCacheUtils sharedCacheUtils = new SharedCacheUtils();
    SharedPreferences preference = App.getAppContext().getSharedPreferences(CACHE, Context.MODE_PRIVATE);
    private static final String CACHE = "CacheFile";

    private SharedCacheUtils() {
    }

    public static SharedCacheUtils getSharedCacheUtils() {
        return sharedCacheUtils;
    }
    /**
     * 保存Cookie
     */
    public void savePreference(String key, String value) {
        SharedPreferences.Editor editor = preference.edit();
        editor.putString(key, value);
        editor.apply();
    }

    /**
     * 得到Cookie
     */
    public String getPreference(String key) {
        final String cookieValue = preference.getString(key, "");
        return cookieValue;
    }
}
