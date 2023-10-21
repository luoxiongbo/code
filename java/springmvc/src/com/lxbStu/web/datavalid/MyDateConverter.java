package com.lxbStu.web.datavalid;
//-*- code = utf-8 -*-
//@Time : 2023-09-22 12:17:05
//@Authors : 罗雄波
//@File : MyDateConverter.java
//@Software : IntelliJ IDEA

import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyDateConverter implements Converter<String, Date> {
    private String datePatten = "yyyy-MM-dd";

    @Override
    public Date convert(String source) {
        System.out.println("前端页面传递过来的时间为：" + source);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(datePatten);
        try {
            return simpleDateFormat.parse(source);
        } catch (ParseException e) {
            throw new IllegalArgumentException("无效的日期格式，请使用正确的日期格式" + datePatten);
        }
    }
}
