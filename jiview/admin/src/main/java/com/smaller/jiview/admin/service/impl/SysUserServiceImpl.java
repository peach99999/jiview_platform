package com.smaller.jiview.admin.service.impl;

import com.smaller.jiview.admin.manager.PagerHelpManager;
import com.smaller.jiview.admin.manager.SysUserManager;
import com.smaller.jiview.admin.manager.SysUserRoleManager;
import com.smaller.jiview.admin.platform.system.mapper.SysRoleMenuPartMapper;
import com.smaller.jiview.admin.platform.system.mapper.SysUserMapper;
import com.smaller.jiview.admin.platform.system.mapper.SysUserRoleMapper;
import com.smaller.jiview.admin.platform.system.model.SysUser;
import com.smaller.jiview.admin.platform.system.model.SysUserRole;
import com.smaller.jiview.admin.pojo.model.ext.SysRoleMenuPartExt;
import com.smaller.jiview.admin.pojo.model.ext.SysUserExt;
import com.smaller.jiview.admin.pojo.model.ext.SysUserRoleExt;
import com.smaller.jiview.admin.pojo.param.SysUserListParam;
import com.smaller.jiview.admin.pojo.param.SysUserRemoveParam;
import com.smaller.jiview.admin.pojo.param.SysUserSaveOrUpdateParam;
import com.smaller.jiview.admin.service.SysUserService;
import com.smaller.jiview.core.pojo.bo.ResultBO;
import com.smaller.jiview.core.pojo.dto.LoginUserDTO;
import com.smaller.jiview.core.util.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

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
        if (sysUserRoleExtList.size() > 0) {
            sysUserExt.setSysUserRoleExtList(sysUserRoleExtList);
        }
        result.setRow(sysUserExt);
        return result;
    }

    @Override
    public ResultBO<SysRoleMenuPartExt> getUserMenuPartAuth(Long menuId, LoginUserDTO loginUserDTO) {
        ResultBO<SysRoleMenuPartExt> result = new ResultBO<>();
        result.setRows(sysRoleMenuPartMapper.listUserRoleMenuPart(menuId, loginUserDTO.getLoginUserPkid()));
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultBO saveOrUpdateUser(SysUserSaveOrUpdateParam sysUserSaveOrUpdateParam) {
        ResultBO result = new ResultBO();
        SysUser sysUser = new SysUser();
        BeanUtil.springCopy(sysUserSaveOrUpdateParam, sysUser);
        LoginUserDTO loginUserDTO = sysUserSaveOrUpdateParam.getLoginUserDTO();
        if (sysUserSaveOrUpdateParam.getId() != null) {
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

}
