package com.vincent.shaka.network;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Created by mowen on 5/20/16.
 */
public class NetworkAccessStrategy {


    /**
     * 签名生成算法
     *
     * @param HashMap<String, String> params 请求参数集，所有参数必须已转换为字符串类型
     * @param String                 secret 签名密钥
     * @return 签名
     * @throws IOException
     */
    public static String getSignature(HashMap<String, String> params, String secret) throws IOException {
        StringBuilder basestring = new StringBuilder();
        if(params != null) {
            // 先将参数以其参数名的字典序升序进行排序
            Map<String, String> sortedParams = new TreeMap<String, String>(params);
            Set<Map.Entry<String, String>> entrys = sortedParams.entrySet();

            // 遍历排序后的字典，将所有参数按"key=value"格式拼接在一起
            for (Map.Entry<String, String> param : entrys) {
                basestring.append(param.getKey()).append("=").append(String.valueOf(param.getValue()));
            }
        }
        basestring.append(secret);

        // 使用MD5对待签名串求签
        byte[] bytes = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            bytes = md5.digest(basestring.toString().getBytes("UTF-8"));
        } catch (GeneralSecurityException ex) {
            throw new IOException(ex);
        }

        // 将MD5输出的二进制结果转换为小写的十六进制
        StringBuilder sign = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                sign.append("0");
            }
            sign.append(hex);
        }
        return sign.toString();
    }
}