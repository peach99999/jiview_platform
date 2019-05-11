package com.smaller.jiview.admin.pojo.param;

import com.smaller.jiview.core.pojo.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by xiagf on 2019-05-10.
 */
@Getter
@Setter
public class SysRoleUpdateMenuAuthParam extends BaseParam {

    @ApiModelProperty("roleId")
    private Long roleId;

    @ApiModelProperty("menuIds")
    private List<SysRoleMenuParam> sysRoleMenuParams;
}
