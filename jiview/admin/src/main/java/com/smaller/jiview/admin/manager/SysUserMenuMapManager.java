package com.smaller.jiview.admin.manager;

import com.smaller.jiview.admin.pojo.param.SysRoleMenuParam;
import com.smaller.jiview.core.pojo.dto.LoginUserDTO;

import java.util.List;

/**
 * @author xiagf on 2019-06-10
 */
public interface SysUserMenuMapManager {

    /**
     * 根据用户Id查询用户的菜单集合
     *
     * @param userId
     * @return
     */
    List<Long> list(Long userId);

    /**
     * 用户菜单权限保存更新
     *
     * @param list
     * @param userId
     * @param loginUserDTO
     */
    void saveOrUpdate(List<SysRoleMenuParam> list, Long userId, LoginUserDTO loginUserDTO);

    /**
     * 删除用户对应的菜单信息
     *
     * @param userId
     * @param menuIds
     */
    void remove(Long userId, List<Long> menuIds);

}
