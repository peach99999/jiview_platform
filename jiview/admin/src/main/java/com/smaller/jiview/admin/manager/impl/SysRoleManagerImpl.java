package com.smaller.jiview.admin.manager.impl;

import com.github.pagehelper.Page;
import com.smaller.jiview.admin.manager.SysRoleManager;
import com.smaller.jiview.admin.platform.system.mapper.SysDeptMapper;
import com.smaller.jiview.admin.platform.system.mapper.SysMenuMapper;
import com.smaller.jiview.admin.platform.system.mapper.SysRoleMapper;
import com.smaller.jiview.admin.platform.system.model.SysDept;
import com.smaller.jiview.admin.platform.system.model.SysRole;
import com.smaller.jiview.admin.pojo.model.ext.SysRoleExt;
import com.smaller.jiview.admin.pojo.param.SysRoleListParam;
import com.smaller.jiview.core.util.BeanUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by xiagf on 2019-05-10.
 */
@Service
public class SysRoleManagerImpl implements SysRoleManager {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Autowired
    private SysDeptMapper sysDeptMapper;

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

    @Override
    public Integer remove(List<Long> roleIds) {
        int effectRowNum = 0;

        for (Long roleId : roleIds) {
            effectRowNum += sysRoleMapper.deleteByPrimaryKey(roleId);
        }
        return effectRowNum;
    }

    @Override
    public List<SysRoleExt> listSubDeptRoles(List<SysRoleExt> list, List<Long> subDeptIds) {
        for (Long deptId : subDeptIds) {

            Example example = new Example(SysRole.class);
            example.createCriteria().andEqualTo("deptId", deptId);
            SysDept sysDept = sysDeptMapper.selectByPrimaryKey(deptId);
            List<SysRole> subRoles = sysRoleMapper.selectByExample(example);
            subRoles.forEach(sysRole -> {
                SysRoleExt sysRoleExt = new SysRoleExt();
                BeanUtil.springCopy(sysRole, sysRoleExt);
                sysRoleExt.setDeptName(sysDept.getDeptName());
                list.add(sysRoleExt);
            });
        }
        return list;
    }

    @Override
    public List<SysRoleExt> roleNameFilter(List<SysRoleExt> list,String roleName) {
        List<SysRoleExt> nameFilterLiist = new Page<SysRoleExt>();
//        nameFilterLiist = list.stream().filter(sysRoleExt -> sysRoleExt.getRoleName().contains(roleName)).collect(Collectors.toList());
        for (SysRoleExt sysRoleExt:list) {
            if (sysRoleExt.getRoleName().contains(roleName)){
                nameFilterLiist.add(sysRoleExt);
            }
        }
        return nameFilterLiist;
    }


}
