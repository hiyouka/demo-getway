package com.jy.gateway.exception;

import org.springframework.security.authentication.InternalAuthenticationServiceException;

/**
 * create by jianglei on 2018/12/2
 */
public class BadCredentialsException extends InternalAuthenticationServiceException {

    private static  String msg = "密码错误";

    public BadCredentialsException(){
        super(msg);
    }

    public BadCredentialsException( String msg) {
        super(msg);
    }

}