package com.smaller.jiview.admin.pojo.model.ext;

import com.smaller.jiview.admin.platform.system.model.SysRoleMenuPart;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * SysRoleMenuPartExt
 *
 * @author xiagf
 * @date 2019-05-13
 */
@Data
@ApiModel("用户菜单部件权限")
public class SysRoleMenuPartExt extends SysRoleMenuPart {
    @ApiModelProperty("组件ID")
    private String cmpId;
}
