package com.smaller.jiview.core.pojo.param;

import com.smaller.jiview.core.pojo.dto.LoginUserDTO;
import com.smaller.jiview.core.util.BeanUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by jianghe on 2017/6/14.
 */
@Data
public class BaseParam implements Serializable {
    private static final long serialVersionUID = 5561713020738559083L;

    @ApiModelProperty(hidden = true)
    private LoginUserDTO loginUserDTO;

    public Map toMap() {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> mapWithoutNull = new HashMap<>();

        BeanUtil.obj2Map(map, this);

        map.remove("class");

        map.forEach((key, value) -> {
            if (map.get(key) != null) {
                mapWithoutNull.put(key, value);
            }
        });

        return mapWithoutNull;
    }

    public SortedMap toTreeMap() {
        SortedMap<String, Object> map = new TreeMap();
        SortedMap<String, Object> mapWithoutNull = new TreeMap<>();

        BeanUtil.obj2Map(map, this);

        map.remove("class");

        map.forEach((key, value) -> {
            if (map.get(key) != null) {
                mapWithoutNull.put(key, value);
            }
        });

        return mapWithoutNull;
    }
}
