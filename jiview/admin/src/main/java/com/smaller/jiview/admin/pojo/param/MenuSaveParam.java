package com.smaller.jiview.admin.pojo.param;

import com.smaller.jiview.core.pojo.param.BaseParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by xiagf on 2019/05/09.
 */
@Data
@ApiModel("添加菜单参数")
public class MenuSaveParam extends BaseParam {
    @ApiModelProperty("父菜单pkid")
    private Long parentId;

    @NotNull(message = "菜单名称不能为空")
    @ApiModelProperty("菜单名称")
    private String menuName;

    @NotNull(message = "路由url不能为空")
    @ApiModelProperty("路由")
    private String url;

    @ApiModelProperty("排序")
    private Integer sortno;

    @ApiModelProperty("class")
    private String iconcls;
}
