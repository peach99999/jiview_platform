package com.smaller.jiview.admin.pojo.param;

import com.smaller.jiview.admin.platform.system.model.SysRoleMenuPart;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by xigf on 2019/05/09.
 */
@Data
@ApiModel("admin管理员登陆信息")
public class SysMenuPartSaveOrupdateParam {

    @ApiModelProperty("部件id")
    private Long partId;

    @NotNull(message = "菜单id不能为空")
    @ApiModelProperty("菜单id")
    private Long menuId;

    @NotNull(message = "UI组件ID不能为空")
    @ApiModelProperty("UI组件ID")
    private Long cmpId;

    @NotNull(message = "UI组件类型不能为空")
    @ApiModelProperty("UI组件类型(1:按钮组件;2:表单输入组件;3:容器面板组件)")
    private String cmpType;

    @ApiModelProperty("备注")
    private String remark;

    List<SysRoleMenuPart> menuPartList;

}
