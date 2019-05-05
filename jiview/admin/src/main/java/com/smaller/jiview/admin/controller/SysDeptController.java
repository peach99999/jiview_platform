package com.smaller.jiview.admin.controller;

import com.smaller.jiview.admin.pojo.param.SysDeptListParam;
import com.smaller.jiview.admin.pojo.param.SysDeptRemoveParam;
import com.smaller.jiview.admin.pojo.param.SysDeptSaveOrUpdateParam;
import com.smaller.jiview.admin.service.SysDeptService;
import com.smaller.jiview.core.config.security.JwtHelper;
import com.smaller.jiview.core.constant.UrlConstants;
import com.smaller.jiview.core.pojo.bo.ResultBO;
import com.smaller.jiview.core.pojo.model.SysDept;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


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
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "页码", dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize", value = "页容量", dataType = "int", paramType = "query", required = false),
    })
    public ResultBO<SysDept> list(
            @RequestParam(required = false) Integer pageNo,
            @RequestParam(required = false) Integer pageSize
    ) {
        SysDeptListParam sysDeptListParam = new SysDeptListParam();
        sysDeptListParam.setPageNo(pageNo);
        sysDeptListParam.setPageSize(pageSize);
        return sysDeptService.list(sysDeptListParam);
    }



    /**
     * @Description:按条件查找角色
     * @author licm
     * @date 2018-11-10
     */
    @ApiOperation(value = "获取部门详情", httpMethod = "GET")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/{deptId}", method = RequestMethod.GET)
    public ResultBO<SysDept> get(
            @ApiParam(value = "deptId", required = true) @PathVariable Long deptId
    ) {
        //主处理
        return sysDeptService.get(deptId);
    }

    /**
     * @Description: 批量删除角色
     * @author licm
     * @date 2018-11-09
     */
    @ApiOperation(value = "批量删除角色", httpMethod = "POST")
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public ResultBO remove(
            @RequestBody SysDeptRemoveParam sysDeptRemoveParam
            ) {
        sysDeptRemoveParam.setLoginUserDTO(jwtHelper.getLoginUserDTO());
        //主处理
        return sysDeptService.remove(sysDeptRemoveParam);
    }


    /**
     * @Description:新增部门
     * @author xuyq
     * @date 2019-03-01
     */
    @ApiOperation(value = "新增部门", httpMethod = "POST")
    @PostMapping(value = "/save-or-update")
    public ResultBO saveOrUpdate(
            @RequestBody @Validated SysDeptSaveOrUpdateParam sysDeptSaveOrUpdateParam
    ) {
        sysDeptSaveOrUpdateParam.setLoginUserDTO(jwtHelper.getLoginUserDTO());

        return sysDeptService.saveOrUpdateSysDept(sysDeptSaveOrUpdateParam);
    }
}
