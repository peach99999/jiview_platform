package com.smaller.jiview.core.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

/**
 * @author xiagf on 2018/12/5.
 */
@Component
@Data
public class JwtProperties {
    private String headerKey = HttpHeaders.AUTHORIZATION;

    @Value("${jwt.tokenType:#{null}}")
    private String tokenType;

    @Value("${jwt.signKey:#{null}}")
    private String signKey;

    @Value("${jwt.issuer:#{null}}")
    private String issuer;

    @Value("${jwt.audience:#{null}}")
    private String audience;

    @Value("${jwt.expiresInMinute:#{null}}")
    private Long expiresInMinute;
}
