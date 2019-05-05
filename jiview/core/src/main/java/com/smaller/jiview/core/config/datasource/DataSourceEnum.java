package com.smaller.jiview.core.config.datasource;

/**
 * 数据源枚举
 *
 * @author xiagf
 * @date 2019/01/24
 */
public enum DataSourceEnum {

    PRIMARY("defaultDataSource"),
    READ("readDataSource");

    private String name;

    DataSourceEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
