package com.smaller.jiview.moduler.system.manager;

import com.smaller.jiview.moduler.system.pojo.param.SysRoleMenuPartAuthParam;

import java.util.List;

/**
 * @author xiagf on 2019-06-10
 */
public interface SysUserMenuPartManager {

    /**
     * 删除用户部件权限信息
     *
     * @param userId
     * @param menuIds
     */
    void remove(Long userId, List<Long> menuIds);

    /**
     * 保存用户菜单部件信息
     *
     * @param list
     * @param userId
     * @param menuId
     * @param creatUserId
     */
    void save(List<SysRoleMenuPartAuthParam> list, Long userId, Long menuId, Long creatUserId);
}
