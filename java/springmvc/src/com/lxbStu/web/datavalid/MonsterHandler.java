package com.lxbStu.web.datavalid;
//-*- code = utf-8 -*-
//@Time : 2023-09-22 10:45:27
//@Authors : 罗雄波
//@File : MonsterHandler.java
//@Software : IntelliJ IDEA

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.Map;

@Controller
@Scope(value = "prototype")
public class MonsterHandler {
    //显示添加 monster 的界面
    @RequestMapping(value = "/addMonsterUI")
    public String addMonsterUI(Map<String, Object> map) {
        /**老韩解读:
         1. 这里的表单，我们使用 springMVC 的标签来完成, ok
         2. SpringMVC 表单标签在显示之前必须在 request 中有一个 bean,
         该 bean的属性和表单标签的字段要对应!
         request 中的 key 为: form 标签的 modelAttribute 属性值，
         比如这里的 monster
         3. SpringMVC 的 form:form 标签的 action 属性值中的 / 不代表 WEB 应用的根目录.
         4. <form:form action="monster" method="POST" modelAttribute="monster">

         // 这 里 需 要 给 request 增 加 一 个 monster ，
         因 为 jsp 页 面 的 modelAttribute="monster"需要

         //这时是 springMVC 的内部的检测机制 即使是一个空的也需要，否则报错.
         */

        //这里就是往request里面放了一个空的monster
        map.put("monster", new Monster());
        //这里是视图解析器给前后缀没必要大惊小怪
        return "datavalid/monster_addUI";
    }

    //处理添加，更加请求的方法来确定
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    // 记得在目标方法参数的前面加注解 @Valid
    public String save(@Valid Monster monster, Errors errors, Map<String, Object> map) {
        System.out.println("email= " + monster.getEmail());
        System.out.println("monster= " + monster);
        if (errors.hasErrors()) {
            System.out.println("验证出错!");
            for (ObjectError error : errors.getAllErrors()) {
                System.out.println(error);
            }
            //返回添加界面
            return "/datavalid/monster_addUI";
            //return "forward:/addMonsterUI";
        }
        return "datavalid/success";
    }
}
