package com.jy.gateway.mapper.user;

import com.jy.gateway.model.user.SysRoleUser;

public interface SysRoleUserMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysRoleUser record);

    int insertSelective(SysRoleUser record);

    SysRoleUser selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysRoleUser record);

    int updateByPrimaryKey(SysRoleUser record);
}