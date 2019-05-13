package com.smaller.jiview.admin.platform.system.mapper;

import com.smaller.jiview.admin.platform.system.model.SysUserMenuMap;
import com.smaller.jiview.admin.pojo.model.ext.SysMenuExt;
import com.smaller.jiview.admin.pojo.model.ext.SysUserExt;
import org.hibernate.validator.constraints.SafeHtml;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

public interface SysUserMenuMapMapper extends Mapper<SysUserMenuMap>, MySqlMapper<SysUserMenuMap> {
    List<SysMenuExt> listForUserMenu(Long userId);
}