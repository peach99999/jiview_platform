package com.smaller.jiview.admin.service;


import com.smaller.jiview.admin.pojo.model.ext.SysRoleMenuPartExt;
import com.smaller.jiview.admin.pojo.model.ext.SysUserExt;
import com.smaller.jiview.admin.pojo.param.SysUserListParam;
import com.smaller.jiview.admin.pojo.param.SysUserRemoveParam;
import com.smaller.jiview.core.pojo.bo.ResultBO;
import com.smaller.jiview.core.pojo.dto.LoginUserDTO;

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
     * @return ResultBo<SysUserExt>
     */
    ResultBO<SysUserExt> list(SysUserListParam sysUserListParam);

    /**
     * 获取用户信息
     *
     * @param userId
     * @return ResultBo<AdminUserExt>
     */
    ResultBO get(Long userId);

    ResultBO<SysRoleMenuPartExt> getUserMenuPartAuth(Long menuId, LoginUserDTO loginUserDTO);

//    /**
//     * 保存用户信息
//     *
//     * @param adminUserSaveOrUpdateUserParam
//     * @return ResultBo
//     */
//    ResultBO saveOrUpdateUser(AdminUserSaveOrUpdateUserParam adminUserSaveOrUpdateUserParam);
//
    /**
     * 批量删除用户
     *
     * @param sysUserRemoveParam
     * @return ResultBo
     */
    ResultBO remove(SysUserRemoveParam sysUserRemoveParam);


}
