package com.jy.gateway.utils;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * create by jianglei on 2018/12/6
 */
public class EncodeUtil {

    /**
     * MD5加密
     * @param s
     * @return
     */
    public static String md5(final String s){
        if(s == null || s.length() == 0){
            //TODO QIUDONGBIAO 错误信息
        }
        return DigestUtils.md5Hex(s);
    }

    public static String encrypt(String data, String salt){
        return Binary.toHex(AES.encrypt(data, salt));
    }

    public static String decrypt(String data, String salt){
        return new String(AES.decrypt(Binary.toBinary(data), salt));
    }

}