package com.lxbStu.web.datavalid;
//-*- code = utf-8 -*-
//@Time : 2023-09-22 12:30:48
//@Authors : 罗雄波
//@File : MyFloatConverter.java
//@Software : IntelliJ IDEA

import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyFloatConverter implements Converter<String, Float> {
    @Override
    public Float convert(String source) {
        System.out.println("前端页面传递过来的工资为 : " + source);
        if(source.contains(",")) {
            source = source.replace(",", "");
        }
        return Float.parseFloat(source);
    }
}
