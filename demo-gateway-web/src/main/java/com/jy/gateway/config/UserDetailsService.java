package com.jy.gateway.config;

import com.jy.common.sso.model.User;
import com.jy.gateway.mapper.user.SysUserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author
 * @create 2018/6/1
 * @since 1.0.0
 */
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

//    @Autowired
//    private UserService userService;
      @Autowired
      private SysUserMapper sysUserMapper;

      private Logger logger = LoggerFactory.getLogger(UserDetailsService.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        SysUser user = "";user
//        SysUser user = userService.findUserByUserName(username);
        User user = sysUserMapper.selectByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        logger.info("用户登录 :{}" , user.getId() + "-" + user.getUsername());

        logger.info("用户权限 :{}" , user.getRoles());
//        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
//        for (String role : user.getRoles()) {
//            authorities.add(new SimpleGrantedAuthority(role));
//        }
//        SysUser securityUser = new SysUser();
//        securityUser.setUsername(user.getUsername());
//        securityUser.setPassword(user.getPassword());
//        securityUser.set
        return user;
    }
}