package com.jy.gateway.service.user;//package com.jy.gateway.service.user;

import com.jy.common.sso.model.User;
import com.jy.gateway.mapper.user.SysUserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}