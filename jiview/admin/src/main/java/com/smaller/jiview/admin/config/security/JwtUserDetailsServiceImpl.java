package com.smaller.jiview.admin.config.security;

import com.smaller.jiview.admin.manager.UserManager;
import com.smaller.jiview.admin.platform.system.model.SysUser;
import com.smaller.jiview.core.config.security.JwtUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserManager userManager;

    @Override
    public UserDetails loadUserByUsername(String login) {
        JwtUser jwtUser = null;
        SysUser user = userManager.getByUserLogin(login);

        if (user != null) {
            jwtUser = JwtUserFactory.create(user);
        }

        return jwtUser;
    }
}
