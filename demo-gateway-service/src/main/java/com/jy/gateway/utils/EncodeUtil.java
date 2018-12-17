package com.jy.gateway.utils;

/**
 * create by jianglei on 2018/12/6
 */
public class EncodeUtil {


    public static String encrypt(String data, String salt){
        return Binary.toHex(AES.encrypt(data, salt));
    }

    public static String decrypt(String data, String salt){
        return new String(AES.decrypt(Binary.toBinary(data), salt));
    }

}