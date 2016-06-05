package com.vincent.shaka.model;

import com.vincent.shaka.BuildConfig;

/**
 * Created by mowen on 5/16/16.
 */
public class GlobalEnv {
    private static GlobalEnv globalEnv = new GlobalEnv();

    private GlobalEnv() {}

    public static GlobalEnv getGlobalEnv() {
        return globalEnv;
    }

    private static final String HOST_RELEASE_HTTPS = "https://www.shake.com";
    private static final String HOST_RELEASE_HTTP = "http://www.shake.com";
    private String HOST_DEBUG = HOST_RELEASE_HTTPS;

    public String getHost() {
        if(BuildConfig.DEBUG) {
            return HOST_DEBUG;
        } else {
            return HOST_RELEASE_HTTPS;
        }
    }

    public void setHostDebug(String host) {
        HOST_DEBUG = host;
    }
}
