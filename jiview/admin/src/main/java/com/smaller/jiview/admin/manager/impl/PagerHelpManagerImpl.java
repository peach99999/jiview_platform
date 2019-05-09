package com.smaller.jiview.admin.manager.impl;

import com.github.pagehelper.PageHelper;
import com.smaller.jiview.admin.manager.PagerHelpManager;
import com.smaller.jiview.core.constant.Constants;
import org.springframework.stereotype.Service;


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
