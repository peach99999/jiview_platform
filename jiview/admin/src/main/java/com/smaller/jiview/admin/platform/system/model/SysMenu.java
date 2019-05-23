package com.smaller.jiview.admin.platform.system.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 * @author xigf 2019/05/23
 */
@Table(name = "sys_menu")
public class SysMenu implements Serializable {
    /**
     * 菜单ID
     */
    @Id
    @Column(name = "menu_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long menuId;

    /**
     * 父菜单ID
     */
    @Column(name = "parent_id")
    private Long parentId;

    /**
     * 菜单名称
     */
    @Column(name = "menu_name")
    private String menuName;

    /**
     * 节点图标CSS类名
     */
    private String iconcls;

    /**
     * 展开状态(1:展开;0:收缩)
     */
    private Boolean expanded;

    /**
     * 请求地址
     */
    private String url;

    /**
     * 叶子节点(0:树枝节点;1:叶子节点)
     */
    private Boolean leaf;

    /**
     * 排序号
     */
    private Integer sortno;

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
     * 获取父菜单ID
     *
     * @return parent_id - 父菜单ID
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * 设置父菜单ID
     *
     * @param parentId 父菜单ID
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
     * 获取菜单名称
     *
     * @return menu_name - 菜单名称
     */
    public String getMenuName() {
        return menuName;
    }

    /**
     * 设置菜单名称
     *
     * @param menuName 菜单名称
     */
    public void setMenuName(String menuName) {
        this.menuName = menuName == null ? null : menuName.trim();
    }

    /**
     * 获取节点图标CSS类名
     *
     * @return iconcls - 节点图标CSS类名
     */
    public String getIconcls() {
        return iconcls;
    }

    /**
     * 设置节点图标CSS类名
     *
     * @param iconcls 节点图标CSS类名
     */
    public void setIconcls(String iconcls) {
        this.iconcls = iconcls == null ? null : iconcls.trim();
    }

    /**
     * 获取展开状态(1:展开;0:收缩)
     *
     * @return expanded - 展开状态(1:展开;0:收缩)
     */
    public Boolean getExpanded() {
        return expanded;
    }

    /**
     * 设置展开状态(1:展开;0:收缩)
     *
     * @param expanded 展开状态(1:展开;0:收缩)
     */
    public void setExpanded(Boolean expanded) {
        this.expanded = expanded;
    }

    /**
     * 获取请求地址
     *
     * @return url - 请求地址
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置请求地址
     *
     * @param url 请求地址
     */
    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    /**
     * 获取叶子节点(0:树枝节点;1:叶子节点)
     *
     * @return leaf - 叶子节点(0:树枝节点;1:叶子节点)
     */
    public Boolean getLeaf() {
        return leaf;
    }

    /**
     * 设置叶子节点(0:树枝节点;1:叶子节点)
     *
     * @param leaf 叶子节点(0:树枝节点;1:叶子节点)
     */
    public void setLeaf(Boolean leaf) {
        this.leaf = leaf;
    }

    /**
     * 获取排序号
     *
     * @return sortno - 排序号
     */
    public Integer getSortno() {
        return sortno;
    }

    /**
     * 设置排序号
     *
     * @param sortno 排序号
     */
    public void setSortno(Integer sortno) {
        this.sortno = sortno;
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
        sb.append(", menuId=").append(menuId);
        sb.append(", parentId=").append(parentId);
        sb.append(", menuName=").append(menuName);
        sb.append(", iconcls=").append(iconcls);
        sb.append(", expanded=").append(expanded);
        sb.append(", url=").append(url);
        sb.append(", leaf=").append(leaf);
        sb.append(", sortno=").append(sortno);
        sb.append(", remark=").append(remark);
        sb.append(", createTime=").append(createTime);
        sb.append(", createUserId=").append(createUserId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}