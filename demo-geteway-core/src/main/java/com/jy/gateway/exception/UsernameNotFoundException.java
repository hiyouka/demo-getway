package com.jy.gateway.exception;

import com.jy.common.exception.BusinessException;
import com.jy.common.exception.StatusCode;

/**
 * create by jianglei on 2018/12/2
 */
public class UsernameNotFoundException extends BusinessException {

    private static  String msg = StatusCode.ACCOUNT_ERROR.getMsg();

    private static Integer code = StatusCode.ACCOUNT_ERROR.getCode();

    public UsernameNotFoundException(){
        super(code,msg);
    }

    public UsernameNotFoundException(String msg){
        super(code,msg);
    }

    public UsernameNotFoundException(Integer code,String msg) {
        super(code,msg);
    }
}