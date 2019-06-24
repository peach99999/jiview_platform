package com.smaller.jiview.admin.pojo.param;

import com.smaller.jiview.core.pojo.param.BaseListParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xigf on 2019/04/30.
 */
@Data
@ApiModel("部门参数")
public class SysDeptListParam extends BaseListParam {

    @ApiModelProperty("部门id")
    private Long deptId;

    @ApiModelProperty("部门名称")
    private String deptName;
}
