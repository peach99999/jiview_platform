package com.smaller.jiview.admin.manager;

import java.util.List;

/**
 * Created by xiagf on 2019-05-09
 */
public interface SysRoleMenuPartManager {

    void remove(Long roleId, List<Long> menuIds);
}
