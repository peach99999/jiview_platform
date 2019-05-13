package com.smaller.jiview.admin.manager;


import com.smaller.jiview.admin.platform.system.model.SysUser;
import com.smaller.jiview.core.pojo.dto.LoginUserDTO;

/**
 * Created by xiagf on 2019/05/09
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

    SysUser getByUserLogin(String userLogin);

    void save(SysUser sysUser, LoginUserDTO loginUserDTO);

    void update(SysUser sysUser, LoginUserDTO loginUserDTO);

    Integer countByUserLogin(String account);

}
