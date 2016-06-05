package com.vincent.shaka.network;

import com.vincent.shaka.model.ServerMap;
import com.vincent.shaka.model.response.BaseResponse;

/**
 * Created by mowen on 5/10/16.
 */
public class NetworkResult {
    private ServerMap requestKey;
    private BaseResponse baseResponse;

    public NetworkResult(ServerMap requestKey, BaseResponse baseResponse) {
        this.requestKey = requestKey;
        this.baseResponse = baseResponse;
    }

    public ServerMap getRequestKey() {
        return requestKey;
    }

    public BaseResponse getResponse() {
        return baseResponse;
    }
}
