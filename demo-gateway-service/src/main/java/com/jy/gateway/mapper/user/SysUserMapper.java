package com.jy.gateway.mapper.user;

import com.jy.common.sso.model.User;

public interface SysUserMapper {
    int deleteByPrimaryKey(String id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User selectByUserName(String username);

    User selectByUserId(String userId);
}