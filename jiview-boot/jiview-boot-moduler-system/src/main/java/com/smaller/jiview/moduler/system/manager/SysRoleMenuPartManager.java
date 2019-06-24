package com.smaller.jiview.moduler.system.manager;

import com.smaller.jiview.moduler.system.pojo.param.SysRoleMenuPartAuthParam;

import java.util.List;

/**
 * @author xiagf on 2019-05-09
 */
public interface SysRoleMenuPartManager {

    /**
     * 删除角色部件权限信息
     *
     * @param roleId
     * @param menuIds
     */
    void remove(Long roleId, List<Long> menuIds);

    /**
     * 保存角色菜单部件信息
     *
     * @param list
     * @param roleId
     * @param userId
     * @param menuId
     */
    void save(List<SysRoleMenuPartAuthParam> list, Long roleId, Long userId, Long menuId);
}
