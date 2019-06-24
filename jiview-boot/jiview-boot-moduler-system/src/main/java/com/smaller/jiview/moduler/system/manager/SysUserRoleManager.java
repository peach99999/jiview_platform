package com.smaller.jiview.moduler.system.manager;

import com.smaller.jiview.moduler.system.platform.system.model.SysUserRole;
import com.smaller.jiview.core.pojo.dto.LoginUserDTO;

import java.util.List;

/**
 * @author xiagf on 2019-05-09
 */
public interface SysUserRoleManager {
    /**
     * 通过用户id获取角色信息
     *
     * @param loginUserId
     * @return
     */
    List<SysUserRole> getByUserPkid(Long loginUserId);

    /**
     * 删除用户角色信息
     *
     * @param userId
     */
    void remove(Long userId);

    /**
     * 保存用户角色信息
     *
     * @param userId
     * @param roleIdList
     * @param loginUserDTO
     */
    void updateUserRoleInfo(Long userId, List<Long> roleIdList, LoginUserDTO loginUserDTO);
}
