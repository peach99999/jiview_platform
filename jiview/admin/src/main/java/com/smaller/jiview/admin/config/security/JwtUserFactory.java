package com.smaller.jiview.admin.config.security;


import com.smaller.jiview.admin.platform.system.model.SysUser;
import com.smaller.jiview.core.config.security.JwtUser;

public final class JwtUserFactory {
    private JwtUserFactory() {
    }

    public static JwtUser create(SysUser SysUser) {
        return new JwtUser(
                String.valueOf(SysUser.getId()),
                SysUser.getAccount(),
                null,
                SysUser.getMobile(),
                SysUser.getUserType(),
                null,
                null
        );
    }

}

