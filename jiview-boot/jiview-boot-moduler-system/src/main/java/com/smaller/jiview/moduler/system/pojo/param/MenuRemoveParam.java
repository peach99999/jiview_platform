package com.smaller.jiview.moduler.system.pojo.param;

import com.smaller.jiview.core.pojo.param.BaseParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * MenuRemoveParam
 *
 * @author xiagf
 * @date 2019/05/09
 */
@Data
@ApiModel("用户删除参数")
public class MenuRemoveParam extends BaseParam {
    @ApiModelProperty("pkids")
    private List<Long> pkids;
}
