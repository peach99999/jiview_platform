package com.smaller.jiview.admin.pojo.param;

import com.smaller.jiview.core.pojo.param.BaseParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by xiagf on 2019/05/09.
 */
@Data
@ApiModel("编辑菜单参数")
public class MenuUpdateParam extends BaseParam {

    private Long menuId;

    private Long parentId;

    private String menuName;

    private String url;

    private Integer sortno;

    @ApiModelProperty("class")
    private String iconcls;
}
