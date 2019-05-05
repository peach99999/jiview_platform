package com.smaller.jiview.admin.controller;

import com.smaller.jiview.admin.pojo.param.LoginParam;
import com.smaller.jiview.admin.pojo.param.ResetPwdParam;
import com.smaller.jiview.admin.service.LoginService;
import com.smaller.jiview.core.constant.UrlConstants;
import com.smaller.jiview.core.pojo.bo.ResultBO;
import com.smaller.jiview.core.pojo.dto.UserForReturnDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(UrlConstants.ADMIN_LOGIN_PREFIX)
@Api("登录")
public class LoginController {
    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private LoginService loginService;

    /**
     * @Description:后台管理登录
     * @author xiagf
     * @date 2019-03-01
     */
    @ApiOperation(value = "后台管理登录", httpMethod = "POST")
    @PostMapping(value = "")
    public ResultBO<UserForReturnDTO> loginAdmin(@RequestBody LoginParam loginParam) {
        return loginService.login(loginParam, httpServletRequest);
    }

    /**
     * @Description:重置Admin密码
     * @author xiagf
     * @date 2019-03-01
     */
    @ApiOperation(value = "重置Admin密码", httpMethod = "POST")
    @PostMapping(value = "/reset-pwd")
    public ResultBO resetAdminPwd(@RequestBody final ResetPwdParam resetPwdParam) {
        return loginService.resetPwd(resetPwdParam);
    }
}
