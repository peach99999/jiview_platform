package com.smaller.jiview.moduler.system.service;

import com.smaller.jiview.moduler.system.pojo.model.ext.SysMenuExt;
import com.smaller.jiview.moduler.system.pojo.param.MenuRemoveParam;
import com.smaller.jiview.moduler.system.pojo.param.MenuSaveParam;
import com.smaller.jiview.moduler.system.pojo.param.MenuUpdateParam;
import com.smaller.jiview.core.pojo.bo.ResultBO;

/**
 * AdminMenuService
 *
 * @author xiagf
 * @date 2018/11/13
 */
public interface SysMenuService {

    /**
     * 查询所有菜单
     *
     * @param
     * @return ResultBO<SysMenuExt>
     */
    ResultBO<SysMenuExt> list();

    /**
     * 获取用户菜单树
     *
     * @param loginPkid
     * @return ResultBO<SysMenuExt>
     */
    ResultBO listUserMenuTree(Long loginPkid);

    /**
     * 获取菜单树
     *
     * @param
     * @return ResultBO<SysMenuExt>
     */
    ResultBO listMenuTree();

    /**
     * 新增菜单
     *
     * @param adminMenuSaveParam
     * @return ResultBO
     */
    ResultBO save(MenuSaveParam adminMenuSaveParam);

    /**
     * 修改菜单
     *
     * @param adminMenuUpdateParam
     * @return ResultBO
     */
    ResultBO update(MenuUpdateParam adminMenuUpdateParam);

    /**
     * 批量删除菜单
     *
     * @param menuRemoveParam
     * @return
     */
    ResultBO remove(MenuRemoveParam menuRemoveParam);
}
