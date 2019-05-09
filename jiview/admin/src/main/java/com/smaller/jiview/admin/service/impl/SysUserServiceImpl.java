package com.smaller.jiview.admin.service.impl;

import com.smaller.jiview.admin.manager.PagerHelpManager;
import com.smaller.jiview.admin.platform.system.mapper.SysUserMapper;
import com.smaller.jiview.admin.pojo.model.ext.SysUserExt;
import com.smaller.jiview.admin.pojo.param.SysUserListParam;
import com.smaller.jiview.admin.service.SysUserService;
import com.smaller.jiview.core.pojo.bo.ResultBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private PagerHelpManager pagerHelpManager;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public ResultBO<SysUserExt> list(SysUserListParam sysUserListParam) {
        pagerHelpManager.setStartPage(sysUserListParam.getPageNo(), sysUserListParam.getPageSize());
        List<SysUserExt> list = sysUserMapper.list(sysUserListParam);
        ResultBO<SysUserExt> result = new ResultBO<>(list);
        return result;
    }

    @Override
    public ResultBO get(Long pkid) {
        return null;
    }
}
