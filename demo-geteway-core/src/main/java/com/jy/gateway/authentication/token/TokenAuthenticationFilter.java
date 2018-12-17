package com.jy.gateway.authentication.token;

import com.jy.common.exception.StatusCode;
import com.jy.common.sso.model.User;
import com.jy.common.utils.JsonUtils;
import com.jy.common.utils.StringUtils;
import com.jy.gateway.exception.LoginExpireException;
import com.jy.gateway.properties.SecurityProperties;
import com.jy.gateway.utils.EncodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * create by jianglei on 2018/12/6
 */
@Component
public class TokenAuthenticationFilter  extends OncePerRequestFilter {

    @Value("${encrypt.salt}")
    private String salt;

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String token = request.getHeader(securityProperties.getToken().getAccessToken());
        if(!StringUtils.isEmpty(token)) {
            token = EncodeUtil.decrypt(token, salt);
            logger.info("check authentication token : " + token);
            Object data = redisTemplate.opsForValue().get(token);   //获取用户信息
            if(data != null && StatusCode.UNLOGIN.getCode().toString().equals(data.toString())){      //注销
                //undo everything
            }else if (data != null) {
                redisTemplate.expire(token, 30, TimeUnit.DAYS);//延长时间30分钟
                User user = JsonUtils.toObject((String) data, User.class);
                //生成授权的usernamePasswordAuthenticationToken
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }else{ //token 失效
                throw new LoginExpireException();
            }
        }
        chain.doFilter(request, response);
    }
}