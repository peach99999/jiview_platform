package com.smaller.jiview.admin.manager.impl;

import com.smaller.jiview.admin.manager.SysMenuManager;
import com.smaller.jiview.admin.platform.system.mapper.SysMenuMapper;
import com.smaller.jiview.admin.platform.system.model.SysMenu;
import com.smaller.jiview.admin.pojo.model.ext.SysMenuExt;
import com.smaller.jiview.admin.pojo.param.MenuRemoveParam;
import com.smaller.jiview.core.constant.Constants;
import com.smaller.jiview.core.pojo.dto.LoginUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by xiagf on 2019-05-09.
 */
@Service
public class SysMenuManagerImpl implements SysMenuManager {
    @Autowired
    private SysMenuMapper sysMenuMapper;

    private SysMenuExt procForList(SysMenuExt sysMenuExt) {
        if (sysMenuExt.getUrl() != null) {
            sysMenuExt.setRouterName(sysMenuExt.getUrl().replace(Constants.URL_PATH_SEPARATOR, ""));
        } else {
            sysMenuExt.setRouterName("");
        }

        return sysMenuExt;
    }

    /**
     * 递归查找子菜单
     *
     * @param id       当前菜单id
     * @param rootMenu 要查找的列表
     * @return
     */
    private List<SysMenuExt> listChild(long id, List<SysMenuExt> rootMenu) {
        // 子菜单
        List<SysMenuExt> childList = new ArrayList<>();
        for (SysMenuExt menu : rootMenu) {
            // 遍历所有节点，将父菜单id与传过来的id比较
            if (menu.getParentId() != 0 && menu.getParentId().equals(id)) {
                childList.add(menu);
            }
        }
        // 递归退出条件 无子菜单则退出
        if (childList.isEmpty()) {
            return Collections.emptyList();
        }
        // 把子菜单的子菜单再循环一遍
        for (SysMenuExt menu : childList) {
            Long menuPkid = menu.getMenuId();
            // 递归
            List<SysMenuExt> adminMenuExtList = listChild(menuPkid, rootMenu);
            menu.setChildren(adminMenuExtList);
        }
        return childList.stream().sorted(Comparator.comparing(SysMenuExt::getSortno))
                .collect(Collectors.toList());
    }

    @Override
    public List<SysMenuExt> listUserMenuTree(Long loginPkid) {
        List<SysMenuExt> menus = sysMenuMapper.list();
        List<SysMenuExt> userMenus = sysMenuMapper.listForUser(loginPkid);
        return listMenuTreeCommon(menus, userMenus);
    }

    @Override
    public List<SysMenuExt> listMenuTree() {
        List<SysMenuExt> menus = sysMenuMapper.list();
        List<SysMenuExt> userMenus = menus;
        return listMenuTreeCommon(menus, userMenus);
    }

    private List<SysMenuExt> listMenuTreeCommon(List<SysMenuExt> menus, List<SysMenuExt> userMenus) {
        List<SysMenuExt> userMenuTree = new ArrayList<>();
        List<SysMenuExt> tmpMenuTree = new ArrayList<>();

        for (int i = 0; i < userMenus.size(); i++) {
            fillTmpMenuTree(menus, userMenus, tmpMenuTree, i);
        }

        for (int i = 0; i < menus.size(); i++) {
            findFirstLevelMenu(menus, userMenuTree, tmpMenuTree, i);
        }

        for (SysMenuExt menu : userMenuTree) {
            setChildren(tmpMenuTree, menu);
        }

        return userMenuTree.stream().sorted(Comparator.comparing(SysMenuExt::getSortno))
                .collect(Collectors.toList());
    }

    private void fillTmpMenuTree(List<SysMenuExt> menus, List<SysMenuExt> userMenus, List<SysMenuExt> tmpMenuTree, int i) {
        Boolean flag = null;
        for (int j = 0; j < menus.size(); j++) {
            flag = false;
            if ((userMenus.get(i).getMenuId()).equals(menus.get(j).getMenuId())
                    || (menus.get(j).getMenuId()).equals(userMenus.get(i).getParentId())) {
                for (int k = 0; k < tmpMenuTree.size(); k++) {
                    if ((tmpMenuTree.get(k).getMenuId()).equals(menus.get(j).getMenuId())) {
                        flag = true;
                        break;
                    }
                }
                if (!flag) {
                    tmpMenuTree.add(procForList(menus.get(j)));
                }
            }
        }
    }

    /**
     * 为一级菜单设置子菜单，getChild是递归调用的
     *
     * @param tmpMenuTree
     * @param menu
     */
    private void setChildren(List<SysMenuExt> tmpMenuTree, SysMenuExt menu) {
        long parentPkid = menu.getMenuId();
        List<SysMenuExt> childMenu = listChild(parentPkid, tmpMenuTree);
        if (childMenu != null) {
            menu.setChildren(childMenu.stream().sorted(Comparator.comparing(SysMenuExt::getSortno))
                    .collect(Collectors.toList()));
        }
    }

    /**
     * 找到所有的一级菜单
     *
     * @param menus
     * @param userMenuTree
     * @param tmpMenuTree
     * @param i
     */
    private void findFirstLevelMenu(List<SysMenuExt> menus, List<SysMenuExt> userMenuTree, List<SysMenuExt> tmpMenuTree, int i) {
        if (menus.get(i).getParentId() != 0) {
            return;
        }

        for (int j = 0; j < tmpMenuTree.size(); j++) {
            if (tmpMenuTree.get(j).getParentId() != 0) {
                if (menus.get(i).getMenuId().toString().equals(tmpMenuTree.get(j).getParentId().toString())) {
                    userMenuTree.add(procForList(menus.get(i)));
                    break;
                }
            } else {
                userMenuTree.add(procForList(menus.get(i)));
                break;
            }
        }
    }

    @Override
    public List<SysMenuExt> list() {
        return sysMenuMapper.list();
    }

    @Override
    public Integer save(SysMenu sysMenu, LoginUserDTO loginUserDTO) {
        sysMenu.setParentId(Optional.ofNullable(sysMenu.getParentId()).orElse(0L));
        sysMenu.setCreateUserId(loginUserDTO.getLoginUserPkid());
        return sysMenuMapper.insertSelective(sysMenu);
    }

    @Override
    public Integer update(SysMenu sysMenu, LoginUserDTO loginUserDTO) {
        sysMenu.setCreateUserId(loginUserDTO.getLoginUserPkid());
        return sysMenuMapper.updateByPrimaryKeySelective(sysMenu);
    }

    @Override
    public Integer remove(MenuRemoveParam menuRemoveParam) {
        Integer effectRowNum = 0;

        for (Long menuId : menuRemoveParam.getPkids()) {
            SysMenu sysMenu = new SysMenu();
            sysMenu.setMenuId(menuId);
            effectRowNum += sysMenuMapper.deleteByPrimaryKey(sysMenu);
        }

        return effectRowNum;
    }
}
