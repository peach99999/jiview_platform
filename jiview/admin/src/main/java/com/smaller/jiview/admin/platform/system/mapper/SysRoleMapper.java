package com.smaller.jiview.admin.platform.system.mapper;

import com.smaller.jiview.admin.platform.system.model.SysRole;
import com.smaller.jiview.admin.pojo.model.ext.SysRoleExt;
import com.smaller.jiview.admin.pojo.param.SysRoleListParam;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

public interface SysRoleMapper extends Mapper<SysRole>, MySqlMapper<SysRole> {
    List<SysRoleExt> list(@Param("deptId") long deptId, @Param("roleName") String roleName, @Param("subDeptIds") List<Long> subDeptIds);

    List<SysRoleExt> get(Long roleId);
}