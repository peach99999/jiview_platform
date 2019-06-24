package com.smaller.jiview.moduler.system.manager.impl;

import com.smaller.jiview.moduler.system.manager.SysUserMenuMapManager;
import com.smaller.jiview.moduler.system.platform.system.mapper.SysUserMenuMapMapper;
import com.smaller.jiview.moduler.system.platform.system.model.SysUserMenuMap;
import com.smaller.jiview.moduler.system.pojo.param.SysRoleMenuParam;
import com.smaller.jiview.core.pojo.dto.LoginUserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiagf on 2019-06-10
 */
@Service
public class SysUserMenuMapManagerImpl implements SysUserMenuMapManager {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SysUserMenuMapMapper sysUserMenuMapMapper;

    @Override
    public List<Long> list(Long userId) {
        List<Long> userMenuIds = new ArrayList<>();
        Example example = new Example(SysUserMenuMap.class);
        example.createCriteria().andEqualTo("userId", userId);
        List<SysUserMenuMap> list = sysUserMenuMapMapper.selectByExample(example);
        list.forEach(sysUserMenuMap -> userMenuIds.add(sysUserMenuMap.getMenuId()));
        return userMenuIds;
    }

    @Override
    public void saveOrUpdate(List<SysRoleMenuParam> list, Long userId, LoginUserDTO loginUserDTO) {
        // 1.删除菜单权限
        Example example = new Example(SysUserMenuMap.class);
        example.createCriteria().andEqualTo("userId", userId);
        int count = sysUserMenuMapMapper.selectCountByExample(example);
        if (count > 0) {
            sysUserMenuMapMapper.deleteByExample(example);
        }
        // 2.保存新增菜单权限
        list.forEach(sysRoleMenuParam -> {
            SysUserMenuMap sysUserMenuMap = new SysUserMenuMap();
            sysUserMenuMap.setUserId(userId);
            sysUserMenuMap.setMenuId(sysRoleMenuParam.getMenuId());
            sysUserMenuMap.setAuthorizeLevel(sysRoleMenuParam.getAuthorizeLevel());
            sysUserMenuMap.setCreateUserId(loginUserDTO.getLoginUserPkid());
            sysUserMenuMapMapper.insertSelective(sysUserMenuMap);
        });
    }

    @Override
    public void remove(Long userId, List<Long> menuIds) {
        sysUserMenuMapMapper.remove(userId, menuIds);
    }
}
