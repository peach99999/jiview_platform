package com.smaller.jiview.admin.pojo.param;

import com.smaller.jiview.core.pojo.param.BaseParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * SysDeptRemoveParam
 *
 * @author xiagf
 * @date 2019/05/05
 */
@Data
@ApiModel("部门删除参数")
public class SysDeptRemoveParam extends BaseParam {
    @ApiModelProperty("deptIdList")
    private List<Long> deptIdList;
}
