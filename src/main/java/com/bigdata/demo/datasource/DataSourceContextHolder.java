package com.bigdata.demo.datasource;

import com.bigdata.demo.enums.DatabaseTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description: TODO 保存一个线程安全的DatabaseType容器
 */
public class DataSourceContextHolder {

    public static final Logger log = LoggerFactory.getLogger(DataSourceContextHolder.class);

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();
    /**
     * 默认数据源 bigdata
     */
    public static final String default_DatabaseType = DatabaseTypeEnum.BIGDATA.getDatasouceType();
    public static void setContextHolder(String databaseType){
        contextHolder.set(databaseType);
    }

    /**
     *设置数据库名
     */
    public static void setDB(String dbType){
        log.debug("切换数据源",dbType);
        contextHolder.set(dbType);
    }

    // 获取数据源名
    public static String getDB() {
        return contextHolder.get();
    }

    // 清除数据源名
    public static void clearDB() {
        contextHolder.remove();
    }
}
