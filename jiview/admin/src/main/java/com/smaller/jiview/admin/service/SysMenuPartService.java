package com.smaller.jiview.admin.service;


import com.smaller.jiview.admin.pojo.param.SysMenuPartSaveOrupdateParam;
import com.smaller.jiview.core.pojo.bo.ResultBO;

/**
 * 部件管理Service
 *
 * @author xiagf
 * @date 2019/05/09
 */
public interface SysMenuPartService {

    ResultBO menuPartSaveOrupdate(SysMenuPartSaveOrupdateParam sysMenuPartSaveOrupdateParam);

    ResultBO get(Long menuId);
}
