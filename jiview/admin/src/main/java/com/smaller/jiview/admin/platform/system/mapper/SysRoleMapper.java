package com.smaller.jiview.admin.platform.system.mapper;

import com.smaller.jiview.admin.platform.system.model.SysRole;
import com.smaller.jiview.admin.pojo.model.ext.SysRoleExt;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

/**
 * @author xigf 2019/05/23
 */
public interface SysRoleMapper extends Mapper<SysRole>, MySqlMapper<SysRole> {
    /**
     * 查询角色列表
     *
     * @param deptId
     * @param roleName
     * @param subDeptIds
     * @return
     */
    List<SysRoleExt> list(@Param("deptId") long deptId, @Param("roleName") String roleName, @Param("subDeptIds") List<Long> subDeptIds);

    /**
     * 获取角色信息
     *
     * @param roleId
     * @return
     */
    List<SysRoleExt> get(Long roleId);
}