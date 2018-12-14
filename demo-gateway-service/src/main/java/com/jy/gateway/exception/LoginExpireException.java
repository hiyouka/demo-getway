package com.jy.gateway.exception;

import com.jy.common.exception.BusinessException;
import com.jy.common.exception.StatusCode;

/**
 * create by jianglei on 2018/12/12
 */
public class LoginExpireException extends BusinessException {

    private static String msg = StatusCode.LOGIN_EXPIRE.getMsg();

    private static Integer code = StatusCode.LOGIN_EXPIRE.getCode();

    public LoginExpireException(){
        super(code,msg);
    }

    public LoginExpireException(int code, String msg) {
        super(code, msg);
    }
}