package com.smaller.jiview.admin.manager.impl;

import com.smaller.jiview.admin.manager.SysRoleManager;
import com.smaller.jiview.admin.platform.system.mapper.SysMenuMapper;
import com.smaller.jiview.admin.platform.system.mapper.SysRoleMapper;
import com.smaller.jiview.admin.pojo.model.ext.SysRoleExt;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by licm on 2018-11-10.
 */
@Service
public class SysRoleManagerImpl implements SysRoleManager {

    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Override
    public SysRoleExt get(Long rolePkid) {
        List<SysRoleExt> roles = sysRoleMapper.get(rolePkid);

        SysRoleExt role = null;
        if (CollectionUtils.isNotEmpty(roles)) {
            role = roles.get(0);
            role.setMenuIds(sysMenuMapper.listMenuPkidByRole(rolePkid));
        }
        return role;

    }
}
