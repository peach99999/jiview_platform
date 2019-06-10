package com.smaller.jiview.admin.pojo.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author jianghe on 2017/6/14.
 */
@Data
@ApiModel("后台管理重置密码参数")
public class ResetPwdParam {

    @ApiModelProperty("账号")
    private String account;

    @ApiModelProperty("密码")
    private String password;
}
