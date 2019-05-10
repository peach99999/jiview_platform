package com.smaller.jiview.admin.manager;

import com.smaller.jiview.core.pojo.dto.LoginUserDTO;

import java.util.List;

/**
 * Created by xiagf on 2019-05-09
 */
public interface SysRoleMenuManager {
    Integer save(Long roleId, long menuId, LoginUserDTO loginUserDTO);

    List<Long> list(Long roleId);

    void remove(Long roleId, List<Long> menuIds);
}
