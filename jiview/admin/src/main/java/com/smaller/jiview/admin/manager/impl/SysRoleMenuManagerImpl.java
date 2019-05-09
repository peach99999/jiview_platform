package com.smaller.jiview.admin.manager.impl;

import com.smaller.jiview.admin.manager.SysRoleMenuManager;
import com.smaller.jiview.admin.platform.system.mapper.SysRoleMenuMapper;
import com.smaller.jiview.admin.platform.system.model.SysRoleMenu;
import com.smaller.jiview.core.pojo.dto.LoginUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by xuyq on 2019-03-01
 */
@Service
public class SysRoleMenuManagerImpl implements SysRoleMenuManager {
    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    public Integer save(SysRoleMenu sysRoleMenu, LoginUserDTO loginUserDTO) {
        sysRoleMenu.setCreateUserId(loginUserDTO.getLoginUserPkid());

        return sysRoleMenuMapper.insertSelective(sysRoleMenu);
    }
}
