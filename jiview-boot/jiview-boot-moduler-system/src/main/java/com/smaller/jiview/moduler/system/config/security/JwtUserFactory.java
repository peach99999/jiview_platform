package com.smaller.jiview.moduler.system.config.security;


import com.smaller.jiview.moduler.system.platform.system.model.SysUser;
import com.smaller.jiview.core.config.security.JwtUser;

/**
 * @author xigf 2019/05/23
 */
public final class JwtUserFactory {
    private JwtUserFactory() {
    }

    public static JwtUser create(SysUser sysUser) {
        return new JwtUser(
                String.valueOf(sysUser.getId()),
                sysUser.getAccount(),
                null,
                sysUser.getMobile(),
                sysUser.getUserType(),
                null,
                null
        );
    }

}

