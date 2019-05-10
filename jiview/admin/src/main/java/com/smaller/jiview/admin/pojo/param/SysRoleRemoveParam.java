package com.smaller.jiview.admin.pojo.param;

import com.smaller.jiview.core.pojo.param.BaseParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * SysRoleRemoveParam
 *
 * @author xiagf
 * @date 2018/11/10
 */
@Data
@ApiModel("角色删除参数")
public class SysRoleRemoveParam extends BaseParam {
    @ApiModelProperty("roleIdList")
    private List<Long> roleIdList;
}
