package com.smaller.jiview.moduler.system.manager.impl;

import com.smaller.jiview.moduler.system.manager.SysDeptManager;
import com.smaller.jiview.moduler.system.platform.system.mapper.SysDeptMapper;
import com.smaller.jiview.moduler.system.platform.system.model.SysDept;
import com.smaller.jiview.moduler.system.pojo.model.ext.SysDeptExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;


/**
 * @author xiagf on 2019/03/01
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
        getSubDeptId(deptIds, parentId);
        return deptIds;
    }

    private List<Long> getSubDeptId(List<Long> list, Long parentId) {
        Example example = new Example(SysDept.class);
        example.createCriteria().andEqualTo("parentId",parentId);
        List<SysDept> subList = sysDeptMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(subList)){
            subList.forEach(sysDept -> {
                getSubDeptId(list,sysDept.getDeptId());
                list.add(sysDept.getDeptId());
            });
        }
        return list;
    }
}
