package com.smaller.jiview.core.config.datasource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 动态数据源
 *
 * @author xiagf
 * @date 2019/01/24
 */
@Slf4j
public class DynamicDataSource extends AbstractRoutingDataSource {
    private static final ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal<>();

    public DynamicDataSource(DataSource defaultTargetDataSource, Map<String, DataSource> targetDataSources) {
        super.setDefaultTargetDataSource(defaultTargetDataSource);
        super.setTargetDataSources(new HashMap<>(targetDataSources));
        super.afterPropertiesSet();
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return getDataSource();
    }

    /**
     * 设置数据源
     *
     * @param dataSource
     */
    public static void setDataSource(String dataSource) {
        log.info("DynamicDataSource→setDataSource→" + dataSource);
        CONTEXT_HOLDER.set(dataSource);
    }

    /**
     * 获取数据源
     *
     * @return
     */
    public static String getDataSource() {
        String dataSource = CONTEXT_HOLDER.get();

        // 如果没有指定数据源，使用默认数据源
        if (null == dataSource) {
            dataSource = DataSourceEnum.PRIMARY.getName();
        }

        DynamicDataSource.setDataSource(dataSource);

        log.info("DynamicDataSource→getDataSource→" + dataSource);

        return dataSource;
    }

    /**
     * 清除数据源
     */
    public static void clearDataSource() {
        log.info("DynamicDataSource→clearDataSource→" + CONTEXT_HOLDER.get());
        CONTEXT_HOLDER.remove();
    }
}
