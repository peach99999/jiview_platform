package com.smaller.jiview.admin.manager;

import com.smaller.jiview.admin.platform.system.model.SysCode;
import com.smaller.jiview.admin.pojo.param.SysCodeListParam;
import com.smaller.jiview.core.pojo.bo.ResultBO;

/**
 * Created by xiagf on 2019/05/13
 */
public interface SysCodeManager {
    /**
     * 根据条件获取code列表
     *
     * @param sysCodeListParam
     * @return List<SysCode>
     */
    ResultBO<SysCode> list(SysCodeListParam sysCodeListParam);

    /**
     * 获取单条code
     *
     * @param codeType
     * @param codeKey
     * @return SysCode
     */
    SysCode getByTypeAndKey(String codeType, String codeKey);

}