package com.lxbStu.spring.component;
//-*- code = utf-8 -*-
//@Time : 2023-09-16 15:30:59
//@Authors : 罗雄波
//@File : LxbBeanPostProcessor.java
//@Software : IntelliJ IDEA

import com.lxbStu.spring.annotation.Component;
import com.lxbStu.spring.aop.AspectContainer;
import com.lxbStu.spring.processe.BeanPostProcessor;
import com.lxbStu.spring.utils.SpringUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class LxbBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        System.out.println("postProcessBeforeInitialization 被调用 " + beanName + " bean= " + bean.getClass());
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        System.out.println("postProcessAfterInitialization 被调用 " + beanName + " bean= " + bean.getClass());

        ConcurrentHashMap<Class, String[]> aspectJ = AspectContainer.getAspectJ();
        String[] strings = aspectJ.get(bean.getClass());
        if(strings != null) {

            Object proxyInstance = Proxy.newProxyInstance(LxbBeanPostProcessor.class.getClassLoader(), bean.getClass().getInterfaces(), new InvocationHandler() {
                        @Override
                        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                            Object result = null;
                            if (strings[0].equals(method.getName())) {
                                Class<?> clazz = Class.forName(strings[1]);
                                Method[] methods = clazz.getDeclaredMethods();
                                Method before = SpringUtils.FindMethodByName(methods, "showBeginLog");
                                before.invoke(clazz.newInstance());

                                result = method.invoke(bean, args);

                                Method after = SpringUtils.FindMethodByName(methods, "showSuccessEndLog");
                                after.invoke(clazz.newInstance());
                            } else {
                                result = method.invoke(proxy, args);
                            }
                            return result;
                        }
                    });
            return proxyInstance;
        }
        return bean;
    }
}
