package com.jy.gateway.exception;

import org.springframework.security.authentication.InternalAuthenticationServiceException;

/**
 * create by jianglei on 2018/12/2
 */
public class UsernameNotFoundException extends InternalAuthenticationServiceException {

    private static  String msg = "账户不存在";

    public UsernameNotFoundException(){
        super(msg);
    }

    public UsernameNotFoundException( String msg) {
        super(msg);
    }
}