package com.smaller.jiview.admin.pojo.param;

import com.smaller.jiview.core.pojo.param.BaseListParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 获取Code列表参数
 *
 * @author xiagf
 * @date 2019-05-13
 */
@Getter
@Setter
@ApiModel("获取Code列表参数")
public class SysCodeListParam extends BaseListParam {
    @ApiModelProperty("code type")
    private String codeType;
}
