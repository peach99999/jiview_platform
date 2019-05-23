package com.smaller.jiview.admin.pojo.param;

import com.smaller.jiview.core.pojo.param.BaseParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 * @author xiagf on 2019/05/09.
 */
@Data
@ApiModel("编辑菜单参数")
public class MenuUpdateParam extends BaseParam {

    @NotNull(message = "菜单id不能为空")
    @ApiModelProperty("菜单id")
    private Long menuId;

    @ApiModelProperty("父菜单id")
    private Long parentId;

    @NotNull(message = "菜单名称不能为空")
    @ApiModelProperty("菜单名称")
    private String menuName;

    @NotNull(message = "菜单url不能为空")
    @ApiModelProperty("菜单url")
    private String url;

    @ApiModelProperty("菜单排序号")
    private Integer sortno;

    @ApiModelProperty("class")
    private String iconcls;
}
