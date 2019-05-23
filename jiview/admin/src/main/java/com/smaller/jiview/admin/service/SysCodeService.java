package com.smaller.jiview.admin.service;

import com.smaller.jiview.admin.platform.system.model.SysCode;
import com.smaller.jiview.admin.pojo.param.SysCodeListParam;
import com.smaller.jiview.core.pojo.bo.ResultBO;

/**
 * @author xiagf on 2019/05/13
 */
public interface SysCodeService {
    /**
     * 获取code列表
     *
     * @param sysCodeListParam
     * @return ResultBO<SysCode>
     */
    ResultBO<SysCode> list(SysCodeListParam sysCodeListParam);

    /**
     * 获取单条code
     *
     * @param codeType
     * @param codeKey
     * @return ResultBO<SysCode>
     */
    ResultBO<SysCode> getByTypeAndKey(String codeType, String codeKey);

}
