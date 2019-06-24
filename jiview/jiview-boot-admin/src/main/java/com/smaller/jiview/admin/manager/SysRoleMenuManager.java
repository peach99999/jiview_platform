package com.smaller.jiview.admin.manager;

import com.smaller.jiview.admin.pojo.param.SysRoleMenuParam;
import com.smaller.jiview.core.pojo.dto.LoginUserDTO;

import java.util.List;

/**
 * @author xiagf on 2019-05-09
 */
public interface SysRoleMenuManager {
    /**
     * 保存角色菜单信息
     *
     * @param roleId
     * @param menuId
     * @param authorizeLevel
     * @param loginUserDTO
     * @return
     */
    Integer save(Long roleId, long menuId, Byte authorizeLevel, LoginUserDTO loginUserDTO);

    /**
     * 角色菜单权限以及部件权限保存更新
     *
     * @param list
     * @param roleId
     * @param loginUserDTO
     */
    void saveOrUpdate(List<SysRoleMenuParam> list, Long roleId, LoginUserDTO loginUserDTO);

    /**
     * 根据角色Id查询角色的菜单
     *
     * @param roleId
     * @return
     */
    List<Long> list(Long roleId);

    /**
     * 删除角色对应的菜单信息
     *
     * @param roleId
     * @param menuIds
     */
    void remove(Long roleId, List<Long> menuIds);
}
