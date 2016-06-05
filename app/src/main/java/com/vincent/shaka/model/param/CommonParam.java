package com.vincent.shaka.model.param;

import com.vincent.shaka.util.GlobalInfoUtils;
import com.vincent.shaka.util.SharedCacheUtils;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by mowen on 5/23/16.
 */
public class CommonParam implements Serializable {
    //用户id
    public String uid;
    //app version
    public String appv;
    //App channel 渠道号
    public String ch;
    //设备类型 device type eg: nexus 5
    public String dt;
    //手机语言类型 eg: en
    public String la;
    //网络类型 eg: wifi 3g 2g
    public String net;
    //操作系统名称
    public final String osn = "android";
    //操作系统版本号 eg: 5.1.1
    public String osv;
    //请求时间戳
    public long ts;

    private void initCommonParam() {
        this.uid = SharedCacheUtils.getSharedCacheUtils().getPreference(SharedCacheUtils.GLOBAL_UID);
        this.appv = GlobalInfoUtils.getAppVersionName();
        this.ch = GlobalInfoUtils.getChannel();
        this.dt = GlobalInfoUtils.getPhoneModel();
        this.la = GlobalInfoUtils.getLanguage();
        this.net = GlobalInfoUtils.getNetType();
        this.osv = GlobalInfoUtils.getRelease();
        this.ts = System.currentTimeMillis();
    }

    public HashMap<String, String> getCommonParam() {
        initCommonParam();
        HashMap<String, String> commonParamMap = new HashMap();
        if(this.uid != null) {
            commonParamMap.put("uid", this.uid);
        }
        commonParamMap.put("appv", this.appv + "");
        commonParamMap.put("ch", this.ch);
        commonParamMap.put("dt", this.dt);
        commonParamMap.put("la", this.la);
        commonParamMap.put("net", this.net);
        commonParamMap.put("osv", this.osv);
        commonParamMap.put("ts", this.ts + "");
        commonParamMap.put("osn", this.osn);

        return commonParamMap;
    }
}
