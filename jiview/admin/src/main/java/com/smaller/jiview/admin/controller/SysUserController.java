package com.smaller.jiview.admin.controller;

import com.smaller.jiview.admin.pojo.model.ext.SysRoleMenuPartExt;
import com.smaller.jiview.admin.pojo.model.ext.SysUserExt;
import com.smaller.jiview.admin.pojo.param.SysUserListParam;
import com.smaller.jiview.admin.pojo.param.SysUserRemoveParam;
import com.smaller.jiview.admin.pojo.param.SysUserSaveOrUpdateParam;
import com.smaller.jiview.admin.service.SysUserService;
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
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "页码", dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页数据量", dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "account", value = "账号", dataType = "string", paramType = "query", required = false),
            @ApiImplicitParam(name = "userName", value = "姓名", dataType = "string", paramType = "query", required = false),
            @ApiImplicitParam(name = "deptId", value = "部门id", dataType = "long", paramType = "query", required = false)
    })
    @GetMapping(value = "")
    public ResultBO<SysUserExt> list(
            @RequestParam(value = "pageNo", required = false) Integer pageNo,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "account", required = false) String account,
            @RequestParam(value = "userName", required = false) String userName,
            @RequestParam(value = "deptId", required = false) Long deptId
    ) {
        //前处理
        SysUserListParam sysUserListParam = new SysUserListParam();

        // 设置查询参数
        sysUserListParam.setPageNo(pageNo);
        sysUserListParam.setPageSize(pageSize);
        sysUserListParam.setAccount(account);
        sysUserListParam.setUserName(userName);
        sysUserListParam.setDeptId(deptId);

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
        return sysUserService.getUserMenuPartAuth(menuId,jwtHelper.getLoginUserDTO());
    }

    /**
     * @Description: 保存用户信息
     * @author xiagf
     * @date 2018-11-09
     */
    @ApiOperation(value = "保存用户信息(admin)", httpMethod = "POST")
    @RequestMapping(value = "/save-or-update-user", method = RequestMethod.POST)
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
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public ResultBO remove(
            @RequestBody SysUserRemoveParam sysUserRemoveParam
    ) {
        sysUserRemoveParam.setLoginUserDTO(jwtHelper.getLoginUserDTO());
        //主处理
        return sysUserService.remove(sysUserRemoveParam);
    }
}
