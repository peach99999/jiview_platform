package com.smaller.jiview.admin.manager.impl;

import com.smaller.jiview.admin.manager.SysDeptManager;
import com.smaller.jiview.admin.platform.system.mapper.SysDeptMapper;
import com.smaller.jiview.admin.platform.system.model.SysDept;
import com.smaller.jiview.admin.pojo.model.ext.SysDeptExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Created by xiagf on 2019/03/01
 */
@Service
public class SysDeptManagerImpl implements SysDeptManager {
    @Autowired
    private SysDeptMapper sysDeptMapper;

    @Override
    public List<SysDeptExt> listDeptParentName(List<SysDeptExt> list) {
        for (SysDeptExt sysDeptExt : list) {
            if (sysDeptExt.getParentId() != 0) {
                SysDept sysDept = sysDeptMapper.selectByPrimaryKey(sysDeptExt.getParentId());
                if (sysDept != null) {
                    sysDeptExt.setParentDeptName(sysDept.getDeptName());
                }
            }
        }
        return list;
    }
}
