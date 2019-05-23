package com.smaller.jiview.admin.platform.system.mapper;

import com.smaller.jiview.admin.platform.system.model.SysUser;
import com.smaller.jiview.admin.pojo.model.ext.SysUserExt;
import com.smaller.jiview.admin.pojo.param.SysUserListParam;
import jdk.internal.dynalink.linker.LinkerServices;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

/**
 * @author xigf 2019/05/23
 */
public interface SysUserMapper extends Mapper<SysUser>, MySqlMapper<SysUser> {
    /**
     * 查询用户列表
     *
     * @param sysUserListParam
     * @return
     */
    List<SysUserExt> list(SysUserListParam sysUserListParam);
}