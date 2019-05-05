package com.smaller.jiview.admin.pojo.param;

import com.smaller.jiview.core.pojo.param.BaseParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * RoleSaveOrUpdateParam
 *
 * @author xiagf
 * @date 2019/05/05
 */
@Data
@ApiModel("保存部门参数")
public class SysDeptSaveOrUpdateParam extends BaseParam {

    @ApiModelProperty("deptId")
    private Long deptId;

    @ApiModelProperty("父部门ID")
    private Long parentId;

    @NotBlank(message = "组织角色名不能为空")
    @ApiModelProperty("部门名称")
    private String deptName;

    @ApiModelProperty("叶子节点(0:树枝节点;1:叶子节点)")
    private Boolean leaf;

    @ApiModelProperty("排序号")
    private Integer sortno;

    @ApiModelProperty("备注")
    private String remark;
}
