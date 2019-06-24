package com.smaller.jiview.moduler.system.service.impl;

import com.smaller.jiview.moduler.system.manager.PagerHelpManager;
import com.smaller.jiview.moduler.system.manager.SysDeptManager;
import com.smaller.jiview.moduler.system.platform.system.mapper.SysDeptMapper;
import com.smaller.jiview.moduler.system.platform.system.model.SysDept;
import com.smaller.jiview.moduler.system.pojo.model.ext.SysDeptExt;
import com.smaller.jiview.moduler.system.pojo.param.SysDeptListParam;
import com.smaller.jiview.moduler.system.pojo.param.SysDeptRemoveParam;
import com.smaller.jiview.moduler.system.pojo.param.SysDeptSaveOrUpdateParam;
import com.smaller.jiview.moduler.system.service.SysDeptService;
import com.smaller.jiview.core.pojo.bo.ResultBO;
import com.smaller.jiview.core.pojo.dto.LoginUserDTO;
import com.smaller.jiview.core.util.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xigf 2019/05/23
 */
@Service
public class SysDeptServiceImpl implements SysDeptService {

    @Autowired
    private SysDeptMapper sysDeptMapper;

    @Autowired
    private PagerHelpManager pagerHelpManager;

    @Autowired
    private SysDeptManager sysDeptManager;

    @Override
    public ResultBO<SysDeptExt> list(SysDeptListParam sysDeptListParam) {
        List<Long> subDeptIds = new ArrayList<>();
        String deptName;
        // 查询部门下所有子部门的角色
        if (!ObjectUtils.isEmpty(sysDeptListParam.getDeptId())) {
            // 获取子部门角色
            subDeptIds.add(sysDeptListParam.getDeptId());
            subDeptIds = sysDeptManager.listDeptIds(subDeptIds, sysDeptListParam.getDeptId());
        }
        pagerHelpManager.setStartPage(sysDeptListParam.getPageNo(), sysDeptListParam.getPageSize());
        deptName = sysDeptListParam.getDeptName();
        if (!ObjectUtils.isEmpty(deptName)){
            deptName = deptName.trim();
        }
        List<SysDeptExt> deptList = sysDeptMapper.listSysDept(deptName,subDeptIds);
        ResultBO<SysDeptExt> result = new ResultBO<>(deptList);
        return result;

    }

    @Override
    public ResultBO<SysDept> get(Long deptId) {
        ResultBO<SysDept> result = new ResultBO<>();
        SysDept sysDept = new SysDept();
        sysDept.setDeptId(deptId);
        result.setRow(sysDeptMapper.selectByPrimaryKey(sysDept));
        return result;
    }

    @Override
    public ResultBO remove(SysDeptRemoveParam sysDeptRemoveParam) {
        ResultBO result = new ResultBO();

        // 循环删除指定角色
        for (Long deptId : sysDeptRemoveParam.getDeptIdList()) {
            SysDept sysDept = new SysDept();
            sysDept.setDeptId(deptId);
            sysDeptMapper.deleteByPrimaryKey(sysDept);
        }
        return result;
    }

    @Override
    public ResultBO saveOrUpdateSysDept(SysDeptSaveOrUpdateParam sysDeptSaveOrUpdateParam) {
        ResultBO<SysDept> result = new ResultBO<>();
        LoginUserDTO loginUserDTO = sysDeptSaveOrUpdateParam.getLoginUserDTO();

        SysDept sysDept = new SysDept();
        BeanUtil.springCopy(sysDeptSaveOrUpdateParam, sysDept);

        if (sysDeptSaveOrUpdateParam.getDeptId() != null) {
            sysDeptMapper.updateByPrimaryKeySelective(sysDept);
        } else {
            sysDept.setCreateUserId(loginUserDTO.getLoginUserPkid());
            sysDeptMapper.insertSelective(sysDept);
        }
        result.setRow(sysDept);
        return result;
    }

    @Override
    public ResultBO listDept() {
        ResultBO<SysDept> result = new ResultBO<>();
        result.setRows(sysDeptMapper.listDept());
        return result;
    }
}
