package com.bigdata.demo.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.bigdata.demo.datasource.DynamicDataSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;
import java.sql.JDBCType;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description todo 多数据源配置
 */
@Configuration
@MapperScan(basePackages = "com.bigdata.demo.mapper")
public class DataSourceConfig implements TransactionManagementConfigurer {



    @Bean(name = "bigdata")
    @ConfigurationProperties(prefix = "spring.bigdata.datasource.druid")
    public DataSource bigdataDataSource() {
        DataSource dataSource = new DruidDataSource();
        return dataSource;
    }

    @Bean(name = "monitor")
    @ConfigurationProperties(prefix = "spring.monitor.datasource.druid")
    public DataSource monitorDataSource() {
        DataSource dataSource = new DruidDataSource();
        return dataSource;
    }


    /**
     * 主数据源
     * @return
     */
    @Primary
    @Bean(name = "default")
    public DataSource defaultDatasource() {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        /**
         * 默认数据源
         */
        dynamicDataSource.setDefaultTargetDataSource(bigdataDataSource());
        //配置多数据源
        Map<Object, Object> datasouces = new HashMap<>();
        datasouces.put("bigdata", bigdataDataSource());
        datasouces.put("monitor", monitorDataSource());
        dynamicDataSource.setTargetDataSources(datasouces);
        return dynamicDataSource;
    }


    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("default")DataSource dataSource)throws Exception{
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource(dataSource);
        factory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/static/mapper/*.xml"));
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setJdbcTypeForNull(JdbcType.NULL);
        configuration.setMapUnderscoreToCamelCase(false);
        configuration.setCacheEnabled(false);
        factory.setConfiguration(configuration);
        return factory.getObject();
    }

    /**
     * 多数据源事务
     * @return
     */
    @Bean
    public PlatformTransactionManager transactionManager(){
        return new DataSourceTransactionManager(defaultDatasource());
    }

    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return transactionManager();
    }
}
