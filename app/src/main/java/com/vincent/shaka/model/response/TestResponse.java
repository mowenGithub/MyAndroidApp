package com.vincent.shaka.model.response;

import java.io.Serializable;

/**
 * Created by mowen on 5/10/16.
 */
public class TestResponse extends BaseResponse {
    public Weatherinfo weatherinfo;
    public class Weatherinfo implements Serializable {
        public String city;
    }
}
