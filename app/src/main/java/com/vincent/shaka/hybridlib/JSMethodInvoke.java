package com.vincent.shaka.hybridlib;

import android.os.Handler;
import android.os.Looper;
import android.webkit.WebView;

import java.lang.ref.WeakReference;

/**
 * Created by mowenlong on 16-5-31.
 */
public class JSMethodInvoke {
    private static Handler mHandler = new Handler(Looper.getMainLooper());
    private static final String CALLBACK_JS_FORMAT_BACK = "javascript:JSBridge.onNatiBack();";
    private String mPort;
    private WeakReference<WebView> mWebViewRef;

    public JSMethodInvoke(WebView view) {
        mWebViewRef = new WeakReference<>(view);
    }


    public void onNatiBack() {
        if (mWebViewRef != null && mWebViewRef.get() != null) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mWebViewRef.get().loadUrl(CALLBACK_JS_FORMAT_BACK);
                }
            });
        }
    }
}
