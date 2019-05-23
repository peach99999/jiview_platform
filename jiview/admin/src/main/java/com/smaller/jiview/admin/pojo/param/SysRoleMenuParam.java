package com.smaller.jiview.admin.pojo.param;

import com.smaller.jiview.admin.platform.system.model.SysRoleMenuPart;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author xigf on 2019/05/10.
 */
@Data
@ApiModel("角色菜单权限参数")
public class SysRoleMenuParam implements Serializable {
    private static final long serialVersionUID = -4587628109468114575L;

    @ApiModelProperty("菜单Id")
    private Long menuId;

    @ApiModelProperty("权限级别(1:访问权限;2:管理权限)")
    private Byte authorizeLevel;

    @ApiModelProperty("菜单组件权限集")
    private List<SysRoleMenuPart> menuPartList;
}
