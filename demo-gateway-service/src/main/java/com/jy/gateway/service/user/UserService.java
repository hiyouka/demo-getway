package com.jy.gateway.service.user;//package com.jy.gateway.service.user;

import com.jy.common.exception.StatusCode;
import com.jy.common.sso.model.User;
import com.jy.common.utils.JsonUtils;
import com.jy.gateway.mapper.user.SysUserMapper;
import com.jy.gateway.utils.EncodeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jainglei
 * @create 2018/6/1
 * @since 1.0.0
 */
@Service
@Transactional
public class UserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Value("${encrypt.salt}")
    private String salt;

    private Logger logger = LoggerFactory.getLogger(UserService.class);

    public User findUserByUserName(String username){

        return sysUserMapper.selectByUserName(username);
    }

    public boolean doRedirect() {
        try{
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if(user != null){
                return true;
            }
        }catch (Exception e){
            logger.error("do Redirect error" , e);
        }
        return false;
    }

    public Map<String,Object> checkToken(String token) {
        Map<String,Object> result = new HashMap<>();
        result.put("auth",false);
        if(token == null){
            result.put("code",StatusCode.UNLOGIN.getCode());
        }else {
            logger.info("check authentication token : " + token);
            Object data = stringRedisTemplate.opsForValue().get(token);
            if(data.toString().equals(StatusCode.UNLOGIN.getCode().toString())){
                result.put("code",StatusCode.UNLOGIN.getCode());
            }else if(data == null){
                result.put("code", StatusCode.LOGIN_EXPIRE.getCode());
            }else {
                result.put("code", 200);
                result.put("auth",true);
            }
        }
        return result;
    }

    public User getUserInfoByToken(String token) {
        Object data = stringRedisTemplate.opsForValue().get(token);
        return JsonUtils.toObject(data.toString(),User.class);
    }

    public void logout(String token) {
        stringRedisTemplate.opsForValue().set(token,StatusCode.UNLOGIN.getCode().toString());
    }
}