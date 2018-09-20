package com.jy.gateway.service.user;

import com.jy.gateway.mapper.user.SysUserMapper;
import com.jy.gateway.model.user.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 〈〉
 *
 * @author jainglei
 * @create 2018/6/1
 * @since 1.0.0
 */
@Service
@Transactional
public class UserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    public SysUser findUserByUserName(String username){
        return sysUserMapper.selectByUserName(username);
    }

}