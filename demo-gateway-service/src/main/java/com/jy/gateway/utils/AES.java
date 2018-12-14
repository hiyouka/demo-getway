package com.jy.gateway.utils;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * create by jianglei on 2018/12/6
 */

class AES{
    /**
     * 加密
     *
     * @param content 需要加密的内容
     * @param password  加密密码
     * @return
     */
    public static byte[] encrypt(String content, String password) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG" );
            secureRandom.setSeed(password.getBytes());
            kgen.init(128, secureRandom);
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            byte[] byteContent = content.getBytes("utf-8");
            cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(byteContent);
            return result; // 加密
        } catch (NoSuchAlgorithmException e) {
            //TODO QIUDONGBIAO  需要将所有的异常进行处理
//                    e.printStackTrace();
        } catch (NoSuchPaddingException e) {
//                    e.printStackTrace();
        } catch (InvalidKeyException e) {
//                    e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
//                    e.printStackTrace();
        } catch (BadPaddingException e) {
//                    e.printStackTrace();
        }
        return null;
    }

    /**解密
     * @param content  待解密内容
     * @param password 解密密钥
     * @return
     */
    public static byte[] decrypt(byte[] content, String password) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG" );
            secureRandom.setSeed(password.getBytes());
            kgen.init(128, secureRandom);
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(content);
            return result; // 加密
        } catch (NoSuchAlgorithmException e) {
            //TODO QIUDONGBIAO  需要将所有的异常进行处理
                    e.printStackTrace();
        } catch (NoSuchPaddingException e) {
                    e.printStackTrace();
        } catch (InvalidKeyException e) {
                    e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
                    e.printStackTrace();
        } catch (BadPaddingException e) {

                    e.printStackTrace();
        }
        return null;
    }
}