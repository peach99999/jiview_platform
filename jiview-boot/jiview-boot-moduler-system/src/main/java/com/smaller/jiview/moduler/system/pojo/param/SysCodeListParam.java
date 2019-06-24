package com.smaller.jiview.moduler.system.pojo.param;

import com.smaller.jiview.core.pojo.param.BaseListParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 获取Code列表参数
 *
 * @author xiagf
 * @date 2019-05-13
 */
@Data
@ApiModel("获取Code列表参数")
public class SysCodeListParam extends BaseListParam {
    @ApiModelProperty("code type")
    private String codeType;
}
