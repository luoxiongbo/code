package com.lxbStu.spring.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD})
public @interface Autowired {
//一个属性 required ，这里我们就不讲了，也比较简单, 有兴趣同学们作为课后加入
//String required() default "true";
}
