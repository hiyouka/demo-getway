package com.jy.gateway.exception;

import com.jy.common.exception.StatusCode;

/**
 * create by jianglei on 2018/12/5
 */
public interface CodeException {

    Integer getCode();

    void setCode(Integer code);

}