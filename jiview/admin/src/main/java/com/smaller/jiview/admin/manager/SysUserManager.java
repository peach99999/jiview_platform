package com.smaller.jiview.admin.manager;


import com.smaller.jiview.admin.platform.system.model.SysUser;
import com.smaller.jiview.core.pojo.dto.LoginUserDTO;

/**
 * @author xiagf on 2019/05/09
 */
public interface SysUserManager {
    /**
     * 获取Admin登录用SysUser
     *
     * @param userLogin
     * @param userPwd
     * @return SysUser
     */
    SysUser getForAdminLogin(String userLogin, String userPwd);

    /**
     * 根据登录账号查询用户信息
     *
     * @param account
     * @return
     */
    SysUser getByUserLogin(String account);

    /**
     * 保存用户信息
     *
     * @param sysUser
     * @param loginUserDTO
     */
    void save(SysUser sysUser, LoginUserDTO loginUserDTO);

    /**
     * 删除用户信息
     *
     * @param sysUser
     * @param loginUserDTO
     */
    void update(SysUser sysUser, LoginUserDTO loginUserDTO);

    /**
     * 查询同名的账户数量
     *
     * @param account
     * @return
     */
    Integer countByUserLogin(String account);

}
