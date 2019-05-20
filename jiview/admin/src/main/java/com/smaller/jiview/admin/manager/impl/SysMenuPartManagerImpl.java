package com.smaller.jiview.admin.manager.impl;

import com.smaller.jiview.admin.manager.SysMenuPartManager;
import com.smaller.jiview.admin.platform.system.mapper.SysMenuPartMapper;
import com.smaller.jiview.admin.platform.system.model.SysMenuPart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;


/**
 * Created by xiagf on 2019/03/01
 */
@Service
public class SysMenuPartManagerImpl implements SysMenuPartManager {

    @Autowired
    private SysMenuPartMapper sysMenuPartMapper;

    @Override
    public List<SysMenuPart> listMenuPart(Long menuId) {
        Example example = new Example(SysMenuPart.class);
        example.createCriteria().andEqualTo("menuId", menuId);
        return sysMenuPartMapper.selectByExample(example);
    }

    @Override
    public void remove(Long menuId) {
        Example example = new Example(SysMenuPart.class);
        example.createCriteria().andEqualTo("menuId", menuId);
        sysMenuPartMapper.deleteByExample(example);
    }
}
