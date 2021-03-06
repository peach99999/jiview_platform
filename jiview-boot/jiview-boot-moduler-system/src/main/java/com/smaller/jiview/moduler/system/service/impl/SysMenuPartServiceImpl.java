package com.smaller.jiview.moduler.system.service.impl;

import com.smaller.jiview.moduler.system.manager.SysMenuPartManager;
import com.smaller.jiview.moduler.system.platform.system.mapper.SysMenuPartMapper;
import com.smaller.jiview.moduler.system.platform.system.model.SysMenuPart;
import com.smaller.jiview.moduler.system.pojo.param.SysMenuPartParam;
import com.smaller.jiview.moduler.system.pojo.param.SysMenuPartSaveOrupdateParam;
import com.smaller.jiview.moduler.system.service.SysMenuPartService;
import com.smaller.jiview.core.pojo.bo.ResultBO;
import com.smaller.jiview.core.pojo.dto.DiffDTO;
import com.smaller.jiview.core.util.BeanUtil;
import com.smaller.jiview.core.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xigf 2019/05/23
 */
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
        if (count == 1) {
            // 如果只有menuId不为空 则表示部件全部删除
            SysMenuPartParam sysMenuPartParam = CommonUtil.getFirstElement(sysMenuPartSaveOrupdateParam.getMenuPartList());
            if (sysMenuPartParam == null) {
                return result;
            }
            if (ObjectUtils.isEmpty(sysMenuPartParam.getCmpId()) && ObjectUtils.isEmpty(sysMenuPartParam.getCmpType()) && !ObjectUtils.isEmpty(sysMenuPartParam.getMenuId())) {
                sysMenuPartManager.remove(sysMenuPartParam.getMenuId());
                return result;
            }
        }
        List<Long> newPartIdList = new ArrayList<>();
        // 查询该菜单是否有设置过部件
        List<SysMenuPart> menuPartList = sysMenuPartManager.listMenuPart(CommonUtil.getFirstElement(sysMenuPartSaveOrupdateParam.getMenuPartList()).getMenuId());
        for (SysMenuPartParam sysMenuPartParam : sysMenuPartSaveOrupdateParam.getMenuPartList()) {
            SysMenuPart sysMenuPart = new SysMenuPart();
            BeanUtil.springCopy(sysMenuPartParam, sysMenuPart);
            // partId不为空表示更新 否则为新增
            if (sysMenuPartParam.getPartId() != null) {
                newPartIdList.add(sysMenuPart.getPartId());
                sysMenuPartMapper.updateByPrimaryKeySelective(sysMenuPart);
            } else {
                sysMenuPart.setCreateUserId(sysMenuPartSaveOrupdateParam.getLoginUserDTO().getLoginUserPkid());
                sysMenuPartMapper.insertSelective(sysMenuPart);
            }
        }
        delMenuPart(newPartIdList, menuPartList);
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
     * @param newPartIdList
     * @param oldMenuPartList
     */
    private void delMenuPart(List<Long> newPartIdList, List<SysMenuPart> oldMenuPartList) {
        List<Long> oldPartIdList = new ArrayList<>();
        oldMenuPartList.forEach(sysMenuPart -> oldPartIdList.add(sysMenuPart.getPartId()));

        DiffDTO diff = CommonUtil.diff(newPartIdList, oldPartIdList);
        List<Long> partIdsForRemove = diff.getDeleted();
        for (Long partId : partIdsForRemove) {
            for (SysMenuPart sysMenuPart : oldMenuPartList) {
                if (sysMenuPart.getPartId().equals(partId)) {
                    sysMenuPartMapper.deleteByPrimaryKey(sysMenuPart);
                }
            }
        }
    }

}
