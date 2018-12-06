package com.jy.gateway.exception;

import com.jy.common.exception.StatusCode;
import org.springframework.security.core.AuthenticationException;

/**
 * create by jianglei on 2018/12/3
 */
public class ValidateCodeException extends AuthenticationException implements CodeException{

    private static Integer code = StatusCode.ACCOUNT_ERROR.getCode();

    private static String msg = "验证码有误";

    public ValidateCodeException(){
        super(msg);
    }

    public ValidateCodeException(String msg) {
        super(msg);
    }

    public ValidateCodeException(Integer code,String msg) {
        super(msg);
        this.code = code;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public void setCode(Integer code) {
    }
}