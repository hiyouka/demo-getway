/**
 * 
 */
package com.jy.gateway.authentication;

import com.jy.gateway.properties.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.filter.CorsFilter;

/**
 * @author jianglei
 *
 */
public class AbstractChannelSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	protected AuthenticationSuccessHandler defaultAuthenticationSuccessHandler;
	
	@Autowired
	protected AuthenticationFailureHandler defaultAuthenticationFailureHandler;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private CorsFilter corsFilter;//跨域配置过滤器

	protected void applyPasswordAuthenticationConfig(HttpSecurity http) throws Exception {

		http
			.addFilterBefore(corsFilter, ChannelProcessingFilter.class)
			.formLogin()
			.loginPage(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
			.loginProcessingUrl(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM)
			.successHandler(defaultAuthenticationSuccessHandler)
			.failureHandler(defaultAuthenticationFailureHandler);
	}
	
}
