package com.bigdata.demo.aspect;

import com.bigdata.demo.annotation.DataSource;
import com.bigdata.demo.datasource.DataSourceContextHolder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class DataSourceAspect implements Ordered {

    private Logger log = LoggerFactory.getLogger(DataSourceAspect.class);

    @Pointcut("@annotation(com.bigdata.demo.annotation.DataSource)")
    public void datasourcePointcut() {
    }
    @Around("datasourcePointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable{
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        //转换数据源
        DataSource dataSource = method.getAnnotation(DataSource.class);
        if(dataSource==null){
            DataSourceContextHolder.setDB(DataSourceContextHolder.default_DatabaseType);
            log.info("set  datasource is " + DataSourceContextHolder.default_DatabaseType);
        }else {
            DataSourceContextHolder.setDB(dataSource.name());
            log.info("set  datasource is " + dataSource.name());
        }
        try {
            return point.proceed();
        } finally {
            DataSourceContextHolder.clearDB();
            log.info("clean datasource");
        }
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
