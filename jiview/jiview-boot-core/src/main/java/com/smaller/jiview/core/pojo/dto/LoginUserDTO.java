package com.smaller.jiview.core.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LoginUserDTO {
    @ApiModelProperty(hidden = true)
    private Long loginUserPkid;

    @ApiModelProperty(hidden = true)
    private String loginUserName;

    @ApiModelProperty(hidden = true)
    private Byte userType;

    @ApiModelProperty(hidden = true)
    private String ip;

    @ApiModelProperty(hidden = true)
    private String requestUri;
}
