package com.smaller.jiview.moduler.system.platform.system.mapper;

import com.smaller.jiview.moduler.system.platform.system.model.SysRole;
import com.smaller.jiview.moduler.system.pojo.model.ext.SysRoleExt;
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
     * @param roleName
     * @param subDeptIds
     * @return
     */
    List<SysRoleExt> list(@Param("roleName") String roleName, @Param("subDeptIds") List<Long> subDeptIds);

    /**
     * 获取角色信息
     *
     * @param roleId
     * @return
     */
    List<SysRoleExt> get(Long roleId);

    /**
     * 查询所有的有效角色
     *
     * @return
     */
    List<SysRole> listAllRoles();
}