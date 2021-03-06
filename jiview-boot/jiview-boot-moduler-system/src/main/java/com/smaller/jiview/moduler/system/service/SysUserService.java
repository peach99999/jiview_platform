package com.smaller.jiview.moduler.system.service;


import com.smaller.jiview.core.pojo.bo.ResultBO;
import com.smaller.jiview.core.pojo.dto.LoginUserDTO;
import com.smaller.jiview.moduler.system.pojo.model.ext.SysRoleMenuPartExt;
import com.smaller.jiview.moduler.system.pojo.model.ext.SysUserExt;
import com.smaller.jiview.moduler.system.pojo.param.*;

/**
 * SysUserService
 *
 * @author xiagf
 * @date 2019/05/09
 */
public interface SysUserService {

    /**
     * 查询用户列表
     *
     * @param sysUserListParam
     * @return ResultBO<SysUserExt>
     */
    ResultBO<SysUserExt> list(SysUserListParam sysUserListParam);

    /**
     * 获取用户信息
     *
     * @param userId
     * @return ResultBO<AdminUserExt>
     */
    ResultBO get(Long userId);

    /**
     * 获取用户菜单部件权限信息
     *
     * @param menuId
     * @param loginUserDTO
     * @return
     */
    ResultBO<SysRoleMenuPartExt> getUserMenuPartAuth(Long menuId, LoginUserDTO loginUserDTO);

    /**
     * 保存用户信息
     *
     * @param sysUserSaveOrUpdateParam
     * @return ResultBO
     */
    ResultBO saveOrUpdateUser(SysUserSaveOrUpdateParam sysUserSaveOrUpdateParam);

    /**
     * 批量删除用户
     *
     * @param sysUserRemoveParam
     * @return ResultBO
     */
    ResultBO remove(SysUserRemoveParam sysUserRemoveParam);

    /**
     * 配置用户菜单权限
     *
     * @param sysUserMenuAuthParam
     * @return
     */
    ResultBO updateUserMenuAuth(SysUserMenuAuthParam sysUserMenuAuthParam);

    /**
     * 配置用户菜单部件权限
     *
     * @param sysUserMenuPartSaveParam
     * @return
     */
    ResultBO updateUserMenuPartAuth(SysUserMenuPartSaveParam sysUserMenuPartSaveParam);

    /**
     * 查询用户设置的菜单部件权限
     *
     * @param menuId
     * @param userId
     * @return
     */
    ResultBO listUserMenuPart(Long menuId, Long userId);

    /**
     * 根据userId查询用户的菜单权限
     * @param userId
     * @return
     */
    ResultBO listUserMenuMap(Long userId);
}
