package com.smaller.jiview.admin.pojo.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xigf on 2019/05/23.
 */
@Data
@ApiModel("菜单访问权限参数")
public class MenuAuthParam {

    @ApiModelProperty("菜单id")
    private Long menuId;

    @ApiModelProperty("菜单访问权限(1:访问权限;2:管理权限)")
    private Byte authorizeLevel;
}
