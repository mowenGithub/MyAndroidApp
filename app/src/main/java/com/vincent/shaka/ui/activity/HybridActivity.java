package com.vincent.shaka.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.webkit.JsPromptResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.vincent.shaka.R;
import com.vincent.shaka.hybridlib.CommonModule;
import com.vincent.shaka.hybridlib.JSBridge;

public class HybridActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hybrid);
        WebView mWebView = (WebView) findViewById(R.id.webview);
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setSavePassword(false);
        if(Build.VERSION.SDK_INT < 17) {
            fixWebView(mWebView);
        }
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
                result.confirm(JSBridge.callJava(HybridActivity.this, view, message));
                return true;
            }
        });
        mWebView.loadUrl("file:///android_asset/index.html");

        JSBridge.register("CommonModule", CommonModule.class);
    }

    /**
     * 移除有风险的Webview系统隐藏接口
     * @param mWebView
     */
    private void fixWebView(WebView mWebView) {
        mWebView.removeJavascriptInterface("searchBoxJavaBridge_");
        mWebView.removeJavascriptInterface("accessibilityTraversal");
        mWebView.removeJavascriptInterface("accessibility");
    }
}
