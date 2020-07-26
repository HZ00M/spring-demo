package com.bigdata.demo.aspect;

import com.bigdata.demo.annotation.DataSource;
import com.bigdata.demo.datasource.DataSourceContextHolder;
import org.apache.ibatis.jdbc.SQL;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect//(切面): 通常是一个类的注解，里面可以定义切入点和通知
@Component
public class DataSourceAspect implements Ordered {

    private Logger log = LoggerFactory.getLogger(DataSourceAspect.class);

    @Pointcut("@annotation(com.bigdata.demo.annotation.DataSource)")//(切入点)： JoinPoint的集合，是程序中需要注入Advice的位置的集合，指明Advice要在什么样的条件下才能被触发，在程序中主要体现为书写切入点表达式。
    public void datasourcePointcut() {
    }
    //    Advice(通知、切面)： 某个连接点所采用的处理逻辑，也就是向连接点注入的代码， AOP在特定的切入点上执行的增强处理。
    //
    //1.1 @Before： 标识一个前置增强方法，相当于BeforeAdvice的功能.
    //1.2 @After： final增强，不管是抛出异常或者正常退出都会执行.
    //1.3 @AfterReturning： 后置增强，似于AfterReturningAdvice, 方法正常退出时执行.
    //1.4 @AfterThrowing： 异常抛出增强，相当于ThrowsAdvice.
    //1.5 @Around： 环绕增强，相当于MethodInterceptor.
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
