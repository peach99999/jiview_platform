package com.smaller.jiview.admin.platform.system.mapper;

import com.smaller.jiview.admin.platform.system.model.SysRole;
import com.smaller.jiview.admin.pojo.model.ext.SysRoleExt;
import com.smaller.jiview.admin.pojo.param.SysRoleListParam;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

public interface SysRoleMapper extends Mapper<SysRole>, MySqlMapper<SysRole> {
    List<SysRoleExt> list(SysRoleListParam sysRoleListParam);

    List<SysRoleExt> get(Long roleId);
}