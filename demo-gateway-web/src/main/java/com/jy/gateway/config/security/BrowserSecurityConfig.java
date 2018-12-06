/**
 * 
 */
package com.jy.gateway.config.security;

import com.jy.gateway.authentication.AbstractChannelSecurityConfig;
import com.jy.gateway.authentication.simple.SimpleJsonAuthenticationConfig;
import com.jy.gateway.properties.SecurityConstants;
import com.jy.gateway.properties.SecurityProperties;
import com.jy.gateway.validate.code.ValidateCodeSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * @author jianglei
 *
 */
@Configuration
@EnableWebSecurity
public class BrowserSecurityConfig extends AbstractChannelSecurityConfig {

	@Autowired
	private SecurityProperties securityProperties;

	@Autowired
	private ValidateCodeSecurityConfig validateCodeSecurityConfig;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private SpringSocialConfigurer springSocialConfigurer;

	@Autowired
	private SimpleJsonAuthenticationConfig simpleJsonAuthenticationConfig;

	private static String[] noLoginUrl = {"/handError","/loginFailure","/callable*","/front/login*","/front/notFind","/test", SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX+"/*"};

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		applyPasswordAuthenticationConfig(http);
		http
//				.apply(validateCodeSecurityConfig)
//				.and()
				.apply(springSocialConfigurer)
				.and()
				.apply(simpleJsonAuthenticationConfig)
				.and()
				.authorizeRequests()
				.antMatchers(noLoginUrl).permitAll()
				.anyRequest()
				.authenticated()
				.and()
				.logout().logoutUrl("/logout")
				.invalidateHttpSession(true)
				.and()
				.csrf().disable();


	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/**/*.js","/**/*.min.js", "/**/*.png","/**/*.css","/*.git","/*.jpg",
				"/static/**","/css/**","/**/favicon.ico","/js/**","/images/**", "/img/**","/imgs/**","/**/editor/**");//不拦截静态资源
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService)
				.passwordEncoder(passwordEncoder());
	}


//	@Bean("messageSource")
//	public ReloadableResourceBundleMessageSource messageSource(){
//		ReloadableResourceBundleMessageSource source = new ReloadableResourceBundleMessageSource();
////		source.setBasename("classpath:message/messages_zh_CN.properties");
//
//		return source;
//	}

}
