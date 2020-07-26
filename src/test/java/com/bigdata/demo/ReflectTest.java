package com.bigdata.demo;

import com.bigdata.demo.proxy.*;
import org.apache.ibatis.builder.BaseBuilder;
import org.apache.ibatis.builder.xml.XMLConfigBuilder;
import org.apache.ibatis.cache.Cache;
import org.apache.ibatis.cache.decorators.WeakCache;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.io.ResolverUtil;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.reflection.TypeParameterResolver;
import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.type.JdbcType;
import org.junit.Test;

import java.lang.ref.SoftReference;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;

import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;
import sun.reflect.generics.reflectiveObjects.TypeVariableImpl;

public class ReflectTest {
    @Test
    public   void test()throws Exception{
        B<Long> b = new B<>();
        Field f = A.class.getDeclaredField("map");
        System.out.println(f.getGenericType());
        System.out.println(f.getGenericType() instanceof ParameterizedType);

        Type type = TypeParameterResolver.resolveFieldType(f, ParameterizedTypeImpl.make(B.class,new Type[]{String.class},ReflectTest.class));
        System.out.println(type.getClass());
        ParameterizedType p = (ParameterizedType) type;
        System.out.println(p.getRawType());
        System.out.println(p.getOwnerType());
        for (Type t:p.getActualTypeArguments()){
            System.out.println(t);
        }
//        Plugin
//        SqlNode
//        MappedStatement
//        Interceptor
//        XMLConfigBuilder
//        BaseBuilder
//        Configuration
//        SqlSessionFactoryBuilder;
//            Configuration
    }
    class A<K,V>{
        protected Map<K,V> map;
    }
    class B <T> extends A<T,T>{
    }

}


