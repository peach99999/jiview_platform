package com.smaller.jiview.moduler.system.pojo.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author jianghe on 2017/6/14.
 */
@Data
@ApiModel("后台管理重置密码参数")
public class ResetPwdParam {

    @ApiModelProperty("账号")
    @NotNull(message = "账号不能为空")
    private String account;

    @ApiModelProperty("密码")
    @NotNull(message = "新密码不能为空")
    private String password;
}
