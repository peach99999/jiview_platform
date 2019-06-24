package com.smaller.jiview.moduler.system.pojo.model.ext;

import com.smaller.jiview.moduler.system.platform.system.model.SysUserMenuPart;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * SysUserMenuPartExt
 *
 * @author xiagf
 * @date 2019-05-13
 */
@Data
@ApiModel("用户映射菜单部件权限")
public class SysUserMenuPartExt extends SysUserMenuPart {
    @ApiModelProperty("组件ID")
    private String cmpId;
}
