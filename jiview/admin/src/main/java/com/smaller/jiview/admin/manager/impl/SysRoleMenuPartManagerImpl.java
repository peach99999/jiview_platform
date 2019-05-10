package com.smaller.jiview.admin.manager.impl;

import com.smaller.jiview.admin.manager.SysRoleMenuPartManager;
import com.smaller.jiview.admin.platform.system.mapper.SysRoleMenuPartMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysRoleMenuPartManagerImpl implements SysRoleMenuPartManager {

    @Autowired
    private SysRoleMenuPartMapper sysRoleMenuPartMapper;

    @Override
    public void remove(Long roleId, List<Long> menuIds) {
        sysRoleMenuPartMapper.remove(roleId, menuIds);
    }
}
