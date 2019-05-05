package com.smaller.jiview.admin.service;


import com.smaller.jiview.admin.pojo.param.*;
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

    /**
     * 部门详情
     * @param deptId
     * @return
     */
    ResultBO get(Long deptId);

    /**
     * 批量删除部门信息
     *
     * @param sysDeptRemoveParam
     * @return ResultBo
     */
    ResultBO remove(SysDeptRemoveParam sysDeptRemoveParam);

    /**
     * 新增部门信息
     *
     * @param sysDeptSaveOrUpdateParam
     * @return ResultBo
     */
    ResultBO saveOrUpdateSysDept(SysDeptSaveOrUpdateParam sysDeptSaveOrUpdateParam);

    /**
     * 新增部门查询供选择部门下拉列表
     *
     * @return ResultBo
     */
    ResultBO listDept();

}
