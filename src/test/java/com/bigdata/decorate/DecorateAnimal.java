package com.bigdata.decorate;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;

public class DecorateAnimal implements Animal{

    private Animal animal;

    private Class<? extends Feature> feature;

    public DecorateAnimal(Animal animal,Class<? extends Feature> feature){
        this.animal = animal;
        this.feature = feature;
    }

    @Override
    public void doStuff() {
        InvocationHandler handler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Object obj = null;
                if (Modifier.isPublic(method.getModifiers())){
                    obj = method.invoke(feature.newInstance(),args);
                }
                animal.doStuff();
                return obj;
            }
        };

        ClassLoader loader = getClass().getClassLoader();
        Feature proxy = (Feature) Proxy.newProxyInstance(loader,feature.getInterfaces(),handler);
        proxy.load();
    }

    public static void main(String[] args) {
        Animal animal = new Rat();
        animal = new DecorateAnimal(animal,FlyFeature.class);
        animal = new DecorateAnimal(animal,DigFeature.class);
        animal.doStuff();
    }
}
