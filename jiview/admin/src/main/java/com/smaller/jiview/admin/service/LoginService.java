package com.smaller.jiview.admin.service;


import com.smaller.jiview.admin.pojo.param.LoginParam;
import com.smaller.jiview.admin.pojo.param.ResetPwdParam;
import com.smaller.jiview.core.pojo.bo.ResultBO;

import javax.servlet.http.HttpServletRequest;

/**
 * 后台管理登录Service
 *
 * @author xiagf
 * @date 2019/04/30
 */
public interface LoginService {

    /**
     * 后台管理登录
     *
     * @param param
     * @param request
     * @return ResultBO
     */
    ResultBO login(LoginParam param, HttpServletRequest request);

    /**
     * 重置密码
     *
     * @param resetPwdParam
     * @return ResultBO
     */
    ResultBO resetPwd(ResetPwdParam resetPwdParam);
}
