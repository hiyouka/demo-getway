/**
 * 
 */
package com.jy.gateway.handler;

import com.jy.common.result.Result;
import com.jy.common.sso.Constants.LoginResponseType;
import com.jy.common.utils.JsonUtils;
import com.jy.gateway.exception.CodeException;
import com.jy.gateway.properties.SecurityProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zhailiang
 *
 */
@Component("defaultAuthenticationFailureHandler")
public class DefaultAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	private Logger logger = LoggerFactory.getLogger(getClass());

	
	@Autowired
	private SecurityProperties securityProperties;

	
	/* (non-Javadoc)
	 * @see org.springframework.security.web.authentication.AuthenticationFailureHandler#onAuthenticationFailure(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.security.core.AuthenticationException)
	 */
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
		logger.info("登录失败");
		if (LoginResponseType.JSON.equals(securityProperties.getBrowser().getLoginType())) {
			response.setContentType("application/json;charset=UTF-8");
			if(exception instanceof CodeException){
				response.getWriter().write(JsonUtils.toJson(Result.error(((CodeException) exception).getCode(),exception.getMessage())));
			}else {
				response.getWriter().write(JsonUtils.toJson(Result.error(101,exception.getMessage())));
			}
		}else{
			super.onAuthenticationFailure(request, response, exception);
		}
		
		
	}

}
