package com.smaller.jiview.moduler.system.pojo.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author xigf on 2019/06/06.
 */
@Data
@ApiModel("角色菜单部件参数")
public class SysRoleMenuPartAuthParam {

    @ApiModelProperty("部件Id")
    @NotNull(message = "部件Id不能为空")
    private Long partId;

    @ApiModelProperty("UI组件权限类型")
    @NotNull(message = "UI组件权限类型不能为空")
    /**
     * UI组件权限类型(1:禁用;2:只读;3:编辑;4:显示;5:隐藏;6:挂起;7:激活;)
     */
    private Byte partAuthType;

}
