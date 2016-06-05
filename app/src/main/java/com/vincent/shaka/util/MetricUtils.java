package com.vincent.shaka.util;

import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.vincent.shaka.App;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wenlongmo on 16-1-14.
 */
public class MetricUtils {

    public final static String METRIC_WIDTH = "width";
    public final static String METRIC_HEIGHT = "height";
    public final static String METRIC_DPI = "dpi";
    public MetricUtils() {
    }

    public static Map<String, Integer> getDisplayMetrics() {
        DisplayMetrics metric = new DisplayMetrics();
        ((WindowManager) App.getAppContext().getSystemService(App.getAppContext().WINDOW_SERVICE)).getDefaultDisplay().getMetrics(metric);
//        int width = metric.widthPixels;  // 屏幕宽度（像素）
//        int height = metric.heightPixels;  // 屏幕高度（像素）
//        float density = metric.density;  // 屏幕密度（0.75 / 1.0 / 1.5）
//        int densityDpi = metric.densityDpi;  // 屏幕密度DPI（120 / 160 / 240）
        Map<String, Integer> result = new HashMap<String, Integer>();
        result.put(METRIC_WIDTH, metric.widthPixels);
        result.put(METRIC_HEIGHT, metric.heightPixels);
        result.put(METRIC_DPI, metric.densityDpi);
        return result;
    }
}
