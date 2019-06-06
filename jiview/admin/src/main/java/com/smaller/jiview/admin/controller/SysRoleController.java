package com.smaller.jiview.admin.controller;

import com.smaller.jiview.admin.pojo.model.ext.SysRoleExt;
import com.smaller.jiview.admin.pojo.param.*;
import com.smaller.jiview.admin.service.SysRoleService;
import com.smaller.jiview.core.config.security.JwtHelper;
import com.smaller.jiview.core.constant.UrlConstants;
import com.smaller.jiview.core.pojo.bo.ResultBO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author xigf 2019/05/23
 */
@RestController
@RequestMapping(UrlConstants.ADMIN_SYS_ROLE_PREFIX)
@Api("角色")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private JwtHelper jwtHelper;

    /**
     * @Description:按条件查找角色
     * @author xiagf
     * @date 2019-05-10
     */
    @ApiOperation(value = "获取角色列表", httpMethod = "GET")
    @GetMapping(value = "")
    public ResultBO<SysRoleExt> list(
            @ModelAttribute SysRoleListParam sysRoleListParam
    ) {
        return sysRoleService.list(sysRoleListParam);
    }

    /**
     * @Description:获取单条角色
     * @author xiagf
     * @date 2019-05-10
     */
    @ApiOperation(value = "获取单条角色", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色id", dataType = "long", paramType = "path", required = true)
    })
    @GetMapping(value = "/{roleId}")
    public ResultBO<SysRoleExt> get(
            @PathVariable Long roleId
    ) {
        return sysRoleService.get(roleId);
    }

    /**
     * @Description:保存更新角色
     * @author xiagf
     * @date 2019-05-10
     */
    @ApiOperation(value = "保存更新角色", httpMethod = "POST")
    @PostMapping(value = "/save-or-update")
    public ResultBO saveOrUpdate(
            @RequestBody @Validated SysRoleSaveOrUpdateParam sysRoleSaveOrUpdateParam
    ) {
        sysRoleSaveOrUpdateParam.setLoginUserDTO(jwtHelper.getLoginUserDTO());

        return sysRoleService.saveOrUpdateRole(sysRoleSaveOrUpdateParam);
    }

    /**
     * @Description: 删除角色
     * @author xiagf
     * @date 2019-05-10
     */
    @ApiOperation(value = "删除角色", httpMethod = "POST")
    @PostMapping(value = "/remove")
    public ResultBO remove(
            @RequestBody SysRoleRemoveParam sysRoleRemoveParam
    ) {
        sysRoleRemoveParam.setLoginUserDTO(jwtHelper.getLoginUserDTO());

        return sysRoleService.remove(sysRoleRemoveParam);
    }

    /**
     * @Description:配置资源(admin)
     * @author xiagf
     * @date 2019-05-10
     */
    @ApiOperation(value = "更新菜单权限", httpMethod = "POST")
    @PostMapping(value = "/update-menu-auth")
    public ResultBO updateMenuAuth(
            @RequestBody @Validated SysRoleUpdateMenuAuthParam sysRoleUpdateMenuAuthParam
    ) {
        sysRoleUpdateMenuAuthParam.setLoginUserDTO(jwtHelper.getLoginUserDTO());

        return sysRoleService.updateMenuAuth(sysRoleUpdateMenuAuthParam);
    }

    /**
     * @Description:配置角色菜单部件权限(admin)
     * @author xiagf
     * @date 2019-05-10
     */
    @ApiOperation(value = "更新菜单部件权限", httpMethod = "POST")
    @PostMapping(value = "/update-menu-part-auth")
    public ResultBO updateMenuPartAuth(
            @RequestBody @Validated SysRoleMenuPartSaveParam SysRoleMenuPartSaveParam
    ) {
        SysRoleMenuPartSaveParam.setLoginUserDTO(jwtHelper.getLoginUserDTO());

        return sysRoleService.updateMenuPartAuth(SysRoleMenuPartSaveParam);
    }
}
