package com.smaller.jiview.moduler.system.pojo.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xigf on 2019/03/01.
 */
@Data
@ApiModel("admin管理员登陆信息")
public class LoginParam {

    @ApiModelProperty("admin管理员登录用账号")
    private String account;

    @ApiModelProperty("admin管理员登录密码")
    private String userPwd;
}
