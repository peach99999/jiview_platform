package com.smaller.jiview.admin.manager.impl;

import com.smaller.jiview.admin.manager.SysRoleMenuPartManager;
import com.smaller.jiview.admin.platform.system.mapper.SysRoleMenuPartMapper;
import com.smaller.jiview.admin.platform.system.model.SysRoleMenuPart;
import com.smaller.jiview.admin.pojo.param.SysRoleMenuPartAuthParam;
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
    public void save(List<SysRoleMenuPartAuthParam> list, Long roleId, Long userId, Long menuId) {
        list.forEach(sysRoleMenuPartAuthParam -> {
            SysRoleMenuPart saveParam = new SysRoleMenuPart();
            BeanUtil.springCopy(sysRoleMenuPartAuthParam, saveParam);
            saveParam.setRoleId(roleId);
            saveParam.setMenuId(menuId);
            saveParam.setCreateUserId(userId);
            sysRoleMenuPartMapper.insertSelective(saveParam);
        });
    }
}
