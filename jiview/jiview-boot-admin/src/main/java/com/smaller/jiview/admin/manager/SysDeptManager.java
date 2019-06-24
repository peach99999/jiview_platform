package com.smaller.jiview.admin.manager;


import com.smaller.jiview.admin.pojo.model.ext.SysDeptExt;

import java.util.List;

/**
 * @author xiagf on 2019/05/09
 */
public interface SysDeptManager {

    /**
     * 查询部门名称
     *
     * @param list
     * @return
     */
    List<SysDeptExt> listDeptParentName(List<SysDeptExt> list);

    /**
     * 获取部门下所有子部门id
     *
     * @param parentId
     * @param subDeptIds
     * @return
     */
    List<Long> listDeptIds(List<Long> subDeptIds, Long parentId);

}
