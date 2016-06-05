package com.vincent.shaka.util;

import android.content.Context;
import android.widget.Toast;

import com.vincent.shaka.App;

/**
 * Created by wenlongmo on 15-6-27.
 */
public class ToastUtils {
    private ToastUtils() {
    }

    private static void show(Context context, int resId, int duration) {
        Toast.makeText(context, resId, duration).show();
    }

    private static void show(Context context, String message, int duration) {
        Toast.makeText(context, message, duration).show();
    }

    public static void showShort(int resId) {
        Toast.makeText(App.getAppContext(), resId, Toast.LENGTH_SHORT).show();
    }

    public static void showShort(String message) {
        Toast.makeText(App.getAppContext(), message, Toast.LENGTH_SHORT).show();
    }

    public static void showLong(int resId) {
        Toast.makeText(App.getAppContext(), resId, Toast.LENGTH_LONG).show();
    }

    public static void showLong(String message) {
        Toast.makeText(App.getAppContext(), message, Toast.LENGTH_LONG).show();
    }
}
