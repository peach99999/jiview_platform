package com.smaller.jiview.core.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by xiagf on 2019/03/01
 */
@Component
@Data
public class ServerProperties {
    @Value("${server.servlet.context-path:#{''}}")
    private String servletContextPath;
}
