package com.smaller.jiview.admin.manager.impl;

import com.smaller.jiview.admin.manager.SysRoleMenuPartManager;
import com.smaller.jiview.admin.platform.system.mapper.SysRoleMenuPartMapper;
import com.smaller.jiview.admin.platform.system.model.SysRoleMenuPart;
import com.smaller.jiview.core.util.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xigf 2019/05/23
 */
@Service
public class SysRoleMenuPartManagerImpl implements SysRoleMenuPartManager {

    @Autowired
    private SysRoleMenuPartMapper sysRoleMenuPartMapper;

    @Override
    public void remove(Long roleId, List<Long> menuIds) {
        sysRoleMenuPartMapper.remove(roleId, menuIds);
    }

    @Override
    public void save(List<SysRoleMenuPart> list, Long roleId, Long userId) {
        list.forEach(sysRoleMenuPart -> {
            SysRoleMenuPart saveParam = new SysRoleMenuPart();
            BeanUtil.springCopy(sysRoleMenuPart,saveParam);
            saveParam.setRoleId(roleId);
            saveParam.setCreateUserId(userId);
            sysRoleMenuPartMapper.insertSelective(saveParam);
        });
    }
}
