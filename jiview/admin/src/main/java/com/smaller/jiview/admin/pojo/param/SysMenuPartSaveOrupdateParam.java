package com.smaller.jiview.admin.pojo.param;

import com.smaller.jiview.core.pojo.param.BaseParam;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

/**
 * Created by xigf on 2019/05/09.
 */
@Data
@ApiModel("部件提交参数")
public class SysMenuPartSaveOrupdateParam extends BaseParam {

    List<SysMenuPartParam> menuPartList;

}
