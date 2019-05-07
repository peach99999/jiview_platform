package com.smaller.jiview.admin.manager.impl;

import com.smaller.jiview.admin.manager.UserManager;
import com.smaller.jiview.admin.platform.system.mapper.SysUserMapper;
import com.smaller.jiview.admin.platform.system.model.SysUser;
import com.smaller.jiview.core.exception.BizException;
import com.smaller.jiview.core.message.AdminMessage;
import com.smaller.jiview.core.util.CommonUtil;
import com.smaller.jiview.core.util.SecurityUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;


/**
 * Created by xiagf on 2019/03/01
 */
@Service
public class UserManagerImpl implements UserManager {
    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public SysUser getForAdminLogin(String userLogin, String userPwd) {
        if (StringUtils.isEmpty(userLogin) || StringUtils.isEmpty(userPwd)) {
            throw new BizException(AdminMessage.LOGIN_FAILED);
        }

        Example example = new Example(SysUser.class);
        example.createCriteria()
                .andEqualTo("account", userLogin)
                .andEqualTo("password",SecurityUtil.encodePwd(userPwd));

        List<SysUser> SysUsers = sysUserMapper.selectByExample(example);
        if (SysUsers.isEmpty()) {
            throw new BizException(AdminMessage.LOGIN_FAILED);
        }
        return CommonUtil.getFirstElement(SysUsers);
    }


    @Override
    public SysUser getByUserLogin(String userLogin) {
        Example example = new Example(SysUser.class);
        example.createCriteria()
                .andEqualTo("account", userLogin);
        return CommonUtil.getFirstElement(sysUserMapper.selectByExample(example));
    }
}
