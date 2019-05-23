package com.smaller.jiview.admin.pojo.param;

import com.smaller.jiview.core.pojo.param.BaseListParam;
import lombok.Data;

/**
 * SysRoleListParam
 *
 * @author xiagf
 * @date 2019/05/10
 */
@Data
public class SysRoleListParam extends BaseListParam {

    /**
     * 角色名
     */
    private String roleName;

    /**
     * 部门id
     */
    private Long deptId;
}
