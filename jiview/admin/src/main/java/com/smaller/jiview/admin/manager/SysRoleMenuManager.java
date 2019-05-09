package com.smaller.jiview.admin.manager;

import com.smaller.jiview.admin.platform.system.model.SysRoleMenu;
import com.smaller.jiview.core.pojo.dto.LoginUserDTO;

/**
 * Created by xiagf on 2019-05-09
 */
public interface SysRoleMenuManager {
    Integer save(SysRoleMenu sysRoleMenu, LoginUserDTO loginUserDTO);
}
