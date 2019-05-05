package com.smaller.jiview.core.pojo.dto;

import lombok.Data;

@Data
public class JwtDTO {
    private String token;
    // 登录用户id
    private Long loginPkid;
    // 登录账号
    private String userLogin;
}
