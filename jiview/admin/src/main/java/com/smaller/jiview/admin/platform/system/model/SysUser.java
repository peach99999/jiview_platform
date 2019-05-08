package com.smaller.jiview.admin.platform.system.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "sys_user")
public class SysUser implements Serializable {
    /**
     * 用户ID
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户名
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * 登陆帐户
     */
    private String account;

    /**
     * 密码
     */
    private String password;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 性别(0:未知;1:男;2:女)
     */
    private Byte sex;

    /**
     * 部门ID
     */
    @Column(name = "dept_id")
    private Long deptId;

    /**
     * 锁定标志(1:锁定;0:激活)
     */
    private Boolean locked;

    /**
     * 备注
     */
    private String remark;

    /**
     * 人员类型(1:业务人员;2:管理员;3:系统内置人员;)
     */
    @Column(name = "user_type")
    private Byte userType;

    /**
     * 启用状态(1:启用;0:不启用)
     */
    private Boolean enabled;

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
     * 获取用户ID
     *
     * @return id - 用户ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置用户ID
     *
     * @param id 用户ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取用户名
     *
     * @return user_name - 用户名
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 设置用户名
     *
     * @param userName 用户名
     */
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    /**
     * 获取登陆帐户
     *
     * @return account - 登陆帐户
     */
    public String getAccount() {
        return account;
    }

    /**
     * 设置登陆帐户
     *
     * @param account 登陆帐户
     */
    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    /**
     * 获取密码
     *
     * @return password - 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码
     *
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * 获取手机号
     *
     * @return mobile - 手机号
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 设置手机号
     *
     * @param mobile 手机号
     */
    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    /**
     * 获取邮箱
     *
     * @return email - 邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置邮箱
     *
     * @param email 邮箱
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**
     * 获取性别(0:未知;1:男;2:女)
     *
     * @return sex - 性别(0:未知;1:男;2:女)
     */
    public Byte getSex() {
        return sex;
    }

    /**
     * 设置性别(0:未知;1:男;2:女)
     *
     * @param sex 性别(0:未知;1:男;2:女)
     */
    public void setSex(Byte sex) {
        this.sex = sex;
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
     * 获取人员类型(1:业务人员;2:管理员;3:系统内置人员;)
     *
     * @return user_type - 人员类型(1:业务人员;2:管理员;3:系统内置人员;)
     */
    public Byte getUserType() {
        return userType;
    }

    /**
     * 设置人员类型(1:业务人员;2:管理员;3:系统内置人员;)
     *
     * @param userType 人员类型(1:业务人员;2:管理员;3:系统内置人员;)
     */
    public void setUserType(Byte userType) {
        this.userType = userType;
    }

    /**
     * 获取启用状态(1:启用;0:不启用)
     *
     * @return enabled - 启用状态(1:启用;0:不启用)
     */
    public Boolean getEnabled() {
        return enabled;
    }

    /**
     * 设置启用状态(1:启用;0:不启用)
     *
     * @param enabled 启用状态(1:启用;0:不启用)
     */
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
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
        sb.append(", id=").append(id);
        sb.append(", userName=").append(userName);
        sb.append(", account=").append(account);
        sb.append(", password=").append(password);
        sb.append(", mobile=").append(mobile);
        sb.append(", email=").append(email);
        sb.append(", sex=").append(sex);
        sb.append(", deptId=").append(deptId);
        sb.append(", locked=").append(locked);
        sb.append(", remark=").append(remark);
        sb.append(", userType=").append(userType);
        sb.append(", enabled=").append(enabled);
        sb.append(", createTime=").append(createTime);
        sb.append(", createUserId=").append(createUserId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}