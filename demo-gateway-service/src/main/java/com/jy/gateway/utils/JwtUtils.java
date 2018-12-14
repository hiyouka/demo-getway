package com.jy.gateway.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;

/**
 * jwt相关操作工具类
 * create by jianglei on 2018/11/29
 */
public class JwtUtils {

    public static final String TOKEN = "ACCESS-TOKEN";

    private static String salt = "t6fAhwmvjEu0qw5TI6TXfUVzH06fddGx";

    @Value("${encrypt.salt}")
    public void setSalt(String salt) {
        JwtUtils.salt= salt;
    }

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
    * 功能描述:获取加密密匙
    * @param:[user]
    * @return:java.lang.String
    * @Date: 2018/11/29
    */
    public static SecretKey getEncryptSalt() {
        SecretKey secretKey = null;
        try {

            //byte[] encodedKey = Base64.decode(salt);
            byte[] encodedKey = salt.getBytes("UTF-8");
            secretKey = new SecretKeySpec(encodedKey,0,encodedKey.length,"AES");
        } catch (Exception e) {

        }
        return secretKey;
    }



    /**
    * 功能描述:生成token 用户退出 超时 token失效
    * @param:[id(jwt的唯一身份标识,用于作为一次性token,从而会不重放攻击),
            iss(jwt签发者), subject(jwt面向用户 payload记录的公开信息),
            ttlMillis(有效期:毫秒)]
    * @return:java.lang.String
    * @Date: 2018/11/29
    */
    public static String generateToke(String id, String iss, String subject, long ttlMillis){
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        Date date = new Date(nowMillis);
        SecretKey encryptSalt = getEncryptSalt();
        //创建JWT构建器.使用指定的加密算法生成token
        JwtBuilder builder = Jwts.builder()
                .setId(id)                                      //设置身份标记.客户端的唯一标记.可以使用用户主键,客户端的ip,服务器生成的随机数据
                .setIssuer(iss)
                .setSubject(subject)
                .setIssuedAt(date)                              //签发时间(token生成时间)
                .signWith(signatureAlgorithm,encryptSalt);      //设定密匙和算法
        if(ttlMillis >= 0){     //
            long expMillis = nowMillis + ttlMillis;
            Date exDate = new Date(expMillis + nowMillis);      //token的过期时间
            builder.setExpiration(exDate);                      //设置有效期
        }
        return builder.compact();                               //生成token
    }


    public static boolean validateJwt(String jwtStr){

        Claims claims = null;


        return false;
    }

    /**
    * 功能描述:解析jwt字符串
    * @param:[token] 客户端传来的token
    * @return:io.jsonwebtoken.Claims
    * @Date: 2018/11/29
    */
    public static Claims parseJwt(String token) throws Exception{
        SecretKey secretKey = getEncryptSalt();
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();         //payload数据
    }



}