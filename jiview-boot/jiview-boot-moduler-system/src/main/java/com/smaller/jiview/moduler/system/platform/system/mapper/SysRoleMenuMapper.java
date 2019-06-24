package com.smaller.jiview.moduler.system.platform.system.mapper;

import com.smaller.jiview.moduler.system.platform.system.model.SysRoleMenu;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

/**
 * @author xigf 2019/05/23
 */
public interface SysRoleMenuMapper extends Mapper<SysRoleMenu>, MySqlMapper<SysRoleMenu> {
    /**
     * 删除角色菜单信息
     *
     * @param rolePkid
     * @param menuIds
     * @return
     */
    Integer remove(@Param("roleId") Long rolePkid, @Param("menuIds") List<Long> menuIds);
}