package com.smaller.jiview.admin.manager;


import com.smaller.jiview.admin.platform.system.model.SysMenuPart;

import java.util.List;

/**
 * @author xiagf on 2019/05/09
 */
public interface SysMenuPartManager {

    /**
     * 查询菜单下的部件
     * @param menuId
     * @return List<SysMenuPart>
     */
    List<SysMenuPart> listMenuPart(Long menuId);

    /**
     * 删除
     * @param menuId
     */
    void remove(Long menuId);

}
