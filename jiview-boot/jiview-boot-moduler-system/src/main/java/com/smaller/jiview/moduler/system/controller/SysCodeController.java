package com.smaller.jiview.moduler.system.controller;

import com.smaller.jiview.moduler.system.pojo.param.SysCodeListParam;
import com.smaller.jiview.moduler.system.service.SysCodeService;
import com.smaller.jiview.core.constant.UrlConstants;
import com.smaller.jiview.core.pojo.bo.ResultBO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xigf 2019/05/23
 */
@RestController
@RequestMapping(UrlConstants.ADMIN_SYS_CODE_PREFIX)
@Api("字典")
public class SysCodeController {

    @Autowired
    private SysCodeService sysCodeService;

    @ApiOperation(value = "获取code列表", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "codeType", value = "codeType", dataType = "string", paramType = "query", required = false)
    })
    @GetMapping(value = "")
    public ResultBO list(
            @RequestParam(value = "codeType", required = false) String codeType
    ) {
        SysCodeListParam sysCodeListParam = new SysCodeListParam();
        sysCodeListParam.setCodeType(codeType);

        return sysCodeService.list(sysCodeListParam);
    }

    @ApiOperation(value = "获取单条code", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "codeType", value = "code type", dataType = "string", paramType = "query", required = true),
            @ApiImplicitParam(name = "codeKey", value = "code key", dataType = "string", paramType = "query", required = true)}
    )
    @GetMapping(value = "/get-by-type-and-key")
    public ResultBO getByTypeAndKey(
            @RequestParam(value = "codeType", required = true) String codeType,
            @RequestParam(value = "codeKey", required = true) String codeKey
    ) {
        return sysCodeService.getByTypeAndKey(codeType, codeKey);
    }
}
