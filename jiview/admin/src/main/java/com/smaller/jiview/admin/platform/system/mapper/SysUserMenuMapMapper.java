package com.smaller.jiview.admin.platform.system.mapper;

import com.smaller.jiview.admin.platform.system.model.SysUserMenuMap;
import com.smaller.jiview.admin.pojo.model.ext.SysMenuExt;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

/**
 * @author xigf 2019/05/23
 */
public interface SysUserMenuMapMapper extends Mapper<SysUserMenuMap>, MySqlMapper<SysUserMenuMap> {
    /**
     * 查询用户菜单列表
     *
     * @param userId
     * @return
     */
    List<SysMenuExt> listForUserMenu(Long userId);

    /**
     * 删除角色菜单信息
     *
     * @param userId
     * @param menuIds
     * @return
     */
    Integer remove(@Param("userId") Long userId, @Param("menuIds") List<Long> menuIds);
}