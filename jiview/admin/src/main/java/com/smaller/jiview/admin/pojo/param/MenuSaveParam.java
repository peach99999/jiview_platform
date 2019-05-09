package com.smaller.jiview.admin.pojo.param;

import com.smaller.jiview.core.pojo.param.BaseParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by xiagf on 2019/05/09.
 */
@Data
@ApiModel("添加菜单参数")
public class MenuSaveParam extends BaseParam {
    @ApiModelProperty("父菜单pkid")
    private Long parentId;

    @ApiModelProperty("标题")
    private String menuName;

    @ApiModelProperty("路由")
    private String url;

    @ApiModelProperty("排序")
    private Integer sortno;

    @ApiModelProperty("class")
    private String iconcls;
}
