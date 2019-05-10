package com.smaller.jiview.admin.manager.impl;

import com.smaller.jiview.admin.manager.SysRoleMenuManager;
import com.smaller.jiview.admin.platform.system.mapper.SysRoleMenuMapper;
import com.smaller.jiview.admin.platform.system.model.SysRoleMenu;
import com.smaller.jiview.core.pojo.dto.LoginUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuyq on 2019-03-01
 */
@Service
public class SysRoleMenuManagerImpl implements SysRoleMenuManager {
    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    public Integer save(Long roleId, long menuId, LoginUserDTO loginUserDTO) {
        SysRoleMenu sysRoleMenu = new SysRoleMenu();
        sysRoleMenu.setMenuId(menuId);
        sysRoleMenu.setRoleId(roleId);
        sysRoleMenu.setCreateUserId(loginUserDTO.getLoginUserPkid());

        return sysRoleMenuMapper.insertSelective(sysRoleMenu);
    }

    @Override
    public List<Long> list(Long roleId) {
        Example example = new Example(SysRoleMenu.class);
        example.createCriteria().andEqualTo("roleId", roleId);
        List<SysRoleMenu> sysRoleMenuList = sysRoleMenuMapper.selectByExample(example);
        List<Long> menuIds = new ArrayList<>();
        sysRoleMenuList.forEach(sysRoleMenu -> menuIds.add(sysRoleMenu.getMenuId()));
        return menuIds;
    }

    @Override
    public void remove(Long roleId, List<Long> menuIds) {
        sysRoleMenuMapper.remove(roleId, menuIds);
    }
}
