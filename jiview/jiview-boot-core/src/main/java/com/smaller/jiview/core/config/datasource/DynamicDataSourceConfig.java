package com.smaller.jiview.core.config.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 动态数据源配置
 *
 * @author xiagf
 * @date 2019/01/24
 */
@Configuration
public class DynamicDataSourceConfig {
    @Bean
    @ConfigurationProperties("spring.datasource.druid.primary")
    public DataSource defaultDataSource() {
        return new DruidDataSource();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.druid.read")
    public DataSource readDataSource() {
        return new DruidDataSource();
    }

    @Bean
    @Primary
    public DynamicDataSource dataSource(DataSource defaultDataSource) {
        Map<String, DataSource> targetDataSources = new HashMap<>();
        targetDataSources.put(DataSourceEnum.PRIMARY.getName(), this.defaultDataSource());
        targetDataSources.put(DataSourceEnum.READ.getName(), this.readDataSource());
        return new DynamicDataSource(defaultDataSource, targetDataSources);
    }
}
