package com.smaller.jiview.admin.manager;

import com.smaller.jiview.admin.platform.system.model.SysRoleMenuPart;

import java.util.List;

/**
 * Created by xiagf on 2019-05-09
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
     */
    void save(List<SysRoleMenuPart> list, Long roleId, Long userId);
}
