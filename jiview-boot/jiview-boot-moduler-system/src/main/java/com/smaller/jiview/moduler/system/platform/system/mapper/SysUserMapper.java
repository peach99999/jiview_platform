package com.smaller.jiview.moduler.system.platform.system.mapper;

import com.smaller.jiview.moduler.system.platform.system.model.SysUser;
import com.smaller.jiview.moduler.system.pojo.model.ext.SysUserExt;
import com.smaller.jiview.moduler.system.pojo.param.SysUserListParam;
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