package com.smaller.jiview.core.pojo.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserForReturnDTO implements Serializable {
    private static final long serialVersionUID = 3196076942937074998L;

    private Long userId;
    private String account;
    private Long deptId;

    @JSONField(name = "Authorization")
    private String authorization;

    private String userName;
}
