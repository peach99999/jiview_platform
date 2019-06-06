package com.smaller.jiview.admin.platform.system.mapper;

import com.smaller.jiview.admin.platform.system.model.SysRoleMenuPart;
import com.smaller.jiview.admin.pojo.model.ext.SysRoleMenuPartExt;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

/**
 * @author xigf 2019/05/23
 */
public interface SysRoleMenuPartMapper extends Mapper<SysRoleMenuPart>, MySqlMapper<SysRoleMenuPart> {
    /**
     * 删除角色菜单部件信息
     *
     * @param roleId
     * @param menuIds
     * @return
     */
    Integer remove(@Param("roleId") Long roleId, @Param("menuIds") List<Long> menuIds);

    /**
     * 查询角色菜单部件信息
     *
     * @param menuId
     * @param userId
     * @return
     */
    List<SysRoleMenuPartExt> listUserRoleMenuPart(@Param("menuId") Long menuId, @Param("userId") Long userId);

    /**
     * 查询角色菜单部件权限
     *
     * @param menuId
     * @param roleId
     * @return
     */
    List<SysRoleMenuPart> listRoleMenuPart(@Param("menuId") Long menuId, @Param("roleId") Long roleId);
}