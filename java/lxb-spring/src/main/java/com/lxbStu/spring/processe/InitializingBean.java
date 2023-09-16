package com.lxbStu.spring.processe;

/**
 * 初始化bean的操作
 */
public interface InitializingBean {
    void afterPropertiesSet() throws Exception;
}
