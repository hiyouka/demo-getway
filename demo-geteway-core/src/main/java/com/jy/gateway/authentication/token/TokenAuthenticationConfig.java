package com.jy.gateway.authentication.token;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 * create by jianglei on 2018/12/7
 */
@Component
public class TokenAuthenticationConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    private TokenAuthenticationSuccessHandler tokenAuthenticationSuccessHandler;

    @Autowired
    protected AuthenticationFailureHandler defaultAuthenticationFailureHandler;

    @Autowired
    private TokenAuthenticationFilter filter;

    @Autowired
    private ExceptionHandFilter exceptionHandFilter;

    @Override
    public void configure(HttpSecurity http) throws Exception {
//        TokenAuthenticationFilter filter = new TokenAuthenticationFilter();
        http//基于token去除session
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(exceptionHandFilter,TokenAuthenticationFilter.class)
        ;
//                .formLogin()
//                .successHandler(tokenAuthenticationSuccessHandler)
//                .failureHandler(defaultAuthenticationFailureHandler);
    }

}