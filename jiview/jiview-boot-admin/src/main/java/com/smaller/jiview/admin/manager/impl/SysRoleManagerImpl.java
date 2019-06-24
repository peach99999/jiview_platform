package com.smaller.jiview.admin.manager.impl;

import com.github.pagehelper.Page;
import com.smaller.jiview.admin.manager.SysRoleManager;
import com.smaller.jiview.admin.platform.system.mapper.SysDeptMapper;
import com.smaller.jiview.admin.platform.system.mapper.SysMenuMapper;
import com.smaller.jiview.admin.platform.system.mapper.SysRoleMapper;
import com.smaller.jiview.admin.platform.system.mapper.SysRoleMenuMapper;
import com.smaller.jiview.admin.platform.system.model.SysDept;
import com.smaller.jiview.admin.platform.system.model.SysMenu;
import com.smaller.jiview.admin.platform.system.model.SysRole;
import com.smaller.jiview.admin.platform.system.model.SysRoleMenu;
import com.smaller.jiview.admin.pojo.model.ext.SysRoleExt;
import com.smaller.jiview.admin.pojo.param.MenuAuthParam;
import com.smaller.jiview.core.util.BeanUtil;
import com.smaller.jiview.core.util.CommonUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiagf on 2019-05-10.
 */
@Service
public class SysRoleManagerImpl implements SysRoleManager {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Autowired
    private SysDeptMapper sysDeptMapper;

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    public SysRoleExt get(Long roleId) {
        List<SysRoleExt> roles = sysRoleMapper.get(roleId);

        SysRoleExt role = null;
        List<MenuAuthParam> menuAuthParamList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(roles)) {
            role = roles.get(0);
            List<Long> menuIdList = sysMenuMapper.listMenuIdByRole(roleId);
            menuIdList.forEach(menuId -> {
                MenuAuthParam menuAuthParam = new MenuAuthParam();
                menuAuthParam.setMenuId(menuId);
                getMenuAuthLevel(roleId, menuAuthParamList, menuId, menuAuthParam);
            });
            role.setMenuIds(menuAuthParamList);
        }
        return role;

    }

    private void getMenuAuthLevel(Long roleId, List<MenuAuthParam> menuAuthParamList, Long menuId, MenuAuthParam menuAuthParam) {
        Example example = new Example(SysRoleMenu.class);
        example.createCriteria().andEqualTo("menuId", menuId)
                .andEqualTo("roleId", roleId);
        List<SysRoleMenu> sysRoleMenuList = sysRoleMenuMapper.selectByExample(example);
        SysRoleMenu sysRoleMenu = CommonUtil.getFirstElement(sysRoleMenuList);
        // 访问权限
        menuAuthParam.setAuthorizeLevel(sysRoleMenu.getAuthorizeLevel());
        // 带单名称
        SysMenu sysMenu = sysMenuMapper.selectByPrimaryKey(menuId);
        if (!ObjectUtils.isEmpty(sysMenu)) {
            menuAuthParam.setMenuName(sysMenu.getMenuName());
        }
        menuAuthParamList.add(menuAuthParam);
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
    public List<SysRoleExt> roleNameFilter(List<SysRoleExt> list, String roleName) {
        List<SysRoleExt> nameFilterLiist = new Page<SysRoleExt>();
        for (SysRoleExt sysRoleExt : list) {
            if (sysRoleExt.getRoleName().contains(roleName)) {
                nameFilterLiist.add(sysRoleExt);
            }
        }
        return nameFilterLiist;
    }


}
