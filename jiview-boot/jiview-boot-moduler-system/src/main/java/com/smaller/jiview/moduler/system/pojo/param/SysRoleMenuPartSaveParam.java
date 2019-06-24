package com.smaller.jiview.moduler.system.pojo.param;

import com.smaller.jiview.core.pojo.param.BaseParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author xigf on 2019/06/06.
 */
@Data
@ApiModel("角色菜单部件权限参数")
public class SysRoleMenuPartSaveParam extends BaseParam {
    private static final long serialVersionUID = -7150474562971523823L;

    @ApiModelProperty("菜单Id")
    @NotNull(message = "菜单Id不能为空")
    private Long menuId;

    @ApiModelProperty("角色Id")
    @NotNull(message = "角色Id不能为空")
    private Long roleId;

    @ApiModelProperty("菜单组件权限集")
    private List<SysRoleMenuPartAuthParam> menuPartList;
}
