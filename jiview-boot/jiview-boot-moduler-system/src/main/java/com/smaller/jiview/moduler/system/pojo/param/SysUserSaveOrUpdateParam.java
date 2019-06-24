package com.smaller.jiview.moduler.system.pojo.param;

import com.smaller.jiview.core.pojo.param.BaseParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * SysUserSaveOrUpdateParam
 *
 * @author xiagf
 * @date 2019/05/13
 */
@Data
@ApiModel("保存用户参数")
public class SysUserSaveOrUpdateParam extends BaseParam {

    @ApiModelProperty("用户id")
    private Long id;

    @NotBlank(message = "登录账号不能为空")
    @ApiModelProperty("管理员登录用账号")
    private String account;

    @ApiModelProperty("登录密码")
    private String password;

    @NotBlank(message = "用户姓名不能为空")
    @ApiModelProperty("用户姓名")
    private String userName;

    @NotBlank(message = "email不能为空")
    @ApiModelProperty("email")
    private String email;

    @NotBlank(message = "手机号不能为空")
    @ApiModelProperty("手机号")
    private String mobile;

    @ApiModelProperty("性别(0:未知;1:男;2:女)")
    private Byte sex;

    @NotNull(message = "部门不能为空!")
    @ApiModelProperty("部门Id")
    private Long deptId;

    /**
     * 锁定标志(1:锁定;0:激活)
     */
    @NotNull(message = "锁定标志不能为空")
    @ApiModelProperty("锁定标志")
    private Boolean locked;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;

    /**
     * 人员类型(1:业务人员;2:管理员;3:系统内置人员;)
     */
    @NotNull(message = "人员类型不能为空")
    @ApiModelProperty("人员类型")
    private Byte userType;

    /**
     * 启用状态(1:启用;0:不启用)
     */
    @NotNull(message = "启用状态不能为空")
    @ApiModelProperty("启用状态")
    private Boolean enabled;

    @ApiModelProperty("角色集合")
    private List<Long> roleIdList;
}
