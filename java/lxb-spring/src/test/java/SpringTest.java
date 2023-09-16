//-*- code = utf-8 -*-
//@Time : 2023-09-16 10:02:46
//@Authors : 罗雄波
//@File : SpringTest.java
//@Software : IntelliJ IDEA

import com.lxbStu.spring.aop.AspectContainer;
import com.lxbStu.spring.component.SmartAnimalable;
import com.lxbStu.spring.ioc.LxbSpringApplicationContext;
import com.lxbStu.spring.ioc.LxbSpringConfig;
import org.junit.Test;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;

public class SpringTest {
    @Test
    public void ConfigTest() {

        LxbSpringApplicationContext ioc = new LxbSpringApplicationContext(LxbSpringConfig.class);


        System.out.println("=====================================================");

        //ConcurrentHashMap<String, Object> beans = ioc.getIoc();
        //Enumeration<String> keys = beans.keys();
        //while (keys.hasMoreElements()) {
        //    String id = keys.nextElement();
        //    System.out.println(beans.get(id));
        //}

        //MonsterService monsterService = (MonsterService) ioc.getBean("monsterService");
        //monsterService.m1();

        //ConcurrentHashMap<Class, String[]> aspectJ = AspectContainer.getAspectJ();
        //Enumeration<Class> keys = aspectJ.keys();
        //while(keys.hasMoreElements()) {
        //    Class aClass = keys.nextElement();
        //    String[] strings = aspectJ.get(aClass);
        //    System.out.println(Arrays.toString(strings));
        //}

        SmartAnimalable bean = (SmartAnimalable) ioc.getBean("smartDog");
        float sum = bean.getSum(1, 9);

    }
}
