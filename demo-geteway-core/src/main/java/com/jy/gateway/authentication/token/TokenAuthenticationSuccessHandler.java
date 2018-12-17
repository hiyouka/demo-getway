package com.jy.gateway.authentication.token;

import com.jy.common.result.Result;
import com.jy.common.sso.Constants.LoginResponseType;
import com.jy.common.sso.Constants.LoginWay;
import com.jy.common.sso.model.User;
import com.jy.common.utils.JsonUtils;
import com.jy.gateway.properties.SecurityProperties;
import com.jy.gateway.utils.EncodeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * create by jianglei on 2018/12/7
 */
@Component("tokenAuthenticationSuccessHandler")
public class TokenAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {


    private Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${encrypt.salt}")
    private String salt;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        logger.info("登录成功");
        if (LoginResponseType.JSON.equals(securityProperties.getBrowser().getLoginType())) {
            response.setContentType("application/json;charset=UTF-8");
            Map<String,Object> tokenMap = new HashMap<>();
            //生成token
            String header = request.getHeader(securityProperties.getHeader().getAppId());
            if(LoginWay.BROWSER.equals(header)){
                logger.info("login way is browser ....");
            }
            String token = System.currentTimeMillis()+"";
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            token = user.getId()+"-"+header+"-"+token; //token由 用户id 请求来源 毫秒数组成 后期可控制设备的登录数 实现一个登录拦截
            stringRedisTemplate.opsForValue().set(user.getId(),token); //支持多地登录可以token ';' 隔开
            String bal = JsonUtils.toJson(user);
            stringRedisTemplate.opsForValue().set(token, bal);
            token = EncodeUtil.encrypt(token,salt);
            tokenMap.put("token",token);
            response.getWriter().write(JsonUtils.toJson(Result.ok(tokenMap)));
        } else {
            super.onAuthenticationSuccess(request, response, authentication);
        }

    }


}