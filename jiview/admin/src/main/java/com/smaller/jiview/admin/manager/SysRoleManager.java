package com.smaller.jiview.admin.manager;

import com.smaller.jiview.admin.pojo.model.ext.SysRoleExt;

import java.util.List;

/**
 * Created by xiagf on 2019/05/10.
 */
public interface SysRoleManager {

    /**
     * 获取用户信息
     *
     * @param roleId
     * @return
     */
    SysRoleExt get(Long roleId);

    /**
     * 删除角色信息
     *
     * @param roleIds
     * @return
     */
    Integer remove(List<Long> roleIds);
}
