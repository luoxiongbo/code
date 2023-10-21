package com.lxbStu.web.exception;
//-*- code = utf-8 -*-
//@Time : 2023-09-24 18:20:46
//@Authors : 罗雄波
//@File : AgeException.java
//@Software : IntelliJ IDEA

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(reason = "原因是 : 年龄不在正确范围内" , value = HttpStatus.BAD_REQUEST)
public class AgeException extends RuntimeException{

}
