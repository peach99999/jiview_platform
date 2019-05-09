package com.smaller.jiview.admin.manager;

import com.smaller.jiview.admin.platform.system.model.SysUserRole;

/**
 * Created by xiagf on 2019-05-09
 */
public interface SysUserRoleManager {
    SysUserRole getByUserPkid(Long loginUserPkid);

    Integer remove(Long userPkid);
}
