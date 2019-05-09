package com.smaller.jiview.admin.platform.system.mapper;

import com.smaller.jiview.admin.platform.system.model.SysMenu;
import com.smaller.jiview.admin.pojo.model.ext.SysMenuExt;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

public interface SysMenuMapper extends Mapper<SysMenu>, MySqlMapper<SysMenu> {
    List<SysMenuExt> list();

    List<SysMenuExt> listForUser(Long userId);

    List<Long> listMenuPkidByRole(Long roleId);
}