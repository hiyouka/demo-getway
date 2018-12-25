package com.jy.gateway.authentication.token;

import com.jy.common.exception.BusinessException;
import com.jy.common.result.Result;
import com.jy.common.utils.JsonUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * create by jianglei on 2018/12/12
 */
@Component
public class ExceptionHandFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            filterChain.doFilter(httpServletRequest,response);
        }catch (BusinessException exception){   //自定义业务异常处理
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(JsonUtils.toJson(Result.error(exception.getCode(),exception.getMessage())));
            response.flushBuffer();
        }
    }
}