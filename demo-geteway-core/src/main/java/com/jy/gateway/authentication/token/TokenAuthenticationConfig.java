package com.jy.gateway.authentication.token;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * create by jianglei on 2018/12/7
 */
@Configuration
@ConditionalOnProperty(prefix = "security.token", name = "is-open" , havingValue = "true")
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
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//                .formLogin()
//                .successHandler(tokenAuthenticationSuccessHandler)
//                .failureHandler(defaultAuthenticationFailureHandler);
    }


    //TODO当前设想工单处理机制
    //监视者模式 回调信息
    //消息处理者集合中维护 消息处理器 当消息处理者集合发生变化时 通知 消息处理器
    //消息处理器中维护着 消息集合 当有当前无法处理任务 存储到消息集合当中
    //(目前问题)
}