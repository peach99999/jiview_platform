package com.smaller.jiview.admin.manager;

import com.smaller.jiview.admin.platform.system.model.SysMenu;
import com.smaller.jiview.admin.pojo.model.ext.SysMenuExt;
import com.smaller.jiview.admin.pojo.param.MenuRemoveParam;
import com.smaller.jiview.core.pojo.dto.LoginUserDTO;

import java.util.List;

/**
 * @author xuyq on 2019-05-09
 */
public interface SysMenuManager {
    /**
     * 查询特定用户的菜单树
     *
     * @param loginPkid
     * @return List<SysMenuExt>
     */

    List<SysMenuExt> listUserMenuTree(Long loginPkid);

    /**
     * 查询菜单树
     *
     * @return List<SysMenuExt>
     */
    List<SysMenuExt> listMenuTree();

    /**
     * 查询菜单列表
     *
     * @return List<SysMenuExt>
     */
    List<SysMenuExt> list();

    /**
     * 新增菜单
     *
     * @param sysMenu
     * @param loginUserDTO
     * @return Integer
     */
    Integer save(SysMenu sysMenu, LoginUserDTO loginUserDTO);

    /**
     * 更新菜单
     *
     * @param sysMenu
     * @param loginUserDTO
     * @return Integer
     */
    Integer update(SysMenu sysMenu, LoginUserDTO loginUserDTO);

    /**
     * 删除菜单
     *
     * @param menuRemoveParam
     * @return
     */
    Integer remove(MenuRemoveParam menuRemoveParam);
}
