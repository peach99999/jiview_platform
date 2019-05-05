package com.smaller.jiview.core.config.security;

import com.smaller.jiview.core.pojo.model.SysUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

public final class JwtUserFactory {
    private JwtUserFactory() {
    }

    public static JwtUser create(SysUser SysUser) {
        return new JwtUser(
                String.valueOf(SysUser.getUserId()),
                SysUser.getAccount(),
                null,
                SysUser.getMobile(),
                SysUser.getUserType(),
                null,
                null
        );
    }

}

