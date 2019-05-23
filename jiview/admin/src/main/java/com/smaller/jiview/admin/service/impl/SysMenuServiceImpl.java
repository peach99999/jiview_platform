package com.smaller.jiview.admin.service.impl;

import com.smaller.jiview.admin.manager.SysMenuManager;
import com.smaller.jiview.admin.manager.SysRoleMenuManager;
import com.smaller.jiview.admin.manager.SysUserRoleManager;
import com.smaller.jiview.admin.platform.system.model.SysMenu;
import com.smaller.jiview.admin.platform.system.model.SysUserRole;
import com.smaller.jiview.admin.pojo.model.ext.SysMenuExt;
import com.smaller.jiview.admin.pojo.param.MenuRemoveParam;
import com.smaller.jiview.admin.pojo.param.MenuSaveParam;
import com.smaller.jiview.admin.pojo.param.MenuUpdateParam;
import com.smaller.jiview.admin.service.SysMenuService;
import com.smaller.jiview.core.constant.Constants;
import com.smaller.jiview.core.exception.BizException;
import com.smaller.jiview.core.message.AdminMessage;
import com.smaller.jiview.core.pojo.bo.ResultBO;
import com.smaller.jiview.core.pojo.dto.LoginUserDTO;
import com.smaller.jiview.core.util.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xigf 2019/05/23
 */
@Service
public class SysMenuServiceImpl implements SysMenuService {

    @Autowired
    private SysMenuManager menuManager;

    @Autowired
    private SysUserRoleManager sysUserRoleManager;

    @Autowired
    private SysRoleMenuManager sysRoleMenuManager;


    @Override
    public ResultBO list() {
        ResultBO<SysMenuExt> result = new ResultBO();
        result.setRows(menuManager.list());
        return result;
    }

    @Override
    public ResultBO<SysMenuExt> listUserMenuTree(Long loginPkid) {
        ResultBO<SysMenuExt> result = new ResultBO<>();

        if (loginPkid == null) {
            throw new BizException(AdminMessage.LOGIN_FAILED);
        }

        result.setRows(menuManager.listUserMenuTree(loginPkid));

        return result;
    }

    @Override
    public ResultBO listMenuTree() {
        ResultBO result = new ResultBO();
        result.setRows(menuManager.listMenuTree());
        return result;
    }

    @Override
    public ResultBO<Integer> save(MenuSaveParam menuSaveParam) {
        ResultBO<Integer> result = new ResultBO();

        LoginUserDTO loginUserDTO = menuSaveParam.getLoginUserDTO();
        Long loginUserPkid = loginUserDTO.getLoginUserPkid();

        // 新增菜单数据
        SysMenu sysMenu = new SysMenu();
        BeanUtil.springCopy(menuSaveParam, sysMenu);
        Integer resultCode = menuManager.save(sysMenu, loginUserDTO);

        // 获取用户权限co
        List<SysUserRole> sysUserRoleList = sysUserRoleManager.getByUserPkid(loginUserPkid);

        // 新增菜单权限数据(给每个用户下面的角色增加该菜单访问权限)
        sysUserRoleList.forEach(sysUserRole ->
                sysRoleMenuManager.save(sysUserRole.getAuthorizeId(), sysMenu.getMenuId(), Constants.SYS_ROLE_MENU_AUTHORIZE_LEVEL_1, loginUserDTO));

        result.setOpResult(resultCode);

        return result;
    }

    @Override
    public ResultBO<Integer> update(MenuUpdateParam menuUpdateParam) {
        ResultBO<Integer> result = new ResultBO();

        LoginUserDTO loginUserDTO = menuUpdateParam.getLoginUserDTO();

        SysMenu sysMenu = new SysMenu();
        BeanUtil.springCopy(menuUpdateParam, sysMenu);

        result.setOpResult(menuManager.update(sysMenu, loginUserDTO));

        return result;
    }

    @Override
    public ResultBO<Integer> remove(MenuRemoveParam menuRemoveParam) {
        ResultBO<Integer> result = new ResultBO();
        result.setOpResult(menuManager.remove(menuRemoveParam));
        return result;
    }
}
