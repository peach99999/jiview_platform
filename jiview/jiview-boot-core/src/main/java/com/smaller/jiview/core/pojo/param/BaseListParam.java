package com.smaller.jiview.core.pojo.param;

import com.smaller.jiview.core.util.CommonUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author jianghe on 2017/6/14.
 */
@Data
public class BaseListParam extends BaseParam {
    @ApiModelProperty("页码")
    private Integer pageNo;
    @ApiModelProperty("页数")
    private Integer pageSize;

    @ApiModelProperty(hidden = true)
    private Integer startRow;

    public Integer getStartRow() {
        return CommonUtil.calcStartRow(this.pageNo, this.pageSize);
    }
}
