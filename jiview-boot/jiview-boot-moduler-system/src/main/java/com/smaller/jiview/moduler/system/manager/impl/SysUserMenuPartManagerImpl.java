package com.smaller.jiview.moduler.system.manager.impl;

import com.smaller.jiview.moduler.system.manager.SysUserMenuPartManager;
import com.smaller.jiview.moduler.system.platform.system.mapper.SysUserMenuPartMapper;
import com.smaller.jiview.moduler.system.platform.system.model.SysUserMenuPart;
import com.smaller.jiview.moduler.system.pojo.param.SysRoleMenuPartAuthParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xigf 2019/06/10
 */
@Service
public class SysUserMenuPartManagerImpl implements SysUserMenuPartManager {

    @Autowired
    private SysUserMenuPartMapper sysUserMenuPartMapper;

    @Override
    public void remove(Long userId, List<Long> menuIds) {
        sysUserMenuPartMapper.remove(userId, menuIds);
    }

    @Override
    public void save(List<SysRoleMenuPartAuthParam> list, Long userId, Long menuId, Long creatUserId) {
        list.forEach(sysRoleMenuPartAuthParam -> {
            SysUserMenuPart sysUserMenuPart = new SysUserMenuPart();
            sysUserMenuPart.setUserId(userId);
            sysUserMenuPart.setMenuId(menuId);
            sysUserMenuPart.setPartId(sysRoleMenuPartAuthParam.getPartId());
            sysUserMenuPart.setPartAuthType(sysRoleMenuPartAuthParam.getPartAuthType());
            sysUserMenuPart.setCreateUserId(creatUserId);
            sysUserMenuPartMapper.insertSelective(sysUserMenuPart);
        });
    }

}
