package com.smaller.jiview.admin.controller;

import com.smaller.jiview.admin.pojo.param.MenuRemoveParam;
import com.smaller.jiview.admin.pojo.param.MenuSaveParam;
import com.smaller.jiview.admin.pojo.param.MenuUpdateParam;
import com.smaller.jiview.admin.service.SysMenuService;
import com.smaller.jiview.core.config.security.JwtHelper;
import com.smaller.jiview.core.constant.UrlConstants;
import com.smaller.jiview.core.pojo.bo.ResultBO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author xigf 2019/05/23
 */
@RestController
@RequestMapping(UrlConstants.ADMIN_SYS_MENU_PREFIX)
@Api("菜单")
public class SysMenuController {

    @Autowired
    private SysMenuService menuService;

    @Autowired
    private JwtHelper jwtHelper;

    /**
     * @Description:查询所有菜单
     * @author xiagf
     * @date 2019-05-09
     */
    @GetMapping(value = "")
    @ApiOperation(value = "获取菜单列表", httpMethod = "GET")
    public ResultBO list() {
        return menuService.list();
    }

    /**
     * @Description:获取用户菜单树
     * @author xiagf
     * @date 2019-05-09
     */
    @ApiOperation(value = "获取用户菜单树", httpMethod = "GET")
    @GetMapping(value = "/user-menu-tree")
    public ResultBO listUserMenuTree() {
        return menuService.listUserMenuTree(jwtHelper.getLoginUserDTO().getLoginUserPkid());
    }

    /**
     * @Description:获取菜单树
     * @author xiagf
     * @date 2019-05-09
     */
    @ApiOperation(value = "获取菜单树", httpMethod = "GET")
    @GetMapping(value = "/menu-tree")
    public ResultBO listMenuTree() {
        return menuService.listMenuTree();
    }

    /**
     * @Description:新增菜单(admin)
     * @author xiagf
     * @date 2019-05-09
     */
    @ApiOperation(value = "新增菜单", httpMethod = "POST")
    @PostMapping(value = "")
    public ResultBO save(
            @ApiParam(value = "menu", required = true)
            @RequestBody MenuSaveParam menuSaveParam
    ) {
        menuSaveParam.setLoginUserDTO(jwtHelper.getLoginUserDTO());

        return menuService.save(menuSaveParam);
    }

    /**
     * @Description:修改菜单(admin)
     */
    @ApiOperation(value = "修改菜单", httpMethod = "PUT")
    @PutMapping(value = "/{menuId}")
    public ResultBO update(
            @ApiParam(value = "menuId", required = true) @PathVariable Long menuId,
            @ApiParam(value = "menu", required = true) @RequestBody MenuUpdateParam menuUpdateParam
    ) {
        menuUpdateParam.setLoginUserDTO(jwtHelper.getLoginUserDTO());
        menuUpdateParam.setMenuId(menuId);

        return menuService.update(menuUpdateParam);
    }

    /**
     * @Description:删除菜单数组(admin)
     * @author xiagf
     * @date 2019-05-09
     */
    @ApiOperation(value = "删除菜单", httpMethod = "POST")
    @ApiImplicitParams({})
    @PostMapping(value = "/remove")
    public ResultBO remove(
            @RequestBody MenuRemoveParam menuRemoveParam
    ) {
        menuRemoveParam.setLoginUserDTO(jwtHelper.getLoginUserDTO());

        return menuService.remove(menuRemoveParam);
    }
}
