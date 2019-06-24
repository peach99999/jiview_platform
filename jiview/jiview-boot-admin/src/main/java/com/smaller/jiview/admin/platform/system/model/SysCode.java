package com.smaller.jiview.admin.platform.system.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 * @author xigf 2019/05/23
 */
@Table(name = "sys_code")
public class SysCode implements Serializable {
    /**
     * id
     */
    @Id
    @Column(name = "code_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codeId;

    /**
     * code种类
     */
    @Column(name = "code_type")
    private String codeType;

    /**
     * code值
     */
    @Column(name = "code_key")
    private String codeKey;

    /**
     * code名
     */
    @Column(name = "code_value")
    private String codeValue;

    /**
     * code顺序
     */
    @Column(name = "code_sort")
    private Integer codeSort;

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
     * 获取id
     *
     * @return code_id - id
     */
    public Long getCodeId() {
        return codeId;
    }

    /**
     * 设置id
     *
     * @param codeId id
     */
    public void setCodeId(Long codeId) {
        this.codeId = codeId;
    }

    /**
     * 获取code种类
     *
     * @return code_type - code种类
     */
    public String getCodeType() {
        return codeType;
    }

    /**
     * 设置code种类
     *
     * @param codeType code种类
     */
    public void setCodeType(String codeType) {
        this.codeType = codeType == null ? null : codeType.trim();
    }

    /**
     * 获取code值
     *
     * @return code_key - code值
     */
    public String getCodeKey() {
        return codeKey;
    }

    /**
     * 设置code值
     *
     * @param codeKey code值
     */
    public void setCodeKey(String codeKey) {
        this.codeKey = codeKey == null ? null : codeKey.trim();
    }

    /**
     * 获取code名
     *
     * @return code_value - code名
     */
    public String getCodeValue() {
        return codeValue;
    }

    /**
     * 设置code名
     *
     * @param codeValue code名
     */
    public void setCodeValue(String codeValue) {
        this.codeValue = codeValue == null ? null : codeValue.trim();
    }

    /**
     * 获取code顺序
     *
     * @return code_sort - code顺序
     */
    public Integer getCodeSort() {
        return codeSort;
    }

    /**
     * 设置code顺序
     *
     * @param codeSort code顺序
     */
    public void setCodeSort(Integer codeSort) {
        this.codeSort = codeSort;
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
        sb.append(", codeId=").append(codeId);
        sb.append(", codeType=").append(codeType);
        sb.append(", codeKey=").append(codeKey);
        sb.append(", codeValue=").append(codeValue);
        sb.append(", codeSort=").append(codeSort);
        sb.append(", createTime=").append(createTime);
        sb.append(", createUserId=").append(createUserId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}