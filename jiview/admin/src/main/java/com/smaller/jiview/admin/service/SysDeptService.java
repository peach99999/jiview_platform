package com.smaller.jiview.admin.service;


import com.smaller.jiview.admin.platform.system.model.SysDept;
import com.smaller.jiview.admin.pojo.model.ext.SysDeptExt;
import com.smaller.jiview.admin.pojo.param.SysDeptListParam;
import com.smaller.jiview.admin.pojo.param.SysDeptRemoveParam;
import com.smaller.jiview.admin.pojo.param.SysDeptSaveOrUpdateParam;
import com.smaller.jiview.core.pojo.bo.ResultBO;

/**
 * 后台管理登录Service
 *
 * @author xiagf
 * @date 2019/04/30
 */
public interface SysDeptService {

    /**
     * 部门列表
     *
     * @param sysDeptListParam
     * @return
     */
    ResultBO<SysDeptExt> list(SysDeptListParam sysDeptListParam);

    /**
     * 部门详情
     *
     * @param deptId
     * @return
     */
    ResultBO get(Long deptId);

    /**
     * 批量删除部门信息
     *
     * @param sysDeptRemoveParam
     * @return ResultBO
     */
    ResultBO remove(SysDeptRemoveParam sysDeptRemoveParam);

    /**
     * 新增部门信息
     *
     * @param sysDeptSaveOrUpdateParam
     * @return ResultBO
     */
    ResultBO saveOrUpdateSysDept(SysDeptSaveOrUpdateParam sysDeptSaveOrUpdateParam);

    /**
     * 新增部门查询供选择部门下拉列表
     *
     * @return ResultBO
     */
    ResultBO listDept();

}
