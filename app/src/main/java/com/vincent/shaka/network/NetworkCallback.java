package com.vincent.shaka.network;

import com.android.volley.VolleyError;

/**
 * Created by mowen on 5/10/16.
 */
public interface NetworkCallback {
    void onResponse(NetworkResult netWorkResult);
    void onErrorResponse(VolleyError error);
}
