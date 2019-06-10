package com.smaller.jiview.admin.manager.impl;

import com.smaller.jiview.admin.manager.SysUserMenuPartManager;
import com.smaller.jiview.admin.platform.system.mapper.SysUserMenuPartMapper;
import com.smaller.jiview.admin.pojo.param.SysRoleMenuPartAuthParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xigf 2019/05/23
 */
@Service
public class SysUserMenuPartManagerImpl implements SysUserMenuPartManager {

    @Autowired
    private SysUserMenuPartMapper sysUserMenuPartMapper;

    @Override
    public void remove(Long userId, List<Long> menuIds) {
        sysUserMenuPartMapper.remove(userId, menuIds);
    }

    @Override
    public void save(List<SysRoleMenuPartAuthParam> list, Long userId, Long menuId) {

    }
}
