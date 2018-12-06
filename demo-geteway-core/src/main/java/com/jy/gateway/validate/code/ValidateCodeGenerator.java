/**
 * 
 */
package com.jy.gateway.validate.code;

import com.jy.common.sso.model.ValidateCode;
import org.springframework.web.context.request.ServletWebRequest;

/**
 *
 */
public interface ValidateCodeGenerator {

	ValidateCode generate(ServletWebRequest request);
	
}
