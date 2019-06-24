package com.smaller.jiview.moduler.system.pojo.param;

import com.smaller.jiview.core.pojo.param.BaseListParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xigf on 2019/05/08.
 */
@Data
@ApiModel("用户查询参数")
public class SysUserListParam extends BaseListParam {

    @ApiModelProperty("部门id")
    private Long deptId;

    @ApiModelProperty("账号")
    private String account;

    @ApiModelProperty("姓名")
    private String userName;
}
