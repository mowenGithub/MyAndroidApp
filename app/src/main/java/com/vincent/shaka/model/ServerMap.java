package com.vincent.shaka.model;

import com.vincent.shaka.model.response.BaseResponse;
import com.vincent.shaka.model.response.GithubResponse;
import com.vincent.shaka.model.response.TestResponse;

/**
 *
 */
public enum ServerMap {

    TEST_BING("https://www.baidu.com/", TestResponse.class, false),
    TEST_NAME("hello", TestResponse.class, false),
    TEST_NAME_Q("http://192.168.1.4:5000/hello", TestResponse.class, false),
    TEST_HTTP("http://www.weather.com.cn/data/sk/101010100.html", TestResponse.class, false),
    TEST_HTTPS_GITHUB("https://raw.githubusercontent.com/mowenGithub/AndroidTestCode/test/jsontest", GithubResponse.class, true),
    ;

    ServerMap(String name, Class<? extends BaseResponse> responseClass, boolean isHttps) {
        this.url = name;
        this.responseClass = responseClass;
        this.isHttps = isHttps;
    }

    private String url;
    private Class<? extends BaseResponse> responseClass;
    private boolean isHttps;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Class<? extends BaseResponse> getResponseClass() {
        return responseClass;
    }

    public void setResponseClass(Class<? extends BaseResponse> responseClass) {
        this.responseClass = responseClass;
    }

    public boolean isHttps() {
        return isHttps;
    }

    public void setHttps(boolean https) {
        isHttps = https;
    }
}
