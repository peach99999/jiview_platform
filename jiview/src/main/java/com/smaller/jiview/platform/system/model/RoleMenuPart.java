package com.smaller.jiview.platform.system.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "sys_role_menu_part")
public class RoleMenuPart {
    /**
     * 权限ID
     */
    @Id
    @Column(name = "authorize_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long authorizeId;

    /**
     * 角色ID
     */
    @Column(name = "role_id")
    private Long roleId;

    /**
     * 菜单ID
     */
    @Column(name = "menu_id")
    private Long menuId;

    /**
     * 组件ID
     */
    @Column(name = "part_id")
    private String partId;

    /**
     * UI组件权限类型(1:禁用;2:只读;3:编辑;4:显示;5:隐藏;6:挂起;7:激活;)
     */
    @Column(name = "part_auth_type")
    private Byte partAuthType;

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

    /**
     * 获取权限ID
     *
     * @return authorize_id - 权限ID
     */
    public Long getAuthorizeId() {
        return authorizeId;
    }

    /**
     * 设置权限ID
     *
     * @param authorizeId 权限ID
     */
    public void setAuthorizeId(Long authorizeId) {
        this.authorizeId = authorizeId;
    }

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
     * 获取菜单ID
     *
     * @return menu_id - 菜单ID
     */
    public Long getMenuId() {
        return menuId;
    }

    /**
     * 设置菜单ID
     *
     * @param menuId 菜单ID
     */
    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    /**
     * 获取组件ID
     *
     * @return part_id - 组件ID
     */
    public String getPartId() {
        return partId;
    }

    /**
     * 设置组件ID
     *
     * @param partId 组件ID
     */
    public void setPartId(String partId) {
        this.partId = partId == null ? null : partId.trim();
    }

    /**
     * 获取UI组件权限类型(1:禁用;2:只读;3:编辑;4:显示;5:隐藏;6:挂起;7:激活;)
     *
     * @return part_auth_type - UI组件权限类型(1:禁用;2:只读;3:编辑;4:显示;5:隐藏;6:挂起;7:激活;)
     */
    public Byte getPartAuthType() {
        return partAuthType;
    }

    /**
     * 设置UI组件权限类型(1:禁用;2:只读;3:编辑;4:显示;5:隐藏;6:挂起;7:激活;)
     *
     * @param partAuthType UI组件权限类型(1:禁用;2:只读;3:编辑;4:显示;5:隐藏;6:挂起;7:激活;)
     */
    public void setPartAuthType(Byte partAuthType) {
        this.partAuthType = partAuthType;
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
        sb.append(", authorizeId=").append(authorizeId);
        sb.append(", roleId=").append(roleId);
        sb.append(", menuId=").append(menuId);
        sb.append(", partId=").append(partId);
        sb.append(", partAuthType=").append(partAuthType);
        sb.append(", createTime=").append(createTime);
        sb.append(", createUserId=").append(createUserId);
        sb.append("]");
        return sb.toString();
    }
}