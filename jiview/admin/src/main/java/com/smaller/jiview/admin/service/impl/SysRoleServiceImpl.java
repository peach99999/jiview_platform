package com.smaller.jiview.admin.service.impl;

import com.smaller.jiview.admin.manager.PagerHelpManager;
import com.smaller.jiview.admin.manager.SysRoleManager;
import com.smaller.jiview.admin.manager.SysRoleMenuManager;
import com.smaller.jiview.admin.manager.SysRoleMenuPartManager;
import com.smaller.jiview.admin.platform.system.mapper.SysRoleMapper;
import com.smaller.jiview.admin.platform.system.model.SysRole;
import com.smaller.jiview.admin.platform.system.model.SysRoleMenuPart;
import com.smaller.jiview.admin.pojo.model.ext.SysRoleExt;
import com.smaller.jiview.admin.pojo.param.SysRoleListParam;
import com.smaller.jiview.admin.pojo.param.SysRoleRemoveParam;
import com.smaller.jiview.admin.pojo.param.SysRoleSaveOrUpdateParam;
import com.smaller.jiview.admin.pojo.param.SysRoleUpdateMenuAuthParam;
import com.smaller.jiview.admin.service.SysRoleService;
import com.smaller.jiview.core.constant.Constants;
import com.smaller.jiview.core.pojo.bo.ResultBO;
import com.smaller.jiview.core.pojo.dto.DiffDTO;
import com.smaller.jiview.core.pojo.dto.LoginUserDTO;
import com.smaller.jiview.core.util.BeanUtil;
import com.smaller.jiview.core.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    private SysRoleManager sysRoleManager;

    @Autowired
    private SysRoleMenuManager sysRoleMenuManager;

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private PagerHelpManager pagerHelpManager;

    @Autowired
    private SysRoleMenuPartManager sysRoleMenuPartManager;

    @Override
    public ResultBO<SysRoleExt> list(SysRoleListParam sysRoleListParam) {
        // PagerHelp分页 要紧接着跟查询语句
        pagerHelpManager.setStartPage(sysRoleListParam.getPageNo(), sysRoleListParam.getPageSize());
        List<SysRoleExt> sysRoleExts = sysRoleMapper.list(sysRoleListParam);
        ResultBO<SysRoleExt> result = new ResultBO<>(sysRoleExts);
        return result;
    }

    @Override
    public ResultBO<SysRoleExt> get(Long roleId) {
        ResultBO<SysRoleExt> result = new ResultBO();
        result.setRow(sysRoleManager.get(roleId));
        return result;
    }

    @Override
    public ResultBO saveOrUpdateRole(SysRoleSaveOrUpdateParam sysRoleSaveOrUpdateParam) {
        ResultBO resultBo = new ResultBO<>();
        SysRole sysRole = new SysRole();
        // 拷贝角色数据
        BeanUtil.springCopy(sysRoleSaveOrUpdateParam, sysRole);
        // 角色id不为null, 则更新, 否则新增
        if (sysRoleSaveOrUpdateParam.getRoleId() != null) {
            resultBo.setOpResult(sysRoleMapper.updateByPrimaryKeySelective(sysRole));
        } else {
            sysRole.setCreateUserId(sysRoleSaveOrUpdateParam.getLoginUserDTO().getLoginUserPkid());
            resultBo.setOpResult(sysRoleMapper.insertSelective(sysRole));
        }
        resultBo.setRow(sysRole);

        return resultBo;
    }

    @Override
    public ResultBO remove(SysRoleRemoveParam sysRoleRemoveParam) {
        ResultBO resultBo = new ResultBO<>();
        List<Long> roleIds = sysRoleRemoveParam.getRoleIdList();
        sysRoleManager.remove(roleIds);
        resultBo.setOpResult(roleIds.size());
        return resultBo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultBO<Integer> updateMenuAuth(SysRoleUpdateMenuAuthParam roleUpdateMenuAuthParam) {
        ResultBO<Integer> result = new ResultBO<>();
        LoginUserDTO loginUserDTO = roleUpdateMenuAuthParam.getLoginUserDTO();

        Long roleId = roleUpdateMenuAuthParam.getRoleId();
        // 角色新的菜单权限id
        List<Long> newMenuPkids = new ArrayList<>();
        // 角色菜单部件集
        List<SysRoleMenuPart> sysRoleMenuPartList = new ArrayList<>();
        roleUpdateMenuAuthParam.getSysRoleMenuParams().forEach(sysRoleMenuParam -> {
            newMenuPkids.add(sysRoleMenuParam.getMenuId());
            sysRoleMenuPartList.addAll(sysRoleMenuParam.getMenuPartList());
        });
        // 角色旧的菜单权限id
        List<Long> oldMenuPkids = sysRoleMenuManager.list(roleId);

        DiffDTO diff = CommonUtil.diff(newMenuPkids, oldMenuPkids);
//        List<Long> menuIdsForSave = diff.getAdded();
        List<Long> menuIdsForRemove = diff.getDeleted();

//        menuIdsForSave.forEach(menuId -> sysRoleMenuManager.save(roleId, menuId, Constants.AUTHORIZE_LEVEL_1, loginUserDTO));
        // 保存或更新角色菜单权限
        sysRoleMenuManager.saveOrUpdate(roleUpdateMenuAuthParam.getSysRoleMenuParams(), roleId, loginUserDTO);

        // 删除角色菜单权限以及部件权限
        if (!menuIdsForRemove.isEmpty()) {
            sysRoleMenuManager.remove(roleId, menuIdsForRemove);
            sysRoleMenuPartManager.remove(roleId, menuIdsForRemove);
        }

        result.setOpResult(Constants.OP_RESULT_SUCCESS);
        return result;
    }
}
