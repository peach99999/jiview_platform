package com.smaller.jiview.moduler.system.platform.system.mapper;

import com.smaller.jiview.moduler.system.platform.system.model.SysUserMenuPart;
import com.smaller.jiview.moduler.system.pojo.model.ext.SysUserMenuPartExt;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

/**
 * @author xigf 2019/05/23
 */
public interface SysUserMenuPartMapper extends Mapper<SysUserMenuPart>, MySqlMapper<SysUserMenuPart> {
    /**
     * 查询用户菜单部件权限信息
     *
     * @param menuId
     * @param userId
     * @return
     */
    List<SysUserMenuPartExt> listUserMenuPart(@Param("menuId") Long menuId, @Param("userId") Long userId);

    /**
     * 删除用户菜单部件信息
     *
     * @param userId
     * @param menuIds
     * @return
     */
    Integer remove(@Param("userId") Long userId, @Param("menuIds") List<Long> menuIds);
}