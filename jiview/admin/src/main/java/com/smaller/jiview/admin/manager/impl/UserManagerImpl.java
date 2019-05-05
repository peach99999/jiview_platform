package com.smaller.jiview.admin.manager.impl;

import com.smaller.jiview.admin.manager.UserManager;
import com.smaller.jiview.core.dao.mapper.SysUserMapper;
import com.smaller.jiview.core.exception.BizException;
import com.smaller.jiview.core.message.AdminMessage;
import com.smaller.jiview.core.pojo.model.SysUser;
import com.smaller.jiview.core.pojo.model.SysUserCriteria;
import com.smaller.jiview.core.util.CommonUtil;
import com.smaller.jiview.core.util.SecurityUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xiagf on 2019/03/01
 */
@Service
public class UserManagerImpl implements UserManager {
    @Autowired
    private SysUserMapper SysUserMapper;

    @Override
    public SysUser getForAdminLogin(String userLogin, String userPwd) {
        if (StringUtils.isEmpty(userLogin) || StringUtils.isEmpty(userPwd)) {
            throw new BizException(AdminMessage.LOGIN_FAILED);
        }

        SysUserCriteria SysUserCriteria = new SysUserCriteria();
        SysUserCriteria.Criteria criteria = SysUserCriteria.createCriteria()
                .andAccountEqualTo(userLogin)
                .andPasswordEqualTo(SecurityUtil.encodePwd(userPwd));

        List<SysUser> SysUsers = SysUserMapper.selectByExample(SysUserCriteria);
        if (SysUsers.isEmpty()) {
            throw new BizException(AdminMessage.LOGIN_FAILED);
        }
        return CommonUtil.getFirstElement(SysUsers);
    }


    @Override
    public SysUser getByUserLogin(String userLogin) {
        SysUserCriteria orgStaffCriteria = new SysUserCriteria();
        orgStaffCriteria.createCriteria()
                .andAccountEqualTo(userLogin);
        return CommonUtil.getFirstElement(SysUserMapper.selectByExample(orgStaffCriteria));
    }
}
