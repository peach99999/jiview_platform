package com.smaller.jiview.admin.platform.system.mapper;

import com.smaller.jiview.admin.platform.system.model.SysRoleMenuPart;
import com.smaller.jiview.admin.pojo.model.ext.SysRoleMenuPartExt;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

public interface SysRoleMenuPartMapper extends Mapper<SysRoleMenuPart>, MySqlMapper<SysRoleMenuPart> {
    Integer remove(@Param("roleId") Long roleId, @Param("menuIds") List<Long> menuIds);

    List<SysRoleMenuPartExt> listUserRoleMenuPart(@Param("menuId") Long menuId, @Param("userId") Long userId);
}