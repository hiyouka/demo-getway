package com.jy.gateway.handler;

import com.jy.common.exception.BusinessException;
import com.jy.common.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 异常处理
 * create by jianglei on 2018/12/2
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(getClass());


    /**
     * @param exception
     * @return
     * @throws Exception
     * @// TODO: 2018/4/25 方法访问权限不足异常
     */
    @ExceptionHandler(value = AccessDeniedException.class)
    public Object AccessDeniedExceptionHandler(AccessDeniedException exception) throws Exception {
        logger.info(exception.getMessage());
        return Result.error(101,"权限不足");
    }

    /**
     * 处理自定义异常
     */
    @ExceptionHandler(value = BusinessException.class)
    public Object WelendExceptionHandler(BusinessException e) {
        return Result.error(e.getCode(),e.getMsg());
    }

    @ExceptionHandler(value = AuthenticationException.class)
    public Object AuthenticationExceptionHandler(AuthenticationException e) {
        return Result.error(102,e.getMessage());
    }


//    @ExceptionHandler(value = BadCredentialsException.class)
//    public Object BadCredentialsExceptionHandler(BadCredentialsException e) throws exception {
//        logger.error(e.getMessage(), e);
//        return Result.error(103,e.getMessage());
//    }

}