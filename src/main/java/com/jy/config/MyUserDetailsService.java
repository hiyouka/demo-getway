package com.jy.config;

import com.jy.mapper.user.SysUserMapper;
import com.jy.model.user.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author
 * @create 2018/6/1
 * @since 1.0.0
 */
public class MyUserDetailsService implements UserDetailsService {

//    @Autowired
//    private UserService userService;
      @Autowired
      private SysUserMapper sysUserMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        SysUser user = "";user
//        SysUser user = userService.findUserByUserName(username);
        SysUser user = sysUserMapper.selectByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
//        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
//        for (String role : user.getRoles()) {
//            authorities.add(new SimpleGrantedAuthority(role));
//        }
//        User securityUser = new User(user.getUsername(), user.getPassword(), authorities);
//        SysUser securityUser = new SysUser();
//        securityUser.setUsername(user.getUsername());
//        securityUser.setPassword(user.getPassword());
//        securityUser.set
        return user;
    }
}