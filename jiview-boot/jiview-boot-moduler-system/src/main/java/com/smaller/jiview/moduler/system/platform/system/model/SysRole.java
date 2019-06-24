package com.smaller.jiview.moduler.system.platform.system.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 * @author xigf 2019/05/23
 */
@Table(name = "sys_role")
public class SysRole implements Serializable {
    /**
     * 角色ID
     */
    @Id
    @Column(name = "role_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;

    /**
     * 角色名称
     */
    @Column(name = "role_name")
    private String roleName;

    /**
     * 部门ID
     */
    @Column(name = "dept_id")
    private Long deptId;

    /**
     * 角色类型(1:业务角色;2:管理角色 ;3:系统内置角色)
     */
    private Byte roletype;

    /**
     * 备注
     */
    private String remark;

    /**
     * 锁定标志(1:锁定;0:激活)
     */
    private Boolean locked;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 创建人ID
     */
    @Column(name = "create_user_id")
    private Long createUserId;

    private static final long serialVersionUID = 1L;

    /**
     * 获取角色ID
     *
     * @return role_id - 角色ID
     */
    public Long getRoleId() {
        return roleId;
    }

    /**
     * 设置角色ID
     *
     * @param roleId 角色ID
     */
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    /**
     * 获取角色名称
     *
     * @return role_name - 角色名称
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * 设置角色名称
     *
     * @param roleName 角色名称
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    /**
     * 获取部门ID
     *
     * @return dept_id - 部门ID
     */
    public Long getDeptId() {
        return deptId;
    }

    /**
     * 设置部门ID
     *
     * @param deptId 部门ID
     */
    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    /**
     * 获取角色类型(1:业务角色;2:管理角色 ;3:系统内置角色)
     *
     * @return roletype - 角色类型(1:业务角色;2:管理角色 ;3:系统内置角色)
     */
    public Byte getRoletype() {
        return roletype;
    }

    /**
     * 设置角色类型(1:业务角色;2:管理角色 ;3:系统内置角色)
     *
     * @param roletype 角色类型(1:业务角色;2:管理角色 ;3:系统内置角色)
     */
    public void setRoletype(Byte roletype) {
        this.roletype = roletype;
    }

    /**
     * 获取备注
     *
     * @return remark - 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置备注
     *
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * 获取锁定标志(1:锁定;0:激活)
     *
     * @return locked - 锁定标志(1:锁定;0:激活)
     */
    public Boolean getLocked() {
        return locked;
    }

    /**
     * 设置锁定标志(1:锁定;0:激活)
     *
     * @param locked 锁定标志(1:锁定;0:激活)
     */
    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取创建人ID
     *
     * @return create_user_id - 创建人ID
     */
    public Long getCreateUserId() {
        return createUserId;
    }

    /**
     * 设置创建人ID
     *
     * @param createUserId 创建人ID
     */
    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", roleId=").append(roleId);
        sb.append(", roleName=").append(roleName);
        sb.append(", deptId=").append(deptId);
        sb.append(", roletype=").append(roletype);
        sb.append(", remark=").append(remark);
        sb.append(", locked=").append(locked);
        sb.append(", createTime=").append(createTime);
        sb.append(", createUserId=").append(createUserId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}