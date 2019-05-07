package com.smaller.jiview.core;

import com.smaller.jiview.core.config.web.FQCNBeanNameGenerator;
import com.smaller.jiview.core.config.web.MKRequestMappingHandlerMapping;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, MultipartAutoConfiguration.class})
@ComponentScan(basePackages = "com.smaller.*", nameGenerator = FQCNBeanNameGenerator.class)
//@MapperScan(basePackages = {"com.smaller.jiview.*.dao.*"})
@MapperScan(basePackages = {"com.smaller.jiview.*.platform.system.*"})
public class Application extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    /**
     * 自动生成request mapping
     *
     * @return
     */
    @Bean
    public WebMvcRegistrations webRegistrations() {
        return new WebMvcRegistrations() {
            @Override
            public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
                return new MKRequestMappingHandlerMapping();
            }
        };
    }
}
