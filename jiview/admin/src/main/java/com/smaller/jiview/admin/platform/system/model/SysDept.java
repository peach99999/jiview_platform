package com.smaller.jiview.admin.platform.system.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 * @author xigf 2019/05/23
 */
@Table(name = "sys_dept")
public class SysDept implements Serializable {
    /**
     * 部门ID
     */
    @Id
    @Column(name = "dept_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deptId;

    /**
     * 父部门ID
     */
    @Column(name = "parent_id")
    private Long parentId;

    /**
     * 部门名称
     */
    @Column(name = "dept_name")
    private String deptName;

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
     * 获取父部门ID
     *
     * @return parent_id - 父部门ID
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * 设置父部门ID
     *
     * @param parentId 父部门ID
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
     * 获取部门名称
     *
     * @return dept_name - 部门名称
     */
    public String getDeptName() {
        return deptName;
    }

    /**
     * 设置部门名称
     *
     * @param deptName 部门名称
     */
    public void setDeptName(String deptName) {
        this.deptName = deptName == null ? null : deptName.trim();
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
        sb.append(", deptId=").append(deptId);
        sb.append(", parentId=").append(parentId);
        sb.append(", deptName=").append(deptName);
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