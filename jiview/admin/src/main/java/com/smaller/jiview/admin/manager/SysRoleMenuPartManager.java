package com.smaller.jiview.admin.manager;

import com.smaller.jiview.admin.platform.system.model.SysRoleMenuPart;

import java.util.List;

/**
 * Created by xiagf on 2019-05-09
 */
public interface SysRoleMenuPartManager {

    void remove(Long roleId, List<Long> menuIds);

    void save(List<SysRoleMenuPart> list, Long roleId, Long userId);
}
