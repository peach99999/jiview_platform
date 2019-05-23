package com.smaller.jiview.admin.platform.system.mapper;

import com.smaller.jiview.admin.platform.system.model.SysDept;
import com.smaller.jiview.admin.pojo.model.ext.SysDeptExt;
import com.smaller.jiview.admin.pojo.param.SysDeptListParam;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

/**
 * @author xigf 2019/05/23
 */
public interface SysDeptMapper extends Mapper<SysDept>, MySqlMapper<SysDept> {
    /**
     * 查询部门信息
     *
     * @param sysDeptListParam
     * @return
     */
    List<SysDeptExt> listSysDept(SysDeptListParam sysDeptListParam);

    /**
     * 查询部门信息
     *
     * @return
     */
    List<SysDept> listDept();

}