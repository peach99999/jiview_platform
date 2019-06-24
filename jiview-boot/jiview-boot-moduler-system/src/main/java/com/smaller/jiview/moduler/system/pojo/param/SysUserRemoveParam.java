package com.smaller.jiview.moduler.system.pojo.param;

import com.smaller.jiview.core.pojo.param.BaseParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * SysUserRemoveParam
 *
 * @author xiagf
 * @date 2019/05/13
 */
@Data
@ApiModel("用户删除参数")
public class SysUserRemoveParam extends BaseParam {
    @ApiModelProperty("userIdList")
    private List<Long> userIdList;
}
