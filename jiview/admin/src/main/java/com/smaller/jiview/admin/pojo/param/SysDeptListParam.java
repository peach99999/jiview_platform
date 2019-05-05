package com.smaller.jiview.admin.pojo.param;

import com.smaller.jiview.core.pojo.param.BaseListParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by xigf on 2019/04/30.
 */
@Data
@ApiModel("部门参数")
public class SysDeptListParam extends BaseListParam {

    @ApiModelProperty("父部门id")
    private Long parentId;
}
