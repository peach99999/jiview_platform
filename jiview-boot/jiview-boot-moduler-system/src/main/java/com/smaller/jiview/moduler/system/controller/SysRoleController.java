package com.smaller.jiview.moduler.system.controller;

import com.smaller.jiview.core.config.security.JwtHelper;
import com.smaller.jiview.core.constant.UrlConstants;
import com.smaller.jiview.core.pojo.bo.ResultBO;
import com.smaller.jiview.moduler.system.platform.system.model.SysRoleMenuPart;
import com.smaller.jiview.moduler.system.pojo.model.ext.SysRoleExt;
import com.smaller.jiview.moduler.system.pojo.param.*;
import com.smaller.jiview.moduler.system.service.SysRoleService;
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
     * @Description:查询全部有效角色
     * @author xiagf
     * @date 2019-06-10
     */
    @ApiOperation(value = "查询全部有效角色", httpMethod = "GET")
    @GetMapping(value = "/all-valid-roles")
    public ResultBO<SysRoleExt> listAllRoles(
    ) {
        return sysRoleService.listAllRoles();
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
            @RequestBody @Validated SysRoleMenuPartSaveParam sysRoleMenuPartSaveParam
    ) {
        sysRoleMenuPartSaveParam.setLoginUserDTO(jwtHelper.getLoginUserDTO());

        return sysRoleService.updateMenuPartAuth(sysRoleMenuPartSaveParam);
    }

    /**
     * @Description: 查询角色设置的菜单部件权限
     * @author xiagf
     * @date 2019-06-06
     */
    @ApiOperation(value = "查询角色设置的菜单部件权限", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "menuId", value = "菜单id", dataType = "long", paramType = "query", required = true),
            @ApiImplicitParam(name = "roleId", value = "角色id", dataType = "long", paramType = "query", required = true)
    })
    @GetMapping(value = "role-menu-part-auth")
    public ResultBO<SysRoleMenuPart> getRoleMenuPartAuth(
            @RequestParam(required = false) Long menuId,
            @RequestParam(required = false) Long roleId
    ) {
        //主处理
        return sysRoleService.listRoleMenuPart(menuId, roleId);
    }
}
