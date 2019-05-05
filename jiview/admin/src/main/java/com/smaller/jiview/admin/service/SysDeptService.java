package com.smaller.jiview.admin.service;


import com.smaller.jiview.admin.pojo.param.LoginParam;
import com.smaller.jiview.admin.pojo.param.ResetPwdParam;
import com.smaller.jiview.admin.pojo.param.SysDeptListParam;
import com.smaller.jiview.core.pojo.bo.ResultBO;
import com.smaller.jiview.core.pojo.model.SysDept;

import javax.servlet.http.HttpServletRequest;

/**
 * 后台管理登录Service
 *
 * @author xiagf
 * @date 2019/04/30
 */
public interface SysDeptService {

    /**
     * 部门列表
     * @param sysDeptListParam
     * @return
     */
    ResultBO<SysDept> list(SysDeptListParam sysDeptListParam);

}
