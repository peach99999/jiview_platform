package com.smaller.jiview.moduler.system.service.impl;

import com.smaller.jiview.moduler.system.manager.*;
import com.smaller.jiview.moduler.system.platform.system.model.SysUser;
import com.smaller.jiview.moduler.system.platform.system.model.SysUserMenuPart;
import com.smaller.jiview.moduler.system.platform.system.model.SysUserRole;
import com.smaller.jiview.moduler.system.pojo.model.ext.*;
import com.smaller.jiview.moduler.system.pojo.param.*;
import com.smaller.jiview.moduler.system.service.SysUserService;
import com.smaller.jiview.core.pojo.bo.ResultBO;
import com.smaller.jiview.core.pojo.dto.DiffDTO;
import com.smaller.jiview.core.pojo.dto.LoginUserDTO;
import com.smaller.jiview.core.util.BeanUtil;
import com.smaller.jiview.core.util.CommonUtil;
import com.smaller.jiview.moduler.system.platform.system.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xigf 2019/05/23
 */
@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private PagerHelpManager pagerHelpManager;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysRoleMenuPartMapper sysRoleMenuPartMapper;

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Autowired
    private SysUserManager sysUserManager;

    @Autowired
    private SysUserRoleManager sysUserRoleManager;

    @Autowired
    private SysUserMenuPartMapper sysUserMenuPartMapper;

    @Autowired
    private SysUserMenuMapManager sysUserMenuMaPManager;

    @Autowired
    private SysUserMenuPartManager sysUserMenuPartManager;

    @Autowired
    private SysUserMenuMapMapper sysUserMenuMapMapper;

    @Override
    public ResultBO<SysUserExt> list(SysUserListParam sysUserListParam) {
        pagerHelpManager.setStartPage(sysUserListParam.getPageNo(), sysUserListParam.getPageSize());
        List<SysUserExt> list = sysUserMapper.list(sysUserListParam);
        ResultBO<SysUserExt> result = new ResultBO<>(list);
        return result;
    }

    @Override
    public ResultBO get(Long userId) {
        ResultBO<SysUserExt> result = new ResultBO<>();
        // 查询用户信息
        SysUser sysUser = sysUserMapper.selectByPrimaryKey(userId);
        SysUserExt sysUserExt = new SysUserExt();
        BeanUtil.springCopy(sysUser, sysUserExt);

        // 查询用户角色信息
        List<SysUserRoleExt> sysUserRoleExtList = sysUserRoleMapper.listUserRole(userId);
        if (!CollectionUtils.isEmpty(sysUserRoleExtList)) {
            sysUserExt.setSysUserRoleExtList(sysUserRoleExtList);
        }
        result.setRow(sysUserExt);
        return result;
    }

    @Override
    public ResultBO<SysRoleMenuPartExt> getUserMenuPartAuth(Long menuId, LoginUserDTO loginUserDTO) {
        ResultBO<SysRoleMenuPartExt> result = new ResultBO<>();
        List<SysRoleMenuPartExt> list = sysRoleMenuPartMapper.listUserRoleMenuPart(menuId, loginUserDTO.getLoginUserPkid());
        if (CollectionUtils.isEmpty(list)) {
            // 查询用户设置的菜单权限
            List<SysUserMenuPartExt> sysUserMenuPartExtList = sysUserMenuPartMapper.listUserMenuPart(menuId, loginUserDTO.getLoginUserPkid());
            List<SysRoleMenuPartExt> finalList = list;
            sysUserMenuPartExtList.forEach(sysUserMenuPartExt -> {
                SysRoleMenuPartExt sysRoleMenuPartExt = new SysRoleMenuPartExt();
                BeanUtil.springCopy(sysUserMenuPartExt, sysRoleMenuPartExt);
                finalList.add(sysRoleMenuPartExt);
            });
        }
        result.setRows(list);
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultBO saveOrUpdateUser(SysUserSaveOrUpdateParam sysUserSaveOrUpdateParam) {
        ResultBO result = new ResultBO();
        SysUser sysUser = new SysUser();
        BeanUtil.springCopy(sysUserSaveOrUpdateParam, sysUser);
        LoginUserDTO loginUserDTO = sysUserSaveOrUpdateParam.getLoginUserDTO();
        if (!ObjectUtils.isEmpty(sysUserSaveOrUpdateParam.getId())) {
            // 更新
            sysUserManager.update(sysUser, loginUserDTO);
        } else {
            // 新增
            sysUserManager.save(sysUser, loginUserDTO);
        }
        // 保存用户角色信息
        sysUserRoleManager.updateUserRoleInfo(sysUser.getId(), sysUserSaveOrUpdateParam.getRoleIdList(), loginUserDTO);
        result.setMsg("操作成功!");
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultBO remove(SysUserRemoveParam sysUserRemoveParam) {
        ResultBO result = new ResultBO();
        // 删除用户表中的用户信息以及用户角色信息
        for (Long userId : sysUserRemoveParam.getUserIdList()) {
            sysUserMapper.deleteByPrimaryKey(userId);
            Example example = new Example(SysUserRole.class);
            example.createCriteria().andEqualTo("userId", userId);
            sysUserRoleMapper.deleteByExample(example);
        }
        return result;
    }

    @Override
    public ResultBO updateUserMenuAuth(SysUserMenuAuthParam sysUserMenuAuthParam) {
        ResultBO<Integer> result = new ResultBO<>();
        LoginUserDTO loginUserDTO = sysUserMenuAuthParam.getLoginUserDTO();

        Long userId = sysUserMenuAuthParam.getUserId();
        // 角色新的菜单权限id
        List<Long> newMenuIds = new ArrayList<>();
        // 角色菜单部件集
        sysUserMenuAuthParam.getSysUserMenuParams().forEach(sysRoleMenuParam ->
                newMenuIds.add(sysRoleMenuParam.getMenuId())
        );
        // 角色旧的菜单权限id
        List<Long> oldMenuIds = sysUserMenuMaPManager.list(userId);

        DiffDTO diff = CommonUtil.diff(newMenuIds, oldMenuIds);
        List<Long> menuIdsForRemove = diff.getDeleted();

        // 保存或更新用户菜单权限
        sysUserMenuMaPManager.saveOrUpdate(sysUserMenuAuthParam.getSysUserMenuParams(), userId, loginUserDTO);

        // 删除角色菜单权限以及部件权限
        if (!menuIdsForRemove.isEmpty()) {
            sysUserMenuMaPManager.remove(userId, menuIdsForRemove);
            sysUserMenuPartManager.remove(userId, menuIdsForRemove);
        }
        result.setMsg("操作成功!");
        return result;
    }

    @Override
    public ResultBO updateUserMenuPartAuth(SysUserMenuPartSaveParam sysUserMenuPartSaveParam) {
        ResultBO resultBO = new ResultBO();
        List<Long> menuIdsForRemove = new ArrayList<>();
        menuIdsForRemove.add(sysUserMenuPartSaveParam.getMenuId());
        // 删除角色菜单部件权限
        sysUserMenuPartManager.remove(sysUserMenuPartSaveParam.getUserId(), menuIdsForRemove);
        // 新增角色菜单部件权限
        sysUserMenuPartManager.save(sysUserMenuPartSaveParam.getMenuPartList(), sysUserMenuPartSaveParam.getUserId(), sysUserMenuPartSaveParam.getMenuId(), sysUserMenuPartSaveParam.getLoginUserDTO().getLoginUserPkid());
        resultBO.setMsg("保存成功！");
        return resultBO;
    }

    @Override
    public ResultBO listUserMenuPart(Long menuId, Long userId) {
        ResultBO resultBO = new ResultBO();
        Example example = new Example(SysUserMenuPart.class);
        example.createCriteria().andEqualTo("menuId",menuId)
                .andEqualTo("userId",userId);
        List<SysUserMenuPart> list = sysUserMenuPartMapper.selectByExample(example);
        resultBO.setRows(list);
        return resultBO;
    }

    @Override
    public ResultBO listUserMenuMap(Long userId) {
        ResultBO resultBO = new ResultBO();
        List<SysMenuExt> list = sysUserMenuMapMapper.listForUserMenuMap(userId);
        resultBO.setRows(list);
        return resultBO;
    }

}
