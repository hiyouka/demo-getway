package com.jy.gateway.authentication.simple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 * create by jianglei on 2018/12/2
 */
@Component
public class SimpleJsonAuthenticationConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    protected AuthenticationSuccessHandler defaultAuthenticationSuccessHandler;

    @Autowired
    protected AuthenticationFailureHandler defaultAuthenticationFailureHandler;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

//    @Autowired
//    private ExceptionTranslationFilter exceptionTranslationFilter;

    @Override
    public void configure(HttpSecurity http) throws Exception {

        JsonUsernamePasswordAuthenticationFilter jsonUsernamePasswordAuthenticationFilter = new JsonUsernamePasswordAuthenticationFilter();
        jsonUsernamePasswordAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        jsonUsernamePasswordAuthenticationFilter.setAuthenticationSuccessHandler(defaultAuthenticationSuccessHandler);
        jsonUsernamePasswordAuthenticationFilter.setAuthenticationFailureHandler(defaultAuthenticationFailureHandler);
        JsonDaoAuthenticationProvider jsonDaoAuthenticationProvider = new JsonDaoAuthenticationProvider();
        jsonDaoAuthenticationProvider.setUserDetailsService(userDetailsService);
        jsonDaoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        http
                .authenticationProvider(jsonDaoAuthenticationProvider)
                .addFilterBefore(jsonUsernamePasswordAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
//                .addFilterBefore(exceptionTranslationFilter, FilterSecurityInterceptor.class);

    }

}