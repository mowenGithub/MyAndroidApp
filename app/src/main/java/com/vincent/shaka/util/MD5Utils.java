package com.vincent.shaka.util;

/**
 * Created by Administrator on 2015/7/19 0019.
 */

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 对外提供getMD5(String)方法
 *
 */
public class MD5Utils {

    public static String getMD5(String val) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5Utils");
        md5.update(val.getBytes());
        byte[] m = md5.digest();//加密
        return getString(m);
    }
    private static String getString(byte[] b){

        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < b.length; i ++){
            sb.append(b[i]);
        }
        return sb.toString();

    }
}
