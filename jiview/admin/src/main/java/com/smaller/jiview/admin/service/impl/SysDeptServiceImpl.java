package com.smaller.jiview.admin.service.impl;

import com.smaller.jiview.admin.dao.query.SysDeptQuery;
import com.smaller.jiview.admin.pojo.param.SysDeptListParam;
import com.smaller.jiview.admin.service.SysDeptService;
import com.smaller.jiview.core.pojo.bo.ResultBO;
import com.smaller.jiview.core.pojo.model.SysDept;
import com.smaller.jiview.core.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysDeptServiceImpl implements SysDeptService {

    @Autowired
    private SysDeptQuery sysDeptQuery;

    @Override
    public ResultBO<SysDept> list(SysDeptListParam sysDeptListParam) {
        ResultBO<SysDept> result = new ResultBO<>();

        // 设置分页参数
        sysDeptListParam.setStartRow(CommonUtil.calcStartRow(sysDeptListParam));
        // 查询部门数量
        result.setCount(sysDeptQuery.count(sysDeptListParam));
        if (result.getCount() > 0) {
            // 查询部门列表
            result.setRows(sysDeptQuery.list(sysDeptListParam));
        }
        return result;
    }
}
