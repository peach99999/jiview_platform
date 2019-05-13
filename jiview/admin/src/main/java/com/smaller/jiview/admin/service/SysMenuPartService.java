package com.smaller.jiview.admin.service;


import com.smaller.jiview.admin.pojo.param.SysMenuPartSaveOrupdateParam;
import com.smaller.jiview.core.pojo.bo.ResultBO;

/**
 * 部件管理Service
 *
 * @author xiagf
 * @date 2019/05/09
 */
public interface SysMenuPartService {

    /**
     * 菜单部件更新或删除
     *
     * @param sysMenuPartSaveOrupdateParam
     * @return
     */
    ResultBO menuPartSaveOrupdate(SysMenuPartSaveOrupdateParam sysMenuPartSaveOrupdateParam);

    /**
     * 获取菜单的所有组件信息
     *
     * @param menuId
     * @return
     */
    ResultBO get(Long menuId);
}
