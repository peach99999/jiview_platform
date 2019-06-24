package com.smaller.jiview.moduler.system.manager.impl;

import com.smaller.jiview.moduler.system.manager.SysCodeManager;
import com.smaller.jiview.moduler.system.platform.system.mapper.SysCodeMapper;
import com.smaller.jiview.moduler.system.platform.system.model.SysCode;
import com.smaller.jiview.moduler.system.pojo.param.SysCodeListParam;
import com.smaller.jiview.core.pojo.bo.ResultBO;
import com.smaller.jiview.core.util.CommonUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author xiagf on 2019/05/13
 */
@Service
public class SysCodeManagerImpl implements SysCodeManager {
    @Autowired
    private SysCodeMapper sysCodeMapper;


    @Override
    public ResultBO<SysCode> list(SysCodeListParam sysCodeListParam) {
        ResultBO<SysCode> result = new ResultBO<>();
        String codeType = sysCodeListParam.getCodeType();
        Example example = new Example(SysCode.class);

        if (StringUtils.isNotEmpty(codeType)) {
            example.createCriteria().andEqualTo("codeType", codeType);
            result.setRows(sysCodeMapper.selectByExample(example));
        }

        return result;
    }

    @Override
    public SysCode getByTypeAndKey(String codeType, String codeKey) {
        if (StringUtils.isEmpty(codeType)) {
            return new SysCode();
        }

        if (StringUtils.isEmpty(codeKey)) {
            return new SysCode();
        }

        Example example = new Example(SysCode.class);
        example.createCriteria().andEqualTo("codeType", codeType)
                .andEqualTo("codeKey", codeKey);
        List<SysCode> sysCodes = sysCodeMapper.selectByExample(example);
        return CommonUtil.getFirstElement(sysCodes);
    }

}
