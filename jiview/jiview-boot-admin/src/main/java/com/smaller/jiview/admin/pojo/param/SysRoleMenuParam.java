package com.smaller.jiview.admin.pojo.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author xigf on 2019/05/10.
 */
@Data
@ApiModel("角色菜单权限参数")
public class SysRoleMenuParam implements Serializable {
    private static final long serialVersionUID = -4587628109468114575L;

    @ApiModelProperty("菜单Id")
    @NotNull(message = "菜单Id不能为空")
    private Long menuId;

    @ApiModelProperty("权限级别(1:访问权限;2:管理权限)")
    @NotNull(message = "权限级别不能为空")
    private Byte authorizeLevel;

}
