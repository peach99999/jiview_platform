package com.smaller.jiview.moduler.system.pojo.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author xigf on 2019/05/09.
 */
@Data
@ApiModel("菜单部门参数")
public class SysMenuPartParam implements Serializable {
    private static final long serialVersionUID = -6718848446450103727L;

    @ApiModelProperty("部件id")
    private Long partId;

    @NotNull(message = "菜单id不能为空")
    @ApiModelProperty("菜单id")
    private Long menuId;

    @ApiModelProperty("UI组件ID")
    private String cmpId;

    @ApiModelProperty("UI组件类型(1:按钮组件;2:表单输入组件;3:容器面板组件)")
    private Byte cmpType;

    @ApiModelProperty("备注")
    private String remark;

}
