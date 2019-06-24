package com.smaller.jiview.moduler.system.pojo.param;

import com.smaller.jiview.core.pojo.param.BaseParam;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

/**
 * @author xigf on 2019/05/09.
 */
@Data
@ApiModel("部件提交参数")
public class SysMenuPartSaveOrupdateParam extends BaseParam {

    private List<SysMenuPartParam> menuPartList;

}
