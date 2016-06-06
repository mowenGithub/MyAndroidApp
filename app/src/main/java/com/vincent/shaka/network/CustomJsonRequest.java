package com.vincent.shaka.network;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by mowen on 5/23/16.
 */
public class CustomJsonRequest extends StringRequest {
    private String cacheKey;
    private Map<String, String> requestParams;

    public CustomJsonRequest(int method, String url, Map<String, String> requestParams, Response.Listener listener, Response.ErrorListener errorListener, String cacheKey) {
        super(method, url, listener, errorListener);
        this.requestParams = requestParams;
        this.cacheKey = cacheKey;
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        String parsed;
        try {
            parsed = new String(response.data, com.android.volley.toolbox.HttpHeaderParser.parseCharset(response.headers));
        } catch (UnsupportedEncodingException e) {
            parsed = new String(response.data);
        }
        return Response.success(parsed, HttpHeaderParser.parseIgnoreCacheHeaders(response));
    }

    /**
     * 重写getParams post的数据是Form Data,
     * 而不重写以像JsonObjectRequest里面直接传jsonRequest的数据形式是Request Payload
     * 服务器解析的策略不同
     * @return
     */
    @Override
    protected Map<String, String> getParams()
    {
        return requestParams;
    }

    @Override
    public String getCacheKey() {
        return cacheKey;
    }
}
