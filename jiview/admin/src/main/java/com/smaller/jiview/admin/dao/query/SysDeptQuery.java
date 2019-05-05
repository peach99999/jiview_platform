package com.smaller.jiview.admin.dao.query;


import com.smaller.jiview.admin.pojo.param.SysDeptListParam;
import com.smaller.jiview.core.pojo.model.SysDept;

import java.util.List;

/**
 * SceneGuideQuery
 *
 * @author xiagf
 * @date 2019-04-016
 */
public interface SysDeptQuery {

    List<SysDept> list(SysDeptListParam sysDeptListParam);

    Long count(SysDeptListParam sysDeptListParam);
}
