package com.smaller.jiview.moduler.system.pojo.model.ext;

import com.smaller.jiview.moduler.system.platform.system.model.SysUserRole;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xigf 2019/05/23
 */
@Data
@ApiModel("用户角色扩展参数")
public class SysUserRoleExt extends SysUserRole {

    @ApiModelProperty("角色名称")
    private String roleName;
}
