package com.smaller.jiview.admin.manager.impl;

import com.smaller.jiview.admin.manager.SysDeptManager;
import com.smaller.jiview.admin.platform.system.mapper.SysDeptMapper;
import com.smaller.jiview.admin.platform.system.model.SysDept;
import com.smaller.jiview.admin.pojo.model.ext.SysDeptExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
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
            if (sysDeptExt.getParentId() != null && sysDeptExt.getParentId() != 0) {
                SysDept sysDept = sysDeptMapper.selectByPrimaryKey(sysDeptExt.getParentId());
                if (sysDept != null) {
                    sysDeptExt.setParentDeptName(sysDept.getDeptName());
                }
            }
        }
        return list;
    }

    @Override
    public List<Long> listDeptIds(List<Long> deptIds,Long parentId) {
        deptIds = getSubDeptId(deptIds, parentId);
        return deptIds;
    }

    private List<Long> getSubDeptId(List<Long> list, Long parentId) {
        Example example = new Example(SysDept.class);
        example.createCriteria().andEqualTo("parentId",parentId);
        List<SysDept> subList = sysDeptMapper.selectByExample(example);
        if (!ObjectUtils.isEmpty(subList)){
            subList.forEach(sysDept -> {
                getSubDeptId(list,sysDept.getDeptId());
                list.add(sysDept.getDeptId());
            });
        }
        return list;
    }
}
