package com.smaller.jiview.admin.service;

import com.smaller.jiview.admin.pojo.model.ext.SysRoleExt;
import com.smaller.jiview.admin.pojo.param.*;
import com.smaller.jiview.core.pojo.bo.ResultBO;

/**
 * SysRoleService
 *
 * @author xiagf
 * @date 2019/05/28
 */
public interface SysRoleService {

    /**
     * 查询角色列表
     *
     * @param sysRoleListParam
     * @return ResultBO<SysRoleExt>
     */
    ResultBO<SysRoleExt> list(SysRoleListParam sysRoleListParam);

    /**
     * 获取角色详情
     *
     * @param roleId
     * @return ResultBO<AdminRoleExt>
     */
    ResultBO<SysRoleExt> get(Long roleId);

    /**
     * 保存角色信息
     *
     * @param sysRoleSaveOrUpdateParam
     * @return ResultBO
     */
    ResultBO saveOrUpdateRole(SysRoleSaveOrUpdateParam sysRoleSaveOrUpdateParam);

    /**
     * 批量删除角色
     *
     * @param sysRoleRemoveParam
     * @return ResultBO
     */
    ResultBO remove(SysRoleRemoveParam sysRoleRemoveParam);

    /**
     * 角色菜单权限
     *
     * @param sysRoleUpdateMenuAuthParam
     * @return
     */
    ResultBO updateMenuAuth(SysRoleUpdateMenuAuthParam sysRoleUpdateMenuAuthParam);

    /**
     * 角色菜单部件权限
     *
     * @param sysRoleMenuPartSaveParam
     * @return
     */
    ResultBO updateMenuPartAuth(SysRoleMenuPartSaveParam sysRoleMenuPartSaveParam);

    /**
     * 查询角色设置的菜单权限
     *
     * @param menuId
     * @param roleId
     * @return
     */
    ResultBO listRoleMenuPart(Long menuId, Long roleId);

    /**
     * 查询全部有效角色
     *
     * @return
     */
    ResultBO listAllRoles();
}
