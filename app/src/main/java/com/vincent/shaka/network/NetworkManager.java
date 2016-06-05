package com.vincent.shaka.network;

import android.net.Uri;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vincent.shaka.App;
import com.vincent.shaka.BuildConfig;
import com.vincent.shaka.R;
import com.vincent.shaka.model.Constants;
import com.vincent.shaka.model.GlobalEnv;
import com.vincent.shaka.model.ServerMap;
import com.vincent.shaka.model.param.BaseParam;
import com.vincent.shaka.model.param.CommonParam;
import com.vincent.shaka.model.response.BaseResponse;
import com.vincent.shaka.util.LogUtils;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import okhttp3.OkHttpClient;

/**
 *
 */
public class NetworkManager {
    private static NetworkManager networkManager = new NetworkManager();
    private RequestQueue mRequestQueue;
    private RequestQueue mHttpsRequestQueue;
    private Gson gson;
    private static final String HOST_NAME = "shaka.com";

    private NetworkManager() {
        //TODO 对主机校验
        mRequestQueue = Volley.newRequestQueue(App.getAppContext(), new OkHttp3Stack(getOkHttpClientBuilder(false).build()));

        mHttpsRequestQueue = Volley.newRequestQueue(App.getAppContext(), new OkHttp3Stack(getOkHttpClientBuilder(true).build()));
        gson = new GsonBuilder().serializeNulls().create();
    }

    public static NetworkManager getNetworkManager() {
        return networkManager;
    }

    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }

    public RequestQueue getHttpsRequestQueue() {
        return mHttpsRequestQueue;
    }

    public void cancelAll(Object tag) {
        mRequestQueue.cancelAll(tag);
        mHttpsRequestQueue.cancelAll(tag);
    }

    public void addPostRequest(ServerMap requestServer, BaseParam param, NetworkCallback netWorkCallback) {
        addRequest(Request.Method.POST, requestServer, param, netWorkCallback, false);
    }

    public void addGetRequest(ServerMap requestServer, BaseParam param, NetworkCallback netWorkCallback) {
        addRequest(Request.Method.GET, requestServer, param, netWorkCallback, false);
    }

    public void addPostRequestNeedCache(ServerMap requestServer, BaseParam param, NetworkCallback netWorkCallback) {
        addRequest(Request.Method.POST, requestServer, param, netWorkCallback, true);
    }

    public void addGetRequestNeedCache(ServerMap requestServer, BaseParam param, NetworkCallback netWorkCallback) {
        addRequest(Request.Method.GET, requestServer, param, netWorkCallback, true);
    }

    /**
     * 网络请求的方法
     * @param requestServer           网络url
     * @param param   请求的json参数
     * @param netWorkCallback      返回的监听
     */
    private void addRequest(int method, final ServerMap requestServer, BaseParam param, final NetworkCallback netWorkCallback, final boolean shouldCache) {

        CommonParam commonParam = new CommonParam();
        HashMap<String, String> commonParamMap = commonParam.getCommonParam();
        String paramJson = null;
        String sign = "";
        try {
//            if(param != null) {
                paramJson = gson.toJson(param);
                sign = NetworkAccessStrategy.getSignature(gson.fromJson(paramJson, HashMap.class), Constants.NETWORK_SECRET);
                commonParamMap.put("token", sign);
//            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String url = null;
        String cacheKey = null;
        if(requestServer.getUrl().startsWith("http://") || requestServer.getUrl().startsWith("https://")) {
            url = requestServer.getUrl();
            if(shouldCache) {
                cacheKey = url + sign;
            }
        } else {
            Uri.Builder uriBuilder = Uri.parse(GlobalEnv.getGlobalEnv().getHost() + requestServer.getUrl()).buildUpon();
            for(Map.Entry<String, String> entry : commonParamMap.entrySet()) {
                uriBuilder.appendQueryParameter(entry.getKey(), entry.getValue());
            }
            url = uriBuilder.build().toString();

            //去掉cache时的无关参数
            commonParamMap.remove("net");
            commonParamMap.remove("osv");
            commonParamMap.remove("ts");

            if(shouldCache) {
                cacheKey = requestServer.getUrl() + commonParamMap.toString();
            }
        }

        final String finalCacheKey = cacheKey;
        CustomJsonRequest jsonRequest= new CustomJsonRequest(method, url, paramJson, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                LogUtils.d("RESPONSE: ", response);
                try {
                    BaseResponse baseResponse = gson.fromJson(response, requestServer.getResponseClass());
                    if(baseResponse.code != 0 && shouldCache) {
                        //当返回结果不正常的时候，删除缓存
                        if(requestServer.isHttps()) {
                            mHttpsRequestQueue.getCache().invalidate(finalCacheKey, true);
                        } else {
                            mRequestQueue.getCache().invalidate(finalCacheKey, true);
                        }
                    }
                    netWorkCallback.onResponse(new NetworkResult(requestServer, baseResponse));
                } catch (Exception e) {
                    LogUtils.e("Response is not a jsonString or not match Object:  " + e.getStackTrace());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                netWorkCallback.onErrorResponse(error);
            }
        }, finalCacheKey);
        jsonRequest.setShouldCache(shouldCache);

        LogUtils.d("URL: ", url);
        if(paramJson != null) {
            LogUtils.d("PARAM: ", paramJson);
        }

        if(finalCacheKey != null) {
            LogUtils.d("CacheKey: ", finalCacheKey);
        }

        jsonRequest.setTag(this);
        if(requestServer.isHttps()) {
            mHttpsRequestQueue.add(jsonRequest);
        } else {
            mRequestQueue.add(jsonRequest);
        }
    }

    protected Response.ErrorListener errorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        };
    }

    /**
     * only allow self CertificateFactory
     * @return
     */
    private static SSLSocketFactory createSSLSocketFactoryNew() {
        try {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            InputStream caInput = App.getAppContext().getResources().openRawResource(R.raw.shaka_com);
            Certificate ca;
            try {
                ca = cf.generateCertificate(caInput);
            } finally {
                caInput.close();
            }

            // Create a KeyStore containing our trusted CAs
            String keyStoreType = KeyStore.getDefaultType();
            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
            keyStore.load(null, null);
            keyStore.setCertificateEntry("ca", ca);

            // Create a TrustManager that trusts the CAs in our KeyStore
            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
            tmf.init(keyStore);

            // Create an SSLContext that uses our TrustManager
            SSLContext context = SSLContext.getInstance("TLSv1","AndroidOpenSSL");
            context.init(null, tmf.getTrustManagers(), null);

            return context.getSocketFactory();
        } catch (CertificateException e) {
            LogUtils.e(e.getStackTrace().toString());
        } catch (IOException e) {
            LogUtils.e(e.getStackTrace().toString());
        } catch (NoSuchAlgorithmException e) {
            LogUtils.e(e.getStackTrace().toString());
        } catch (KeyStoreException e) {
            LogUtils.e(e.getStackTrace().toString());
        } catch (KeyManagementException e) {
            LogUtils.e(e.getStackTrace().toString());
        } catch (NoSuchProviderException e) {
            LogUtils.e(e.getStackTrace().toString());
        }
        return null;
    }

    private OkHttpClient.Builder getOkHttpClientBuilder(boolean isHttps) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if(isHttps) {
//            builder.sslSocketFactory(createSSLSocketFactoryNew());
        }
        if(BuildConfig.DEBUG) {
            builder.addNetworkInterceptor(new StethoInterceptor());
        } else {
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    if(HOST_NAME.equals(hostname)) {
                        return true;
                    }
                    return false;
                }
            });
        }

        return builder;
    }
}
