package com.smaller.jiview.admin.platform.system.mapper;

import com.smaller.jiview.admin.platform.system.model.SysDept;
import com.smaller.jiview.admin.pojo.param.SysDeptListParam;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

public interface SysDeptMapper extends Mapper<SysDept>, MySqlMapper<SysDept> {
    List<SysDept> list(SysDeptListParam sysDeptListParam);

    List<SysDept> listDept();

    Long count(SysDeptListParam sysDeptListParam);
}