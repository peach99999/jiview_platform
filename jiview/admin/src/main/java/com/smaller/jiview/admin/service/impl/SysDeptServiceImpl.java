package com.smaller.jiview.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.smaller.jiview.admin.manager.PagerHelpManager;
import com.smaller.jiview.admin.platform.system.mapper.SysDeptMapper;
import com.smaller.jiview.admin.platform.system.model.SysDept;
import com.smaller.jiview.admin.pojo.param.SysDeptListParam;
import com.smaller.jiview.admin.pojo.param.SysDeptRemoveParam;
import com.smaller.jiview.admin.pojo.param.SysDeptSaveOrUpdateParam;
import com.smaller.jiview.admin.service.SysDeptService;
import com.smaller.jiview.core.pojo.bo.ResultBO;
import com.smaller.jiview.core.pojo.dto.LoginUserDTO;
import com.smaller.jiview.core.util.BeanUtil;
import com.smaller.jiview.core.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysDeptServiceImpl implements SysDeptService {

    @Autowired
    private SysDeptMapper sysDeptMapper;

    @Autowired
    private PagerHelpManager pagerHelpManager;

    @Override
    public ResultBO<SysDept> list(SysDeptListParam sysDeptListParam) {
        pagerHelpManager.setStartPage(sysDeptListParam.getPageNo(), sysDeptListParam.getPageSize());
        List<SysDept> deptList = sysDeptMapper.listSysUser(sysDeptListParam);
        ResultBO<SysDept> result = new ResultBO<>(deptList);
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
