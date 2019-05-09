package com.smaller.jiview.admin.manager.impl;

import com.smaller.jiview.admin.manager.SysUserRoleManager;
import com.smaller.jiview.admin.platform.system.mapper.SysUserRoleMapper;
import com.smaller.jiview.admin.platform.system.model.SysUserRole;
import com.smaller.jiview.core.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * Created by licm on 2018-11-13.
 */
@Service
public class SysUserRoleManagerImpl implements SysUserRoleManager {
    @Autowired
    private SysUserRoleMapper adminUserRoleMapper;

    @Override
    public SysUserRole getByUserPkid(Long loginUserPkid) {

        Example example = new Example(SysUserRole.class);
        example.createCriteria().andEqualTo("userId",loginUserPkid);
        List<SysUserRole> orgUserRoles = adminUserRoleMapper.selectByExample(example);

        return CommonUtil.getFirstElement(orgUserRoles);
    }

    @Override
    public Integer remove(Long userPkid) {
        return null;
    }
}
