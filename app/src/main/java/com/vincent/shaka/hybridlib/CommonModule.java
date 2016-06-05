package com.vincent.shaka.hybridlib;

import android.app.Activity;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mowenlong on 16-5-31.
 */
public class CommonModule implements IModule {
    public static void showToast(Activity activity, WebView webView, JSONObject param, final Callback callback) {
        String message = param.optString("msg");
        Toast.makeText(webView.getContext(), message, Toast.LENGTH_SHORT).show();
        if (null != callback) {
            try {
                JSONObject object = new JSONObject();
                object.put("key", "value");
                object.put("key1", "value1");
                callback.apply(getJSONObject(0, "ok", object));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void onBack(Activity activity, WebView webView, JSONObject param, final Callback callback) {
        Toast.makeText(webView.getContext(), "Activity back", Toast.LENGTH_SHORT).show();
        ViewGroup parent = (ViewGroup) webView.getParent();
        if (parent != null) {
            parent.removeView(webView);
        }
        webView.removeAllViews();
        activity.finish();
    }

    private static JSONObject getJSONObject(int code, String msg, JSONObject result) {
        JSONObject object = new JSONObject();
        try {
            object.put("code", code);
            object.put("codeMsg", msg);
            object.putOpt("data", result);
            return object;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
