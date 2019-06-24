package com.smaller.jiview.moduler.system.controller;

import com.smaller.jiview.moduler.system.platform.system.model.SysDept;
import com.smaller.jiview.moduler.system.pojo.model.ext.SysDeptExt;
import com.smaller.jiview.moduler.system.pojo.param.SysDeptListParam;
import com.smaller.jiview.moduler.system.pojo.param.SysDeptRemoveParam;
import com.smaller.jiview.moduler.system.pojo.param.SysDeptSaveOrUpdateParam;
import com.smaller.jiview.moduler.system.service.SysDeptService;
import com.smaller.jiview.core.config.security.JwtHelper;
import com.smaller.jiview.core.constant.UrlConstants;
import com.smaller.jiview.core.pojo.bo.ResultBO;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author xigf 2019/05/23
 */
@RestController
@RequestMapping(UrlConstants.ADMIN_SYS_DEPT_PREFIX)
@Api("部门管理")
public class SysDeptController {

    @Autowired
    private SysDeptService sysDeptService;

    @Autowired
    private JwtHelper jwtHelper;

    /**
     * @Description:部门管理列表
     * @author xiagf
     * @date 2019-04-30
     */
    @GetMapping(value = "")
    @ApiOperation(value = "部门管理列表", httpMethod = "GET")
    public ResultBO<SysDeptExt> list(
            @ModelAttribute SysDeptListParam sysDeptListParam
    ) {
        return sysDeptService.list(sysDeptListParam);
    }


    /**
     * @Description:按条件查找角色
     * @author xiagf
     * @date 2019-05-05
     */
    @ApiOperation(value = "获取部门详情", httpMethod = "GET")
    @GetMapping(value = "/{deptId}")
    public ResultBO<SysDept> get(
            @ApiParam(value = "deptId", required = true) @PathVariable Long deptId
    ) {
        //主处理
        return sysDeptService.get(deptId);
    }

    /**
     * @Description: 批量删除部门
     * @author xiagf
     * @date 2019-05-05
     */
    @ApiOperation(value = "批量删除部门", httpMethod = "POST")
    @PostMapping(value = "/remove")
    public ResultBO remove(
            @RequestBody SysDeptRemoveParam sysDeptRemoveParam
    ) {
        sysDeptRemoveParam.setLoginUserDTO(jwtHelper.getLoginUserDTO());
        //主处理
        return sysDeptService.remove(sysDeptRemoveParam);
    }


    /**
     * @Description:新增部门
     * @author xiagf
     * @date 2019-05-05
     */
    @ApiOperation(value = "新增部门", httpMethod = "POST")
    @PostMapping(value = "/save-or-update")
    public ResultBO saveOrUpdate(
            @RequestBody @Validated SysDeptSaveOrUpdateParam sysDeptSaveOrUpdateParam
    ) {
        sysDeptSaveOrUpdateParam.setLoginUserDTO(jwtHelper.getLoginUserDTO());

        return sysDeptService.saveOrUpdateSysDept(sysDeptSaveOrUpdateParam);
    }

    /**
     * @Description:新增部门供选择下拉部门列表
     * @author xiagf
     * @date 2019-05-05
     */
    @ApiOperation(value = "新增部门供选择下拉部门列表", httpMethod = "GET")
    @GetMapping(value = "/dept-list")
    public ResultBO deptList(
    ) {
        return sysDeptService.listDept();
    }
}
