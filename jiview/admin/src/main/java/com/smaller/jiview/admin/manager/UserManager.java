package com.smaller.jiview.admin.manager;


import com.smaller.jiview.admin.platform.system.model.SysUser;

/**
 * Created by xiagf on 2019/03/01
 */
public interface UserManager {
    /**
     * 获取Admin登录用SysUser
     *
     * @param userLogin
     * @param userPwd
     * @return SysUser
     */
    SysUser getForAdminLogin(String userLogin, String userPwd);

    SysUser getByUserLogin(String userLogin);

}
