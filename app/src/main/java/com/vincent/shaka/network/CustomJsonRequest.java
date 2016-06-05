package com.vincent.shaka.network;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonRequest;

import java.io.UnsupportedEncodingException;

/**
 * Created by mowen on 5/23/16.
 */
public class CustomJsonRequest extends JsonRequest {
    private String cacheKey;
    public CustomJsonRequest(int method, String url, String requestBody, Response.Listener listener, Response.ErrorListener errorListener, String cacheKey) {
        super(method, url, requestBody, listener, errorListener);
        this.cacheKey = cacheKey;
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        String jsonString = null;
        try {
            jsonString = new String(response.data,
                    com.android.volley.toolbox.HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return Response.success(jsonString,
                HttpHeaderParser.parseIgnoreCacheHeaders(response));
    }

    @Override
    public String getCacheKey() {
        return cacheKey;
    }
}
