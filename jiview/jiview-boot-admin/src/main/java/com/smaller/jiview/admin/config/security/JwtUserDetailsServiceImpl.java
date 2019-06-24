package com.smaller.jiview.admin.config.security;

import com.smaller.jiview.admin.manager.SysUserManager;
import com.smaller.jiview.admin.platform.system.model.SysUser;
import com.smaller.jiview.core.config.security.JwtUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

/**
 * @author xigf 2019/05/23
 */
@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private SysUserManager sysUserManager;

    @Override
    public UserDetails loadUserByUsername(String login) {
        JwtUser jwtUser = null;
        SysUser user = sysUserManager.getByUserLogin(login);

        if (user != null) {
            jwtUser = JwtUserFactory.create(user);
        }

        return jwtUser;
    }
}
