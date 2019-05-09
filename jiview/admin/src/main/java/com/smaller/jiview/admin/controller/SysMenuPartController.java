package com.smaller.jiview.admin.controller;

import com.smaller.jiview.admin.pojo.param.ResetPwdParam;
import com.smaller.jiview.admin.pojo.param.SysMenuPartSaveOrupdateParam;
import com.smaller.jiview.admin.service.LoginService;
import com.smaller.jiview.admin.service.SysMenuPartService;
import com.smaller.jiview.core.constant.UrlConstants;
import com.smaller.jiview.core.pojo.bo.ResultBO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(UrlConstants.ADMIN_SYS_MENU_PART_PREFIX)
@Api("菜单部件")
public class SysMenuPartController {

    @Autowired
    private SysMenuPartService sysMenuPartService;

    /**
     * @Description:菜单部件管理
     * @author xiagf
     * @date 2019-05-09
     */
    @ApiOperation(value = "新增或修改菜单部件", httpMethod = "POST")
    @PostMapping(value = "/save-or-update-menu-part")
    public ResultBO menuPartSaveOrupdate(@RequestBody SysMenuPartSaveOrupdateParam sysMenuPartSaveOrupdateParam) {
        return sysMenuPartService.menuPartSaveOrupdate(sysMenuPartSaveOrupdateParam);
    }
}
