package com.vincent.shaka.hybridlib;

import android.os.Handler;
import android.os.Looper;
import android.webkit.WebView;

import org.json.JSONObject;

import java.lang.ref.WeakReference;

/**
 * Created by mowenlong on 16-5-31.
 */
public class Callback {
    private static Handler mHandler = new Handler(Looper.getMainLooper());
    private static final String CALLBACK_JS_FORMAT_SUCCESS = "javascript:JSBridge.onFinish('%s', %s);";
    private static final String CALLBACK_JS_FORMAT_FAILURE = "javascript:JSBridge.onFailure('%s', %s);";
    private static final String CALLBACK_JS_FORMAT_BACK = "javascript:JSBridge.onNatiBack();";
    private String mPort;
    private WeakReference<WebView> mWebViewRef;

    public Callback(WebView view, String port) {
        mWebViewRef = new WeakReference<>(view);
        mPort = port;
    }


    public void apply(JSONObject jsonObject) {
        final String execJs = String.format(CALLBACK_JS_FORMAT_SUCCESS, mPort, String.valueOf(jsonObject));
        if (mWebViewRef != null && mWebViewRef.get() != null) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mWebViewRef.get().loadUrl(execJs);
                }
            });
        }
    }
}
