package com.smaller.jiview.moduler.system.pojo.model.ext;

import com.smaller.jiview.moduler.system.platform.system.model.SysDept;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * SysDeptExt
 *
 * @author xiagf
 * @date 2019-05-07
 */
@Data
@ApiModel("部门查询参数")
public class SysDeptExt extends SysDept {
    @ApiModelProperty("用户名称")
    private String userName;

    @ApiModelProperty("父部门名称")
    private String parentDeptName;
}
