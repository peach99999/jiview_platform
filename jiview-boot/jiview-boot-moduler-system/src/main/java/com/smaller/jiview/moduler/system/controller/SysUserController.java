package com.smaller.jiview.moduler.system.controller;

import com.smaller.jiview.core.config.security.JwtHelper;
import com.smaller.jiview.core.constant.UrlConstants;
import com.smaller.jiview.core.pojo.bo.ResultBO;
import com.smaller.jiview.moduler.system.platform.system.model.SysUserMenuPart;
import com.smaller.jiview.moduler.system.pojo.model.ext.SysRoleMenuPartExt;
import com.smaller.jiview.moduler.system.pojo.model.ext.SysUserExt;
import com.smaller.jiview.moduler.system.pojo.param.*;
import com.smaller.jiview.moduler.system.service.SysUserService;
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
@RequestMapping(UrlConstants.ADMIN_SYS_USER_PREFIX)
@Api("系统用户管理")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private JwtHelper jwtHelper;

    /**
     * @Description:按条件查询用户(admin)
     * @author xiagf
     * @date 2019-05-08
     */
    @ApiOperation(value = "获取用户列表", httpMethod = "GET")
    @GetMapping(value = "")
    public ResultBO<SysUserExt> list(
            @ModelAttribute SysUserListParam sysUserListParam
    ) {
        //主处理
        return sysUserService.list(sysUserListParam);
    }

    /**
     * @Description: 查询用户详情信息
     * @author xiagf
     * @date 2019-05-09
     */
    @ApiOperation(value = "根据userId获取用户信息", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", dataType = "long", paramType = "path", required = true)
    })
    @GetMapping(value = "/{userId}")
    public ResultBO get(
            @PathVariable Long userId
    ) {
        //主处理
        return sysUserService.get(userId);
    }

    /**
     * @Description: 查询用户菜单部件权限
     * @author xiagf
     * @date 2019-05-13
     */
    @ApiOperation(value = "查询用户菜单部件权限", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "menuId", value = "菜单id", dataType = "long", paramType = "path", required = true)
    })
    @GetMapping(value = "menu-part-auth/{menuId}")
    public ResultBO<SysRoleMenuPartExt> getUserMenuPartAuth(
            @PathVariable Long menuId
    ) {
        //主处理
        return sysUserService.getUserMenuPartAuth(menuId, jwtHelper.getLoginUserDTO());
    }

    /**
     * @Description: 保存用户信息
     * @author xiagf
     * @date 2018-11-09
     */
    @ApiOperation(value = "保存用户信息(admin)", httpMethod = "POST")
    @PostMapping(value = "/save-or-update-user")
    public ResultBO saveOrUpdateUser(
            @RequestBody @Validated SysUserSaveOrUpdateParam sysUserSaveOrUpdateParam
    ) {
        sysUserSaveOrUpdateParam.setLoginUserDTO(jwtHelper.getLoginUserDTO());
        //主处理
        return sysUserService.saveOrUpdateUser(sysUserSaveOrUpdateParam);
    }

    /**
     * @Description: 批量删除用户
     * @author xiagf
     * @date 2019-05-13
     */
    @ApiOperation(value = "批量删除用户", httpMethod = "POST")
    @PostMapping(value = "/remove")
    public ResultBO remove(
            @RequestBody SysUserRemoveParam sysUserRemoveParam
    ) {
        sysUserRemoveParam.setLoginUserDTO(jwtHelper.getLoginUserDTO());
        //主处理
        return sysUserService.remove(sysUserRemoveParam);
    }

    /**
     * @Description:用户配置资源
     * @author xiagf
     * @date 2019-06-10
     */
    @ApiOperation(value = "配置用户菜单权限", httpMethod = "POST")
    @PostMapping(value = "/update-menu-auth")
    public ResultBO updateMenuAuth(
            @RequestBody @Validated SysUserMenuAuthParam sysUserMenuAuthParam
    ) {
        sysUserMenuAuthParam.setLoginUserDTO(jwtHelper.getLoginUserDTO());

        return sysUserService.updateUserMenuAuth(sysUserMenuAuthParam);
    }

    /**
     * @Description:配置用户菜单部件权限
     * @author xiagf
     * @date 2019-06-10
     */
    @ApiOperation(value = "配置用户菜单部件权限", httpMethod = "POST")
    @PostMapping(value = "/update-menu-part-auth")
    public ResultBO updateMenuPartAuth(
            @RequestBody @Validated SysUserMenuPartSaveParam sysUserMenuPartSaveParam
    ) {
        sysUserMenuPartSaveParam.setLoginUserDTO(jwtHelper.getLoginUserDTO());

        return sysUserService.updateUserMenuPartAuth(sysUserMenuPartSaveParam);
    }

    /**
     * @Description: 查询用户设置的菜单部件权限
     * @author xiagf
     * @date 2019-06-06
     */
    @ApiOperation(value = "查询用户设置的菜单部件权限", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "menuId", value = "菜单id", dataType = "long", paramType = "query", required = true),
            @ApiImplicitParam(name = "userId", value = "用户id", dataType = "long", paramType = "query", required = true)
    })
    @GetMapping(value = "user-menu-part-auth")
    public ResultBO<SysUserMenuPart> getUserMenuPartAuth(
            @RequestParam(required = false) Long menuId,
            @RequestParam(required = false) Long userId
    ) {
        //主处理
        return sysUserService.listUserMenuPart(menuId, userId);
    }

    /**
     * @Description: 查询用户设置的菜单信息
     * @author xiagf
     * @date 2019-06-12
     */
    @ApiOperation(value = "查询用户设置的菜单信息", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", dataType = "long", paramType = "path", required = true)
    })
    @GetMapping(value = "/list-user-menu-map/{userId}")
    public ResultBO listUserMenu(
            @PathVariable Long userId
    ) {
        //主处理
        return sysUserService.listUserMenuMap(userId);
    }
}
