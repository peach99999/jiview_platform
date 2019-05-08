package com.smaller.jiview.admin.manager.impl;

import com.github.pagehelper.PageHelper;
import com.smaller.jiview.admin.manager.PagerHelpManager;
import com.smaller.jiview.admin.manager.UserManager;
import com.smaller.jiview.admin.platform.system.mapper.SysUserMapper;
import com.smaller.jiview.admin.platform.system.model.SysUser;
import com.smaller.jiview.core.constant.Constants;
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
public class PagerHelpManagerImpl implements PagerHelpManager {


    @Override
    public void setStartPage(Integer pageNo, Integer pageSize) {
        if (pageNo != null && pageSize != null) {
            PageHelper.startPage(pageNo, pageSize);
        } else {
            PageHelper.startPage(Constants.PAGE_NO, Constants.PAGE_SIZE);
        }
    }
}
