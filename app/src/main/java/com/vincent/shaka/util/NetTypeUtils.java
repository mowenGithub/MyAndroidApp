package com.vincent.shaka.util;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import com.vincent.shaka.App;

/**
 * Created by mowen on 5/11/16.
 */
public class NetTypeUtils {
    public static final String NETWORK_CLASS_UNKNOWN = "unknown";
    public static final String NETWORK_CLASS_WIFI = "wifi";
    public static final String NETWORK_CLASS_2_G = "2g";
    public static final String NETWORK_CLASS_3_G = "3g";
    public static final String NETWORK_CLASS_4_G = "4g";

    /**
     * 获取网络类型
     * @return
     */
    public static String getNetworkType() {

        ConnectivityManager manager = (ConnectivityManager) App.getAppContext().getSystemService(App.getAppContext().CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();

        if (info != null) {

            switch (info.getType()) {
                case ConnectivityManager.TYPE_WIFI:
                    return NETWORK_CLASS_WIFI;
                case ConnectivityManager.TYPE_MOBILE:
                    switch (info.getSubtype()) {
                        case TelephonyManager.NETWORK_TYPE_GPRS:
                        case TelephonyManager.NETWORK_TYPE_EDGE:
                        case TelephonyManager.NETWORK_TYPE_CDMA:
                        case TelephonyManager.NETWORK_TYPE_1xRTT:
                        case TelephonyManager.NETWORK_TYPE_IDEN:
                            return NETWORK_CLASS_2_G;
                        case TelephonyManager.NETWORK_TYPE_UMTS:
                        case TelephonyManager.NETWORK_TYPE_EVDO_0:
                        case TelephonyManager.NETWORK_TYPE_EVDO_A:
                        case TelephonyManager.NETWORK_TYPE_HSDPA:
                        case TelephonyManager.NETWORK_TYPE_HSUPA:
                        case TelephonyManager.NETWORK_TYPE_HSPA:
                        case TelephonyManager.NETWORK_TYPE_EVDO_B:
                        case TelephonyManager.NETWORK_TYPE_EHRPD:
                        case TelephonyManager.NETWORK_TYPE_HSPAP:
                            return NETWORK_CLASS_3_G;
                        case TelephonyManager.NETWORK_TYPE_LTE:
                            return NETWORK_CLASS_4_G;
                        default:
                            return NETWORK_CLASS_UNKNOWN;
                    }
                default:
                    return NETWORK_CLASS_UNKNOWN;
            }
        }

        return NETWORK_CLASS_UNKNOWN;
    }
}
