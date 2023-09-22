package com.lxb.springmvc.context;
//-*- code = utf-8 -*-
//@Time : 2023-09-21 16:18:39
//@Authors : 罗雄波
//@File : LxbWebApplicationContext.java
//@Software : IntelliJ IDEA

import com.lxb.springmvc.xml.XMLParser;
import com.lxb.springmvc.annotation.AutoWired;
import com.lxb.springmvc.annotation.Controller;
import com.lxb.springmvc.annotation.Service;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LxbWebApplicationContext {
    private ArrayList<String> classFullPath = new ArrayList<String>();
    private ConcurrentHashMap<String, Object> ioc = new ConcurrentHashMap<String, Object>();

    public void init(String configLocation) {
        String packageInfo = XMLParser.xmlPackageInfo(configLocation);
        // 可能有多个包
        String[] split = packageInfo.split(",");
        if (split.length > 0) {
            for (String pack : split) {
                scanPackage(pack);
            }
        }
        System.out.println("扫描完的类路径是 : " + classFullPath);
        executeInstance();
        System.out.println("实例完的bean : " + ioc);
        executeWired();
    }

    /**
     * 传进来的是com.lxb.controller和com.lxb.service以及com.lxb.service.impl
     * @param pack
     */
    public void scanPackage(String pack) {
        URL resource = LxbWebApplicationContext.class.getClassLoader().getResource("/" + pack.replaceAll("\\.", "/"));
        String path = resource.getFile();
        File fileDirectory = new File(path);
        for (File file : fileDirectory.listFiles()) {
            if (file.isDirectory()) {
                // 如果是文件夹, 递归读取
                scanPackage(pack + "." + file.getName());
            } else {
                // pack 是包名
                classFullPath.add(pack + "." + file.getName().replaceAll(".class", ""));
            }
        }
    }

    public void executeInstance() {
        if (classFullPath.size() == 0) {
            return;
        }
        try {
            for (String path : classFullPath) {
                Class<?> clazz = Class.forName(path);
                if (clazz.isAnnotationPresent(Controller.class)) {
                    //String name = clazz.getName();//这个是得到加路径的名字, 不对
                    String name = clazz.getSimpleName();
                    String beanName = name.substring(0, 1).toLowerCase() + name.substring(1);
                    ioc.put(beanName, clazz.newInstance());
                } else if (clazz.isAnnotationPresent(Service.class)) {
                    Service service = clazz.getAnnotation(Service.class);
                    if (service.value() == null || "".equals(service.value())) {
                        Class<?>[] interfaces = clazz.getInterfaces();
                        for (Class<?> anInterface : interfaces) {
                            String interfaceSimpleName = anInterface.getSimpleName();
                            String beanName = interfaceSimpleName.substring(0, 1).toLowerCase() + interfaceSimpleName.substring(1);
                            ioc.put(beanName, clazz.newInstance());
                        }
                    } else {
                        ioc.put(service.value(), clazz.newInstance());
                    }

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void executeWired() {
        if(ioc == null) {
            throw new RuntimeException("spring为空, 没有可装配的");
        }

        for (Map.Entry<String, Object> entry : ioc.entrySet()) {
            Object value = entry.getValue();
            Class<?> clazz = value.getClass();
            Field[] declaredFields = clazz.getDeclaredFields();
            for (Field field : declaredFields) {

                if(field.isAnnotationPresent(AutoWired.class)) {
                    Class<?> type = field.getType();
                    String simpleName = type.getSimpleName();
                    String nameBy = simpleName.substring(0, 1).toLowerCase() + simpleName.substring(1);
                    Object bean = ioc.get(nameBy);
                    field.setAccessible(true);
                    try {
                        field.set(value, bean);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    public ConcurrentHashMap<String, Object> getIoc() {
        return ioc;
    }
}
