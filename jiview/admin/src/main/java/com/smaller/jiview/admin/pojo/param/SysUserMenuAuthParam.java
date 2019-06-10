package com.smaller.jiview.admin.pojo.param;

import com.smaller.jiview.core.pojo.param.BaseParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author xiagf on 2019-06-10.
 */
@Data
@ApiModel("用户菜单权限映射参数")
public class SysUserMenuAuthParam extends BaseParam {

    @ApiModelProperty("roleId")
    @NotNull(message = "角色id不能为空")
    private Long userId;

    @ApiModelProperty("菜单权限以及权限级别")
    private List<SysRoleMenuParam> sysUserMenuParams;
}
