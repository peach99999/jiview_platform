package com.smaller.jiview.admin.manager.impl;

import com.smaller.jiview.admin.manager.SysRoleMenuManager;
import com.smaller.jiview.admin.manager.SysRoleMenuPartManager;
import com.smaller.jiview.admin.platform.system.mapper.SysRoleMenuMapper;
import com.smaller.jiview.admin.platform.system.mapper.SysRoleMenuPartMapper;
import com.smaller.jiview.admin.platform.system.model.SysRoleMenu;
import com.smaller.jiview.admin.pojo.param.SysRoleMenuParam;
import com.smaller.jiview.core.pojo.dto.LoginUserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiagf on 2019-05-10
 */
@Service
public class SysRoleMenuManagerImpl implements SysRoleMenuManager {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    public Integer save(Long roleId, long menuId, Byte authorizeLevel, LoginUserDTO loginUserDTO) {
        SysRoleMenu sysRoleMenu = new SysRoleMenu();
        sysRoleMenu.setMenuId(menuId);
        sysRoleMenu.setRoleId(roleId);
        sysRoleMenu.setAuthorizeLevel(authorizeLevel);
        sysRoleMenu.setCreateUserId(loginUserDTO.getLoginUserPkid());

        return sysRoleMenuMapper.insertSelective(sysRoleMenu);
    }

    @Override
    public void saveOrUpdate(List<SysRoleMenuParam> list, Long roleId, LoginUserDTO loginUserDTO) {
        // 1.删除菜单权限
        Example example = new Example(SysRoleMenu.class);
        example.createCriteria().andEqualTo("roleId", roleId);
        int count = sysRoleMenuMapper.selectCountByExample(example);
        if (count > 0) {
            sysRoleMenuMapper.deleteByExample(example);
        }
        // 2.保存新增菜单权限
        list.forEach(sysRoleMenuParam -> {
            SysRoleMenu sysRoleMenu = new SysRoleMenu();
            sysRoleMenu.setRoleId(roleId);
            sysRoleMenu.setMenuId(sysRoleMenuParam.getMenuId());
            sysRoleMenu.setAuthorizeLevel(sysRoleMenuParam.getAuthorizeLevel());
            sysRoleMenu.setCreateUserId(loginUserDTO.getLoginUserPkid());
            sysRoleMenuMapper.insertSelective(sysRoleMenu);
        });
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
