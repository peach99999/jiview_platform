package com.smaller.jiview.admin.manager;

import com.smaller.jiview.admin.platform.system.model.SysUserRole;

import java.util.List;

/**
 * Created by xiagf on 2019-05-09
 */
public interface SysUserRoleManager {
    List<SysUserRole> getByUserPkid(Long loginUserPkid);

    Integer remove(Long userPkid);
}
