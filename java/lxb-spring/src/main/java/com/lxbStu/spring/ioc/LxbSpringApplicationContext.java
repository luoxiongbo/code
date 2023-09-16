package com.lxbStu.spring.ioc;
//-*- code = utf-8 -*-
//@Time : 2023-09-16 09:45:31
//@Authors : 罗雄波
//@File : LxbSpringApplicationContext.java
//@Software : IntelliJ IDEA

import com.lxbStu.spring.annotation.*;
import com.lxbStu.spring.aop.AspectContainer;
import com.lxbStu.spring.processe.BeanPostProcessor;
import com.lxbStu.spring.processe.InitializingBean;
import com.sun.xml.internal.bind.v2.runtime.output.StAXExStreamWriterOutput;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class LxbSpringApplicationContext {
    // 配置类
    private Class config;
    // 存放单例bean的map
    private final ConcurrentHashMap<String, Object> singletonMap = new ConcurrentHashMap<>();
    // 存放bean元数据的map
    private final ConcurrentHashMap<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    private final List<BeanPostProcessor> beanPostProcessorList = new ArrayList<>();
    // 返回单例map
    public ConcurrentHashMap<String, Object> getIoc() {
        return singletonMap;
    }

    // 返回bean根据 id
    public Object getBean(String bean) {
        if(beanDefinitionMap.containsKey(bean)) {

            BeanDefinition beanDefinition = beanDefinitionMap.get(bean);
            String scope = beanDefinition.getScope();
            if("singleton".equals(scope)) {
                return singletonMap.get(bean);
            } else {
                return createBean(bean, beanDefinition);
            }
        } else {
            throw new NullPointerException("没有这个bean");
        }
    }

    // 将从包中扫描类的步骤封装起来, 封装到这个方法中
    public void LoaderResourceByConfig(Class ClassConfig) throws RuntimeException {
        this.config = ClassConfig;
        ComponentScan component = (ComponentScan) config.getDeclaredAnnotation(ComponentScan.class);
        String path = component.value();
        path = path.replace(".", "/");
        System.out.println("扫描的路径是 : " + path);
        ClassLoader classLoader = LxbSpringApplicationContext.class.getClassLoader();
        URL resource = classLoader.getResource(path);
        //System.out.println(resource);
        File ComponentFile = new File(resource.getFile());
        if (ComponentFile.isDirectory()) {
            File[] files = ComponentFile.listFiles();
            for (File file : files) {
                System.out.println("=====================================================");
                String classPath = file.getAbsolutePath();
                System.out.println("类的绝对路径是 : " + classPath);

                if (classPath.endsWith(".class")) {
                    String className = classPath.substring(classPath.lastIndexOf("\\") + 1, classPath.indexOf(".class"));
                    String classFullPath = path.replace("/", ".") + "." + className;
                    System.out.println("类的全路径是 :" + classFullPath + ", 类名是 :" + className);

                    try {
                        Class<?> clazz = Class.forName(classFullPath);

                        if (clazz.isAnnotationPresent(Component.class)) {
                            Component beanId = clazz.getDeclaredAnnotation(Component.class);
                            String id = beanId.value();
                            if (id == null || "".equals(id)) {
                                id = className.substring(0, 1).toLowerCase() + className.substring(1);
                            }
                            //System.out.println(value);
                            if(clazz.isAnnotationPresent(Aspect.class)) {
                                Method[] methods = clazz.getDeclaredMethods();
                                for (Method method : methods) {
                                    String classAndMethod = null;
                                    if(method.getDeclaredAnnotation(Before.class) != null) {
                                        Before before = method.getDeclaredAnnotation(Before.class);
                                        classAndMethod = before.value();
                                    }

                                    if(method.getDeclaredAnnotation(After.class) != null) {
                                        After after = method.getDeclaredAnnotation(After.class);
                                        classAndMethod = after.value();
                                    }
                                    if(classAndMethod != null) {
                                        // 我只需要把aspect方法里的注解的信息进行分析就可以了, 至于aspect只需要将它的对象的路径放在字符串数组中即可
                                        String[] split = classAndMethod.split(" ");
                                        Class<?> key = Class.forName(split[1]);
                                        String methodName = split[2];
                                        String[] value = new String[] {methodName, classFullPath};
                                        // 封装为 = 类路径 + 方法
                                        AspectContainer.add(key, value);
                                        break;
                                    }
                                }
                                System.out.println("是一个 AspectJ = " + clazz);
                                continue;
                            }

                            // 这行很重要
                            if(BeanPostProcessor.class.isAssignableFrom(clazz)) {
                                BeanPostProcessor beanPostProcessor = (BeanPostProcessor) clazz.newInstance();
                                beanPostProcessorList.add(beanPostProcessor);
                                System.out.println("是一个 BeanPostProcessor = " + clazz);
                                continue;
                            }

                            BeanDefinition beanDefinition = new BeanDefinition();
                            beanDefinition.setClazz(clazz);
                            if (clazz.isAnnotationPresent(Scope.class)) {
                                Scope scope = clazz.getDeclaredAnnotation(Scope.class);
                                beanDefinition.setScope(scope.value());

                            } else {
                                beanDefinition.setScope("singleton");
                            }
                            beanDefinitionMap.put(id, beanDefinition);
                            System.out.println("是一个 bean = " + clazz);
                        } else {
                            System.out.println("不是一个 bean = " + clazz);
                        }
                    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

        }

    }

    // 构造器, 参数是配置类
    public LxbSpringApplicationContext(Class ClassConfig) {
        LoaderResourceByConfig(ClassConfig);
        System.out.println("=====================================================");
        System.out.println(beanDefinitionMap);

        Enumeration<String> keys = beanDefinitionMap.keys();
        while(keys.hasMoreElements()) {
            String beanId = keys.nextElement();
            BeanDefinition beanDefinition = beanDefinitionMap.get(beanId);
            String scope = beanDefinition.getScope();
            if("singleton".equals(scope)) {
                Object instance = createBean(beanId, beanDefinition);
                singletonMap.put(beanId, instance);
            }
        }
    }

    public Object createBean(String beanName, BeanDefinition beanDefinition) {
        Class clazz = beanDefinition.getClazz();
        Object instance = null;
        try {
            instance = clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            Object bean = getBean(declaredField.getName());
            try {
                declaredField.setAccessible(true);
                declaredField.set(instance, bean);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("===============创建好了实例 Set 方法执行完===============");


        for (BeanPostProcessor beanPostProcessor : beanPostProcessorList) {
            Object temp = beanPostProcessor.postProcessBeforeInitialization(instance, beanName);
            if(temp != null) {
                instance = temp;
            }
        }
        if(instance instanceof InitializingBean) {
            try {
                ((InitializingBean) instance).afterPropertiesSet();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        for(BeanPostProcessor beanPostProcessor : beanPostProcessorList) {
            Object temp = beanPostProcessor.postProcessAfterInitialization(instance, beanName);
            if(temp != null) {
                instance = temp;
            }
        }
        return instance;
    }
}
