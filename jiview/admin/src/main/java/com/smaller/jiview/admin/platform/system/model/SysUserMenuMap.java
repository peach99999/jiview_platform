package com.smaller.jiview.admin.platform.system.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "sys_user_menu_map")
public class SysUserMenuMap implements Serializable {
    /**
     * 权限ID
     */
    @Column(name = "authorize_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long authorizeId;

    /**
     * 用户ID
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 菜单ID
     */
    @Column(name = "menu_id")
    private Long menuId;

    /**
     * 权限级别(1:访问权限;2:管理权限)
     */
    @Column(name = "authorize_level")
    private Byte authorizeLevel;

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
     * 获取用户ID
     *
     * @return user_id - 用户ID
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置用户ID
     *
     * @param userId 用户ID
     */
    public void setUserId(Long userId) {
        this.userId = userId;
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
     * 获取权限级别(1:访问权限;2:管理权限)
     *
     * @return authorize_level - 权限级别(1:访问权限;2:管理权限)
     */
    public Byte getAuthorizeLevel() {
        return authorizeLevel;
    }

    /**
     * 设置权限级别(1:访问权限;2:管理权限)
     *
     * @param authorizeLevel 权限级别(1:访问权限;2:管理权限)
     */
    public void setAuthorizeLevel(Byte authorizeLevel) {
        this.authorizeLevel = authorizeLevel;
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
        sb.append(", userId=").append(userId);
        sb.append(", menuId=").append(menuId);
        sb.append(", authorizeLevel=").append(authorizeLevel);
        sb.append(", createTime=").append(createTime);
        sb.append(", createUserId=").append(createUserId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}