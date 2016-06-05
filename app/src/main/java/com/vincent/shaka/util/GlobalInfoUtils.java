package com.vincent.shaka.util;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import com.vincent.shaka.App;

import java.util.Locale;

/**
 * Created by mowen on 5/11/16.
 */
public class GlobalInfoUtils {

    /**
     * 获得手机端版本号
     */
    public static int getAppVersionCode() {
        int currentVersionCode = 0;
        PackageManager manager = App.getAppContext().getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(App.getAppContext().getPackageName(), 0);
            currentVersionCode = info.versionCode; // 版本名
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch blockd
            e.printStackTrace();
        }
        return currentVersionCode;
    }

    /**
     * 获得手机端版本号名
     */
    public static String getAppVersionName() {
        String versionName = "";
        PackageManager manager = App.getAppContext().getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(App.getAppContext().getPackageName(), 0);
            versionName = info.versionName; // 版本名
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch blockd
            e.printStackTrace();
        }
        return versionName;
    }

    /**
     * 获取手机IMEI号 设备号 deviceid
     * android.permission.READ_PHONE_STATE
     * @return
     */
    public static String getPhoneDID() {
        return "";
    }

//    @AfterPermissionGranted(PermissionMap.RC_PHONE_STATE_PERM)
//    public static String phoneDidTask() {
//        if (EasyPermissions.hasPermissions(App.getAppContext(), Manifest.permission.READ_PHONE_STATE)) {
//            // Have permission, do the thing!
//            TelephonyManager tm = (TelephonyManager)App.getAppContext().getSystemService(App.getAppContext().TELEPHONY_SERVICE);
//            return tm.getDeviceId();
//        } else {
//            // Ask for one permission
//            EasyPermissions.requestPermissions(App.getAppContext(), App.getAppContext().getString(R.string.read_phone_state),
//                    PermissionMap.RC_PHONE_STATE_PERM, Manifest.permission.READ_PHONE_STATE);
//            return "";
//        }
//    }

    /**
     * 安装下载的渠道
     * @return
     */
    public static String getChannel() {
        //TODO 需加密 需优化
        return "google play";
    }

    /**
     * 获取手机的类型
     * @return
     */
    public static String getPhoneModel() {
        return Build.MODEL;
    }

    /**
     * os版本号 例如 22
     * @return
     */
    public static int getSDKVersion() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * os版本号 例如 5.1.1
     * @return
     */
    public static String getRelease() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 系统语言 eg: en
     * @return
     */
    public static String getLanguage() {
        return Locale.getDefault().getLanguage();
    }

    /**
     * 网络类型 eg: wifi 3G 2G
     * @return
     */
    public static String getNetType() {
        return NetTypeUtils.getNetworkType();
    }
}
