package com.smaller.jiview.moduler.system.pojo.param;

import com.smaller.jiview.core.pojo.param.BaseListParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * SysRoleListParam
 *
 * @author xiagf
 * @date 2019/05/10
 */
@Data
public class SysRoleListParam extends BaseListParam {

    /**
     * 角色名
     */
    @ApiModelProperty("角色名称")
    private String roleName;

    /**
     * 部门id
     */
    @ApiModelProperty("部门id")
    private Long deptId;
}
