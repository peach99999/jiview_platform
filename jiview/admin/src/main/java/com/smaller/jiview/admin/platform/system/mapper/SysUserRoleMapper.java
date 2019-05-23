package com.smaller.jiview.admin.platform.system.mapper;

import com.smaller.jiview.admin.platform.system.model.SysUserRole;
import com.smaller.jiview.admin.pojo.model.ext.SysUserRoleExt;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

/**
 * @author xigf 2019/05/23
 */
public interface SysUserRoleMapper extends Mapper<SysUserRole>, MySqlMapper<SysUserRole> {
    /**
     * 查询用户角色信息
     *
     * @param userId
     * @return
     */
    List<SysUserRoleExt> listUserRole(@Param("userId") Long userId);
}