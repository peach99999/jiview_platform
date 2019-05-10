package com.smaller.jiview.admin.platform.system.mapper;

import com.smaller.jiview.admin.platform.system.model.SysRoleMenu;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

public interface SysRoleMenuMapper extends Mapper<SysRoleMenu>, MySqlMapper<SysRoleMenu> {
    Integer remove(@Param("roleId") Long rolePkid, @Param("menuIds") List<Long> menuIds);
}