package com.smaller.jiview.admin.platform.system.mapper;

import com.smaller.jiview.admin.platform.system.model.SysUserMenuPart;
import com.smaller.jiview.admin.pojo.model.ext.SysUserMenuPartExt;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

public interface SysUserMenuPartMapper extends Mapper<SysUserMenuPart>, MySqlMapper<SysUserMenuPart> {
    List<SysUserMenuPartExt> listUserMenuPart(@Param("menuId") Long menuId, @Param("userId") Long userId);
}