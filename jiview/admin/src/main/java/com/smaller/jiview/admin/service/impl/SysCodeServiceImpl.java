package com.smaller.jiview.admin.service.impl;

import com.smaller.jiview.admin.manager.SysCodeManager;
import com.smaller.jiview.admin.platform.system.model.SysCode;
import com.smaller.jiview.admin.pojo.param.SysCodeListParam;
import com.smaller.jiview.admin.service.SysCodeService;
import com.smaller.jiview.core.pojo.bo.ResultBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotEmpty;

/**
 * @author xiagf on 2019/05/13
 */
@Service
public class SysCodeServiceImpl implements SysCodeService {
    @Autowired
    private SysCodeManager sysCodeManager;

    @Override
    public ResultBO<SysCode> list(SysCodeListParam sysCodeListParam) {

        return sysCodeManager.list(sysCodeListParam);
    }

    @Override
    public ResultBO<SysCode> getByTypeAndKey(@NotEmpty String codeType, @NotEmpty String codeKey) {
        ResultBO<SysCode> result = new ResultBO<>();
        result.setRow(sysCodeManager.getByTypeAndKey(codeType, codeKey));
        return result;
    }

}
