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
                SysUser.getMobile(),
                null,
                SysUser.getMobile(),
                null,
                null
        );
    }

//    public static JwtUser create(OrgUser orgUser) {
//        return new JwtUser(
//                String.valueOf(orgUser.getPkid()),
//                orgUser.getUserMobile(),
//                null,
//                orgUser.getUserMobile(),
//                orgUser.getUserUuid(),
//                orgUser.getSupplierPkid(),
//                null,
//                null
//        );
//    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<String> authorities) {
        return authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}

