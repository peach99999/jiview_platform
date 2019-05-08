package com.smaller.jiview.admin.service;


import com.smaller.jiview.admin.pojo.model.ext.SysUserExt;
import com.smaller.jiview.admin.pojo.param.SysUserListParam;
import com.smaller.jiview.core.pojo.bo.ResultBO;

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
     * @param pkid
     * @return ResultBo<AdminUserExt>
     */
    ResultBO get(Long pkid);

//    /**
//     * 保存用户信息
//     *
//     * @param adminUserSaveOrUpdateUserParam
//     * @return ResultBo
//     */
//    ResultBO saveOrUpdateUser(AdminUserSaveOrUpdateUserParam adminUserSaveOrUpdateUserParam);
//
//    /**
//     * 批量删除用户
//     *
//     * @param adminUserRemoveParam
//     * @return ResultBo
//     */
//    ResultBO remove(AdminUserRemoveParam adminUserRemoveParam);


}
