package com.smaller.jiview.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.smaller.jiview.admin.manager.PagerHelpManager;
import com.smaller.jiview.admin.manager.SysRoleManager;
import com.smaller.jiview.admin.platform.system.mapper.SysRoleMapper;
import com.smaller.jiview.admin.platform.system.model.SysRole;
import com.smaller.jiview.admin.pojo.model.ext.SysRoleExt;
import com.smaller.jiview.admin.pojo.param.SysRoleListParam;
import com.smaller.jiview.admin.pojo.param.SysRoleSaveOrUpdateParam;
import com.smaller.jiview.admin.service.SysRoleService;
import com.smaller.jiview.core.pojo.bo.ResultBO;
import com.smaller.jiview.core.util.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    private SysRoleManager sysRoleManager;
//    @Autowired
//    private AdminMenuAuthManager adminMenuAuthManager;
//    @Autowired
//    private AdminRoleQuery adminRoleQuery;
    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private PagerHelpManager pagerHelpManager;

    @Override
    public ResultBO<SysRoleExt> list(SysRoleListParam sysRoleListParam) {
        pagerHelpManager.setStartPage(sysRoleListParam.getPageNo(), sysRoleListParam.getPageSize());
        List<SysRoleExt> sysRoleExts = sysRoleMapper.list(sysRoleListParam);
        ResultBO<SysRoleExt>  result = new ResultBO<>(sysRoleExts);
        return result;
    }

    @Override
    public ResultBO<SysRoleExt> get(Long roleId) {
        ResultBO<SysRoleExt> result = new ResultBO();
        result.setRow(sysRoleManager.get(roleId));
        return result;
    }

    @Override
    public ResultBO saveOrUpdateRole(SysRoleSaveOrUpdateParam sysRoleSaveOrUpdateParam) {
        ResultBO resultBo = new ResultBO<>();
        SysRole sysRole = new SysRole();

        // 拷贝角色数据
        BeanUtil.springCopy(sysRoleSaveOrUpdateParam, sysRole);

        // 角色id不为null, 则更新, 否则新增
        if (sysRoleSaveOrUpdateParam.getRoleId() != null) {
            resultBo.setOpResult(sysRoleMapper.updateByPrimaryKeySelective(sysRole));
        } else {
            sysRole.setCreateUserId(sysRoleSaveOrUpdateParam.getLoginUserDTO().getLoginUserPkid());
            resultBo.setOpResult(sysRoleMapper.insertSelective(sysRole));
        }
        resultBo.setRow(sysRole);

        return resultBo;
    }
//
//    @Override
//    public ResultBo remove(AdminRoleRemoveParam adminRoleRemoveParam) {
//        ResultBo resultBo = new ResultBo<>();
//        int count = 0;
//
//        // 循环删除指定角色
//        for (Long pkid : adminRoleRemoveParam.getPkidList()) {
//            AdminRole adminRole = new AdminRole();
//
//            // 删除指定角色
//            adminRole.setPkid(pkid);
//            adminRole.setVer(Constants.VER_MINUS_1 * pkid);
//            adminRoleMapper.updateByPrimaryKeySelective(adminRole);
//            count++;
//        }
//        resultBo.setOpResult(count);
//
//        return resultBo;
//    }
//
//    @Override
//    public ResultBo updateRoleMenuAuthorization(AdminRoleUpdateRoleMenuAuthorizationParam adminRoleUpdateRoleMenuAuthorizationParam) {
//        Long pkid = adminRoleUpdateRoleMenuAuthorizationParam.getPkid();
//        List<Long> newMenuPkids = adminRoleUpdateRoleMenuAuthorizationParam.getMenuPkids();
//        List<Long> oldMenuPkids = adminMenuAuthManager.list(pkid);
//
//        DiffDto diff = CommonUtil.diff(newMenuPkids, oldMenuPkids);
//        List<Long> menuPkidsForSave = diff.getAdded();
//        List<Long> menuPkidsForRemove = diff.getDeleted();
//
//        menuPkidsForSave.forEach((menuPkid) -> {
//            adminMenuAuthManager.save(pkid, menuPkid);
//        });
//
//        if (menuPkidsForRemove.size() > 0) {
//            adminMenuAuthManager.remove(pkid, menuPkidsForRemove);
//        }
//
//        ResultBo result = new ResultBo();
//        result.setOpResult(Constants.OP_RESULT_SUCCESS);
//        return result;
//    }
}
