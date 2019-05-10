package com.smaller.jiview.admin.manager;

import com.smaller.jiview.admin.pojo.model.ext.SysRoleExt;

import java.util.List;

/**
 * Created by xiagf on 2019/05/10.
 */
public interface SysRoleManager {

    SysRoleExt get(Long roleId);

    Integer remove(List<Long> roleIds);
}
