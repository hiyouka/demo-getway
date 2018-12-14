/**
 * 
 */
package com.jy.gateway.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author jianglei
 *
 */
@ConfigurationProperties(prefix = "simple.security")
public class SecurityProperties {
	
	private BrowserProperties browser = new BrowserProperties();
	
	private ValidateCodeProperties code = new ValidateCodeProperties();
	
	private SocialProperties social = new SocialProperties();

	private TokenProperties token = new TokenProperties();

	private RequestHeaderProperties header = new RequestHeaderProperties();

	public RequestHeaderProperties getHeader() {
		return header;
	}

	public void setHeader(RequestHeaderProperties header) {
		this.header = header;
	}

	public BrowserProperties getBrowser() {
		return browser;
	}

	public TokenProperties getToken() {
		return token;
	}

	public void setToken(TokenProperties token) {
		this.token = token;
	}

	public void setBrowser(BrowserProperties browser) {
		this.browser = browser;
	}

	public ValidateCodeProperties getCode() {
		return code;
	}

	public void setCode(ValidateCodeProperties code) {
		this.code = code;
	}

	public SocialProperties getSocial() {
		return social;
	}

	public void setSocial(SocialProperties social) {
		this.social = social;
	}
}

