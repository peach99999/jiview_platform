package com.smaller.jiview.admin.pojo.model.ext;

import com.smaller.jiview.admin.platform.system.model.SysUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * SysUserExt
 *
 * @author xiagf
 * @date 2019-05-08
 */
@Data
@ApiModel("用户信息")
public class SysUserExt extends SysUser {
    @ApiModelProperty("部门名称")
    private String deptName;

    @ApiModelProperty("用户角色集")
    private List<SysUserRoleExt> sysUserRoleExtList;
}
