package com.hzoom.demo.activeObject;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * 模式角色   proxy参与者的可复用角色
 */
public abstract class AbstractObjectProxy {
    private static class DispatchInvocationHandler implements InvocationHandler {
        private final Object delegate;
        private final ExecutorService scheduler;

        public DispatchInvocationHandler(Object delegate, ExecutorService executorService) {
            this.delegate = delegate;
            this.scheduler = executorService;
        }

        private String makeDelegateMethodName(final Method method, final Object[] args) {
            String name = method.getName();
            name = "do" + Character.toUpperCase(name.charAt(0)) + name.substring(1);
            return name;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Object returnValue;
            final Object delegate = this.delegate;
            final Method delegateMethod;
            //如果拦截到的是异步方法，则转移到相应的doXXX方法
            if (Future.class.isAssignableFrom(method.getReturnType())) {
                delegateMethod = delegate.getClass().getMethod(makeDelegateMethodName(method, args), method.getParameterTypes());
                final ExecutorService scheduler = this.scheduler;
                Callable<Object> methodRequest = new Callable<Object>() {
                    @Override
                    public Object call() throws Exception {
                        Object rv = null;
                        try {
                            rv = delegateMethod.invoke(delegate, args);
                        } catch (IllegalArgumentException e) {
                            throw new Exception(e);
                        } catch (IllegalAccessException e) {
                            throw new Exception(e);
                        } catch (InvocationTargetException e) {
                            throw new Exception(e);
                        }
                        return rv;
                    }
                };
                Future<Object> future = scheduler.submit(methodRequest);
                returnValue = future;
            } else {
                //若拦截的方法调用的不熟异步方法，则直接转发
                delegateMethod = delegate.getClass().getMethod(method.getName(), method.getParameterTypes());
                returnValue = delegateMethod.invoke(delegate, args);
            }
            return returnValue;
        }
    }

    /**
     * 生成一个实现指定接口的Active Object Proxy对象
     *
     * @param interf    要实现的Active Object接口
     * @param servant   Active Object 的servant 参与者实例
     * @param scheduler Active Object 的scheduler实例
     * @return Active Object 的 proxy实例
     */
    public static <T> T newInstance(Class<T> interf, Object servant, ExecutorService scheduler) {
        @SuppressWarnings("unchecked")
        T f = (T) Proxy.newProxyInstance(interf.getClassLoader(), new Class[]{interf}, new DispatchInvocationHandler(servant, scheduler));
        return f;
    }

}
