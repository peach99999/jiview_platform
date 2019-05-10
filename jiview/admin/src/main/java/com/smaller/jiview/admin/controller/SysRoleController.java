package com.smaller.jiview.admin.controller;

import com.smaller.jiview.admin.pojo.model.ext.SysRoleExt;
import com.smaller.jiview.admin.pojo.param.SysRoleUpdateMenuAuthParam;
import com.smaller.jiview.admin.pojo.param.SysRoleListParam;
import com.smaller.jiview.admin.pojo.param.SysRoleRemoveParam;
import com.smaller.jiview.admin.pojo.param.SysRoleSaveOrUpdateParam;
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

@RestController
@RequestMapping(UrlConstants.ADMIN_LOGIN_PREFIX)
@Api("登录")
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
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "页码", dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页数据量", dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "roleName", value = "角色名", dataType = "string", paramType = "query", required = false),
            @ApiImplicitParam(name = "deptId", value = "部门id", dataType = "long", paramType = "query", required = false)
    })
    @GetMapping(value = "")
    public ResultBO<SysRoleExt> list(
            @RequestParam(value = "pageNo", required = false) Integer pageNo,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "roleName", required = false) String roleName,
            @RequestParam(value = "deptId", required = false) Long deptId
    ) {
        SysRoleListParam sysRoleListParam = new SysRoleListParam();
        sysRoleListParam.setPageNo(pageNo);
        sysRoleListParam.setPageSize(pageSize);
        sysRoleListParam.setRoleName(roleName);
        sysRoleListParam.setDeptId(deptId);

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
    public ResultBO get(
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
            @RequestBody SysRoleUpdateMenuAuthParam sysRoleUpdateMenuAuthParam
    ) {
        sysRoleUpdateMenuAuthParam.setLoginUserDTO(jwtHelper.getLoginUserDTO());

        return sysRoleService.updateMenuAuth(sysRoleUpdateMenuAuthParam);
    }
}
