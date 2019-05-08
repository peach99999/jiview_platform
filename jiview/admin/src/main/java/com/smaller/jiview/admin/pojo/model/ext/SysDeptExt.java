package com.smaller.jiview.admin.pojo.model.ext;

import com.smaller.jiview.admin.platform.system.model.SysDept;
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
@ApiModel("部门")
public class SysDeptExt extends SysDept {
    @ApiModelProperty("用户名称")
    private String userName;

    @ApiModelProperty("用户账户")
    private String account;

}
