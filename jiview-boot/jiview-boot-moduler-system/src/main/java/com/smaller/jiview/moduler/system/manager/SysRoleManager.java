package com.smaller.jiview.moduler.system.manager;

import com.smaller.jiview.moduler.system.pojo.model.ext.SysRoleExt;

import java.util.List;

/**
 * @author xiagf on 2019/05/10.
 */
public interface SysRoleManager {

    /**
     * 获取用户信息
     *
     * @param roleId
     * @return
     */
    SysRoleExt get(Long roleId);

    /**
     * 删除角色信息
     *
     * @param roleIds
     * @return
     */
    Integer remove(List<Long> roleIds);

    /**
     * 查询角色列表
     *
     * @param list
     * @param subDeptIds
     * @return
     */
    List<SysRoleExt> listSubDeptRoles(List<SysRoleExt> list, List<Long> subDeptIds);

    /**
     * 过滤角色名称
     *
     * @param list
     * @param roleName
     * @return
     */
    List<SysRoleExt> roleNameFilter(List<SysRoleExt> list, String roleName);
}
