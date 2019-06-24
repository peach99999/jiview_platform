package com.smaller.jiview.admin.platform.system.mapper;

import com.smaller.jiview.admin.platform.system.model.SysDept;
import com.smaller.jiview.admin.pojo.model.ext.SysDeptExt;
import org.apache.ibatis.annotations.Param;
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
     * @param deptName
     * @param subDeptIds
     * @return
     */
    List<SysDeptExt> listSysDept(@Param("deptName") String deptName, @Param("subDeptIds") List<Long> subDeptIds);

    /**
     * 查询部门信息
     *
     * @return
     */
    List<SysDept> listDept();

}