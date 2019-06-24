package com.smaller.jiview.moduler.system.platform.system.mapper;

import com.smaller.jiview.moduler.system.platform.system.model.SysMenu;
import com.smaller.jiview.moduler.system.pojo.model.ext.SysMenuExt;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

/**
 * @author xigf 2019/05/23
 */
public interface SysMenuMapper extends Mapper<SysMenu>, MySqlMapper<SysMenu> {
    /**
     * 查询全部菜单信息
     *
     * @return
     */
    List<SysMenuExt> list();

    /**
     * 查询用户菜单信息
     *
     * @param userId
     * @return
     */
    List<SysMenuExt> listForUser(Long userId);

    /**
     * 查询角色菜单信息
     *
     * @param roleId
     * @return
     */
    List<Long> listMenuIdByRole(Long roleId);
}