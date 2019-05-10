package com.smaller.jiview.admin.platform.system.mapper;

import com.smaller.jiview.admin.platform.system.model.SysRoleMenuPart;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

public interface SysRoleMenuPartMapper extends Mapper<SysRoleMenuPart>, MySqlMapper<SysRoleMenuPart> {
    Integer remove(@Param("roleId") Long rolePkid, @Param("menuIds") List<Long> menuIds);
}