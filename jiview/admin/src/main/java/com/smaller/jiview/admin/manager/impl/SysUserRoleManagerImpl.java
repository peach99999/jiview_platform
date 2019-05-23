package com.smaller.jiview.admin.manager.impl;

import com.smaller.jiview.admin.manager.SysUserRoleManager;
import com.smaller.jiview.admin.platform.system.mapper.SysUserRoleMapper;
import com.smaller.jiview.admin.platform.system.model.SysUserRole;
import com.smaller.jiview.core.pojo.dto.LoginUserDTO;
import com.smaller.jiview.core.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author licm on 2018-11-13.
 */
@Service
public class SysUserRoleManagerImpl implements SysUserRoleManager {
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Override
    public List<SysUserRole> getByUserPkid(Long loginUserPkid) {

        Example example = new Example(SysUserRole.class);
        example.createCriteria().andEqualTo("userId", loginUserPkid);
        List<SysUserRole> orgUserRoles = sysUserRoleMapper.selectByExample(example);

        return orgUserRoles;
    }

    @Override
    public void remove(Long userId) {
        Example example = new Example(SysUserRole.class);
        example.createCriteria().andEqualTo("userId", userId);
        sysUserRoleMapper.deleteByExample(example);
    }

    @Override
    public void updateUserRoleInfo(Long userId, List<Long> roleIdList, LoginUserDTO loginUserDTO) {
        // 先删除用户角色
        this.remove(userId);
        // 新增用户角色
        for (Long roleId: roleIdList){
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setUserId(userId);
            sysUserRole.setRoleId(roleId);
            sysUserRole.setCreateUserId(loginUserDTO.getLoginUserPkid());
            sysUserRoleMapper.insertSelective(sysUserRole);
        }

    }
}
