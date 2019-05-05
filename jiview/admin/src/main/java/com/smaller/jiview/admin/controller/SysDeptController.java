package com.smaller.jiview.admin.controller;

import com.smaller.jiview.admin.pojo.param.LoginParam;
import com.smaller.jiview.admin.pojo.param.ResetPwdParam;
import com.smaller.jiview.admin.pojo.param.SysDeptListParam;
import com.smaller.jiview.admin.service.LoginService;
import com.smaller.jiview.admin.service.SysDeptService;
import com.smaller.jiview.core.constant.UrlConstants;
import com.smaller.jiview.core.pojo.bo.ResultBO;
import com.smaller.jiview.core.pojo.dto.UserForReturnDTO;
import com.smaller.jiview.core.pojo.model.SysDept;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(UrlConstants.ADMIN_SYS_DEPT_PREFIX)
@Api("部门管理")
public class SysDeptController {

    @Autowired
    private SysDeptService sysDeptService;

    /**
     * @Description:部门管理列表
     * @author xiagf
     * @date 2019-04-30
     */
    @GetMapping(value = "")
    @ApiOperation(value = "获取菜单列表", httpMethod = "GET")
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

}
