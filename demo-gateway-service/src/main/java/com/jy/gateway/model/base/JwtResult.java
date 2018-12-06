package com.jy.gateway.model.base;

import io.jsonwebtoken.Claims;

/**
 * create by jainglei on 2018/9/18
 *
 * @since 1.0.0
 */
public class JwtResult {

    private Claims claims;
    private Integer code;
    private boolean success;
    private String msg;

}