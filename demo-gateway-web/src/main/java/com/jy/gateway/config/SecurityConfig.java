package com.jy.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author jianglei
 * @create 2018/6/12
 * @since 1.0.0
 */

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean("userDetails")
    public UserDetailsService customUserService(){
        return new MyUserDetailsService();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //并根据传入的AuthenticationManagerBuilder中的userDetailsService方法来接收我们自定义的认证方法。
        //且该方法必须要实现UserDetailsService这个接口。
        auth.userDetailsService(customUserService())
                //密码使用BCryptPasswordEncoder()方法验证，因为这里使用了BCryptPasswordEncoder()方法验证。
                // 所以在注册用户的时候在接收前台明文密码之后也需要使用BCryptPasswordEncoder().encode(明文密码)方法加密密码。
                .passwordEncoder(new BCryptPasswordEncoder());

    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/**/static/**","/css/**","/**/favicon.ico");//不拦截静态资源
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/main").permitAll()
                .antMatchers("/user/space").hasAnyRole("ADMIN","FRIEND")
                .antMatchers("/client/*").hasAnyRole("ADMIN","FRIEND","TEACHER")
                .antMatchers("/user/line").hasRole("TEACHER")
                .antMatchers("/user/cc","/user/rabbit/**","/user/kk").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
//                .loginProcessingUrl("").permitAll()
                .loginPage("/front/index").permitAll()
                .successForwardUrl("/doFilter")
                .failureUrl("/login?error")
                .permitAll()
                .and()
                .logout().invalidateHttpSession(true)
                .permitAll();
    }

}