package com.bigdata.demo;

import org.apache.ibatis.javassist.*;

import java.lang.reflect.Method;

public class JavassistTest {
    public static void main(String[] args)throws Exception {
        ClassPool cp = ClassPool.getDefault();
        //妥生成的类名称为com.xxx.test.JavassistTest
        CtClass ctClass = cp.makeClass("com.bigdata.demo.Javasist");
        StringBuilder body = null;
        // 创建字段，指定了字段类型、字段名称、字段所属的类
        CtField field = new CtField(cp.get("java.lang.String"),"prop",ctClass);
        field.setModifiers(Modifier.PRIVATE);
        ctClass.addMethod(CtNewMethod.setter("setProp",field));
        ctClass.addMethod(CtNewMethod.getter("getMethod",field));

        ctClass.addField(field,CtField.Initializer.constant("myName"));

        //创建构造方法，指定了构造方法的参数类型和构造方法所属的类
        CtConstructor constructor = new CtConstructor(new CtClass[]{},ctClass);
        //方法体
        body = new StringBuilder();
        body.append("{\n prop = \"youName \";\n}");
        constructor.setBody(body.toString());

        ctClass.addConstructor(constructor);

       // 创建execute （）方法，指定了方法返回佳、方法名称、方法参数f1J 表以及方法所属的类
        CtMethod method = new CtMethod(CtClass.voidType,"execute",new CtClass[]{},ctClass);
        method.setModifiers(Modifier.PUBLIC);
        body = new StringBuilder();
        body.append((" {\n System.out.println(\" execute();\" + this.prop ) ;"));
        body.append("\n}");
        method.setBody(body.toString());

        ctClass.addMethod(method);
        ctClass.writeFile();

        Class<?> c = ctClass.toClass();
        Object obj = c.newInstance();
        Method method1 = obj.getClass().getMethod("execute",new Class[]{});
        method1.invoke(obj,new Object[]{});
    }
}
