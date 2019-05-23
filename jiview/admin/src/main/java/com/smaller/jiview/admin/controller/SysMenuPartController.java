package com.smaller.jiview.admin.controller;

import com.smaller.jiview.admin.pojo.param.SysMenuPartSaveOrupdateParam;
import com.smaller.jiview.admin.service.SysMenuPartService;
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
@RequestMapping(UrlConstants.ADMIN_SYS_MENU_PART_PREFIX)
@Api("菜单部件")
public class SysMenuPartController {

    @Autowired
    private SysMenuPartService sysMenuPartService;

    @Autowired
    private JwtHelper jwtHelper;

    /**
     * @Description:菜单部件管理
     * @author xiagf
     * @date 2019-05-09
     */
    @ApiOperation(value = "新增或修改菜单部件", httpMethod = "POST")
    @PostMapping(value = "/save-or-update-menu-part")
    public ResultBO menuPartSaveOrupdate(@RequestBody SysMenuPartSaveOrupdateParam sysMenuPartSaveOrupdateParam) {
        sysMenuPartSaveOrupdateParam.setLoginUserDTO(jwtHelper.getLoginUserDTO());
        return sysMenuPartService.menuPartSaveOrupdate(sysMenuPartSaveOrupdateParam);
    }

    /**
     * @Description:查询菜单部件详情
     * @author xiagf
     * @date 2019-05-09
     */
    @ApiOperation(value = "查询菜单部件详情", httpMethod = "GET")
    @ApiImplicitParams({
    })
    @GetMapping(value = "/{menuId}")
    public ResultBO get(
            @ApiParam(value = "menuId", required = true) @PathVariable Long menuId
    ) {
        //主处理
        return sysMenuPartService.get(menuId);
    }
}
