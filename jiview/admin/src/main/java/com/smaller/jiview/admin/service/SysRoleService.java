package com.smaller.jiview.admin.service;

import com.smaller.jiview.admin.pojo.model.ext.SysRoleExt;
import com.smaller.jiview.admin.pojo.param.SysRoleListParam;
import com.smaller.jiview.admin.pojo.param.SysRoleRemoveParam;
import com.smaller.jiview.admin.pojo.param.SysRoleSaveOrUpdateParam;
import com.smaller.jiview.admin.pojo.param.SysRoleUpdateMenuAuthParam;
import com.smaller.jiview.core.pojo.bo.ResultBO;

/**
 * SysRoleService
 *
 * @author licm
 * @date 2018/11/10
 */
public interface SysRoleService {

    /**
     * 查询角色列表
     *
     * @param sysRoleListParam
     * @return ResultBo<SysRoleExt>
     */
    ResultBO<SysRoleExt> list(SysRoleListParam sysRoleListParam);

    /**
     * 获取角色详情
     *
     * @param roleId
     * @return ResultBo<AdminRoleExt>
     */
    ResultBO<SysRoleExt> get(Long roleId);

    /**
     * 保存角色信息
     *
     * @param sysRoleSaveOrUpdateParam
     * @return ResultBo
     */
    ResultBO saveOrUpdateRole(SysRoleSaveOrUpdateParam sysRoleSaveOrUpdateParam);

    /**
     * 批量删除角色
     *
     * @param sysRoleRemoveParam
     * @return ResultBo
     */
    ResultBO remove(SysRoleRemoveParam sysRoleRemoveParam);

    /**
     * 角色菜单权限
     * @param sysRoleUpdateMenuAuthParam
     * @return
     */
    ResultBO updateMenuAuth(SysRoleUpdateMenuAuthParam sysRoleUpdateMenuAuthParam);
}
