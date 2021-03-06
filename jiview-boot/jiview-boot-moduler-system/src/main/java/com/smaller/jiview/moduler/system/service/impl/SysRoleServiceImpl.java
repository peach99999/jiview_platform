package com.smaller.jiview.moduler.system.service.impl;

import com.smaller.jiview.core.constant.Constants;
import com.smaller.jiview.core.pojo.bo.ResultBO;
import com.smaller.jiview.core.pojo.dto.DiffDTO;
import com.smaller.jiview.core.pojo.dto.LoginUserDTO;
import com.smaller.jiview.core.util.BeanUtil;
import com.smaller.jiview.core.util.CommonUtil;
import com.smaller.jiview.moduler.system.manager.*;
import com.smaller.jiview.moduler.system.platform.system.mapper.SysRoleMapper;
import com.smaller.jiview.moduler.system.platform.system.mapper.SysRoleMenuPartMapper;
import com.smaller.jiview.moduler.system.platform.system.model.SysRole;
import com.smaller.jiview.moduler.system.pojo.model.ext.SysRoleExt;
import com.smaller.jiview.moduler.system.pojo.param.*;
import com.smaller.jiview.moduler.system.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xigf 2019/05/23
 */
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

    @Autowired
    private SysDeptManager sysDeptManager;

    @Autowired
    private SysRoleMenuPartMapper sysRoleMenuPartMapper;

    @Override
    public ResultBO<SysRoleExt> list(SysRoleListParam sysRoleListParam) {
        List<Long> subDeptIds = new ArrayList<>();
        // 查询部门下所有子部门的角色
        if (!ObjectUtils.isEmpty(sysRoleListParam.getDeptId())) {
            // 获取子部门角色
            subDeptIds.add(sysRoleListParam.getDeptId());
            subDeptIds = sysDeptManager.listDeptIds(subDeptIds, sysRoleListParam.getDeptId());
        }
        // PagerHelp分页 要紧接着跟查询语句
        pagerHelpManager.setStartPage(sysRoleListParam.getPageNo(), sysRoleListParam.getPageSize());
        List<SysRoleExt> sysRoleExtList = sysRoleMapper.list(sysRoleListParam.getRoleName(), subDeptIds);
        ResultBO<SysRoleExt> result = new ResultBO<>(sysRoleExtList);
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
        List<Long> newMenuIds = new ArrayList<>();
        // 角色菜单部件集
        roleUpdateMenuAuthParam.getSysRoleMenuParams().forEach(sysRoleMenuParam ->
                newMenuIds.add(sysRoleMenuParam.getMenuId())
        );
        // 角色旧的菜单权限id
        List<Long> oldMenuIds = sysRoleMenuManager.list(roleId);

        DiffDTO diff = CommonUtil.diff(newMenuIds, oldMenuIds);
        List<Long> menuIdsForRemove = diff.getDeleted();

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

    @Override
    public ResultBO updateMenuPartAuth(SysRoleMenuPartSaveParam sysRoleMenuPartSaveParam) {
        ResultBO resultBO = new ResultBO();
        List<Long> menuIdsForRemove = new ArrayList<>();
        menuIdsForRemove.add(sysRoleMenuPartSaveParam.getMenuId());
        // 删除角色菜单部件权限
        sysRoleMenuPartManager.remove(sysRoleMenuPartSaveParam.getRoleId(), menuIdsForRemove);
        // 新增角色菜单部件权限
        sysRoleMenuPartManager.save(sysRoleMenuPartSaveParam.getMenuPartList(), sysRoleMenuPartSaveParam.getRoleId(), sysRoleMenuPartSaveParam.getLoginUserDTO().getLoginUserPkid(), sysRoleMenuPartSaveParam.getMenuId());
        resultBO.setMsg("保存成功！");
        return resultBO;
    }

    @Override
    public ResultBO listRoleMenuPart(Long menuId, Long roleId) {
        ResultBO resultBO = new ResultBO();
        resultBO.setRows(sysRoleMenuPartMapper.listRoleMenuPart(menuId, roleId));
        return resultBO;
    }

    @Override
    public ResultBO listAllRoles() {
        ResultBO resultBO = new ResultBO();
        resultBO.setRows(sysRoleMapper.listAllRoles());
        return resultBO;
    }
}
