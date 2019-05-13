package com.smaller.jiview.admin.manager;

import com.smaller.jiview.admin.platform.system.model.SysUserRole;
import com.smaller.jiview.core.pojo.dto.LoginUserDTO;

import java.util.List;

/**
 * Created by xiagf on 2019-05-09
 */
public interface SysUserRoleManager {
    List<SysUserRole> getByUserPkid(Long loginUserPkid);

    void remove(Long userPkid);

    void updateUserRoleInfo(Long userId, List<Long> roleIdList, LoginUserDTO loginUserDTO);
}
