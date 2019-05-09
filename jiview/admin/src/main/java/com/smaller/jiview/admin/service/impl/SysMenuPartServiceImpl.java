package com.smaller.jiview.admin.service.impl;

import com.smaller.jiview.admin.manager.SysMenuPartManager;
import com.smaller.jiview.admin.platform.system.mapper.SysMenuPartMapper;
import com.smaller.jiview.admin.platform.system.model.SysMenuPart;
import com.smaller.jiview.admin.pojo.param.SysMenuPartParam;
import com.smaller.jiview.admin.pojo.param.SysMenuPartSaveOrupdateParam;
import com.smaller.jiview.admin.service.SysMenuPartService;
import com.smaller.jiview.core.pojo.bo.ResultBO;
import com.smaller.jiview.core.util.BeanUtil;
import com.smaller.jiview.core.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SysMenuPartServiceImpl implements SysMenuPartService {

    @Autowired
    private SysMenuPartMapper sysMenuPartMapper;

    @Autowired
    private SysMenuPartManager sysMenuPartManager;

    @Override
    public ResultBO menuPartSaveOrupdate(SysMenuPartSaveOrupdateParam sysMenuPartSaveOrupdateParam) {
        ResultBO result = new ResultBO();
        Integer count = sysMenuPartSaveOrupdateParam.getMenuPartList().size();
        if (count <= 0) {
            return result;
        }
        Set<Long> partIddSet = new HashSet<>();
        // 查询该菜单是否有设置过部件
        List<SysMenuPart> menuPartList = sysMenuPartManager.listMenuPart(CommonUtil.getFirstElement(sysMenuPartSaveOrupdateParam.getMenuPartList()).getMenuId());
        for (SysMenuPartParam sysMenuPartParam : sysMenuPartSaveOrupdateParam.getMenuPartList()) {
            SysMenuPart sysMenuPart = new SysMenuPart();
            BeanUtil.springCopy(sysMenuPartParam, sysMenuPart);
            // partId不为空表示更新 否则为新增
            if (sysMenuPartParam.getPartId() != null) {
                partIddSet.add(sysMenuPart.getPartId());
                sysMenuPartMapper.updateByPrimaryKeySelective(sysMenuPart);
            } else {
                sysMenuPart.setCreateUserId(sysMenuPartSaveOrupdateParam.getLoginUserDTO().getLoginUserPkid());
                sysMenuPartMapper.insertSelective(sysMenuPart);
            }
        }
        delMenuPart(count, partIddSet, menuPartList);
        result.setMsg("菜单部件提交成功!");
        return result;
    }

    @Override
    public ResultBO get(Long menuId) {
        ResultBO<SysMenuPart> result = new ResultBO<>();
        result.setRows(sysMenuPartManager.listMenuPart(menuId));
        return result;
    }

    /**
     * 判断菜单部件是否删除过
     *
     * @param newCount
     * @param partIdSet
     * @param oldMenuPartList
     */
    private void delMenuPart(Integer newCount, Set<Long> partIdSet, List<SysMenuPart> oldMenuPartList) {
        if (newCount < oldMenuPartList.size()) {
            for (SysMenuPart sysMenuPart : oldMenuPartList) {
                if (!partIdSet.contains(sysMenuPart.getPartId())) {
                    sysMenuPartMapper.deleteByPrimaryKey(sysMenuPart);
                }
            }
        }
    }

}
