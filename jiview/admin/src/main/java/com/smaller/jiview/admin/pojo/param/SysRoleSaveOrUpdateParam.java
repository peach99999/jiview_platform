package com.smaller.jiview.admin.pojo.param;

import com.smaller.jiview.core.pojo.param.BaseParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * SysRoleSaveOrUpdateParam
 *
 * @author xiagf
 * @date 2019/05/10
 */
@Data
@ApiModel("保存角色参数")
public class SysRoleSaveOrUpdateParam extends BaseParam {

    @ApiModelProperty("roleId")
    private Long roleId;

    @NotBlank(message = "角色名不能为空")
    @ApiModelProperty("角色名")
    private String roleName;

    @NotNull(message = "部门id不能为空")
    @ApiModelProperty("deptId")
    private Long deptId;

    @NotNull(message = "角色类型不能为空")
    @ApiModelProperty("角色类型(1:业务角色;2:管理角色 ;3:系统内置角色)")
    private Byte roletype;

    @ApiModelProperty("备注")
    private String remark;

    @NotNull(message = "锁定标志不能为空")
    @ApiModelProperty("锁定标志(1:锁定;0:激活)")
    private Boolean locked;
}
