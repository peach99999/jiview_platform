package com.smaller.jiview.admin.pojo.param;

import com.smaller.jiview.core.pojo.param.BaseParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author xiagf on 2019-05-10.
 */
@Data
@ApiModel("配置角色菜单权限参数")
public class SysRoleUpdateMenuAuthParam extends BaseParam {

    @ApiModelProperty("roleId")
    @NotNull(message = "角色id不能为空")
    private Long roleId;

    @ApiModelProperty("菜单权限以及权限级别")
    private List<SysRoleMenuParam> sysRoleMenuParams;
}
