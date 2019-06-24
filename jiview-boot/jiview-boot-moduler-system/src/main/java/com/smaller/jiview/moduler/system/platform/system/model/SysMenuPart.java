package com.smaller.jiview.moduler.system.platform.system.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 * @author xigf 2019/05/23
 */
@Table(name = "sys_menu_part")
public class SysMenuPart implements Serializable {
    /**
     * 部件ID
     */
    @Id
    @Column(name = "part_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long partId;

    /**
     * 菜单ID
     */
    @Column(name = "menu_id")
    private Long menuId;

    /**
     * UI组件ID
     */
    @Column(name = "cmp_id")
    private String cmpId;

    /**
     * UI组件类型(1:按钮组件;2:表单输入组件;3:容器面板组件)
     */
    @Column(name = "cmp_type")
    private Byte cmpType;

    /**
     * 备注
     */
    private String remark;

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
     * 获取部件ID
     *
     * @return part_id - 部件ID
     */
    public Long getPartId() {
        return partId;
    }

    /**
     * 设置部件ID
     *
     * @param partId 部件ID
     */
    public void setPartId(Long partId) {
        this.partId = partId;
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
     * 获取UI组件ID
     *
     * @return cmp_id - UI组件ID
     */
    public String getCmpId() {
        return cmpId;
    }

    /**
     * 设置UI组件ID
     *
     * @param cmpId UI组件ID
     */
    public void setCmpId(String cmpId) {
        this.cmpId = cmpId == null ? null : cmpId.trim();
    }

    /**
     * 获取UI组件类型(1:按钮组件;2:表单输入组件;3:容器面板组件)
     *
     * @return cmp_type - UI组件类型(1:按钮组件;2:表单输入组件;3:容器面板组件)
     */
    public Byte getCmpType() {
        return cmpType;
    }

    /**
     * 设置UI组件类型(1:按钮组件;2:表单输入组件;3:容器面板组件)
     *
     * @param cmpType UI组件类型(1:按钮组件;2:表单输入组件;3:容器面板组件)
     */
    public void setCmpType(Byte cmpType) {
        this.cmpType = cmpType;
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
        sb.append(", partId=").append(partId);
        sb.append(", menuId=").append(menuId);
        sb.append(", cmpId=").append(cmpId);
        sb.append(", cmpType=").append(cmpType);
        sb.append(", remark=").append(remark);
        sb.append(", createTime=").append(createTime);
        sb.append(", createUserId=").append(createUserId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}