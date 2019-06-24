package com.smaller.jiview.admin.pojo.model.ext;

import com.smaller.jiview.admin.platform.system.model.SysRole;
import com.smaller.jiview.admin.pojo.param.MenuAuthParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author xigf 2019/05/23
 */
@Data
@ApiModel("用户角色")
public class SysRoleExt extends SysRole {

    @ApiModelProperty("角色对应菜单权限")
    private List<MenuAuthParam> menuIds;

    @ApiModelProperty("部门名称")
    private String deptName;

    @ApiModelProperty("状态名称")
    private String statusName;
}
