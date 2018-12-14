package com.jy.gateway.exception;

import com.jy.common.exception.BusinessException;
import com.jy.common.exception.StatusCode;

/**
 * create by jianglei on 2018/12/2
 */
public class BadCredentialsException extends BusinessException {

    private static Integer code = StatusCode.PASSWORD_ERROR.getCode();

    private static String msg = StatusCode.PASSWORD_ERROR.getMsg();

    public BadCredentialsException(){
        super(code,msg);
    }

    public BadCredentialsException(String msg){
        super(code,msg);
    }

    public BadCredentialsException(Integer code,String msg) {
        super(code,msg);
    }

}