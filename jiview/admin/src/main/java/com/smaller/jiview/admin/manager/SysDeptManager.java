package com.smaller.jiview.admin.manager;


import com.smaller.jiview.admin.pojo.model.ext.SysDeptExt;

import java.util.List;

/**
 * Created by xiagf on 2019/05/09
 */
public interface SysDeptManager {


    List<SysDeptExt> listDeptParentName(List<SysDeptExt> list);

}
