package com.lxbStu.web.json;
//-*- code = utf-8 -*-
//@Time : 2023-09-22 16:50:46
//@Authors : 罗雄波
//@File : JsonHandler.java
//@Software : IntelliJ IDEA

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Controller
// 该注解是返回JSON数据的注解, 如果要返回JSON的话就要加这个注解
@ResponseBody
public class JsonHandler {
    @RequestMapping(value = "/getJson")
    //指定返回的数据格式 json ,靠这个@ResponseBody
    public List<Dog> getJson() {
        //创建一只狗
        ArrayList<Dog> dogs = new ArrayList<>();
        dogs.add(new Dog("大黄狗", "北京八达岭"));
        dogs.add(new Dog("田园犬", "中华人民国"));
        return dogs;
    }

    @PostMapping("/save2")
    public User save2(@RequestBody User user) {
        //将前台传过来的数据 以 json 的格式相应回浏览器
        System.out.println("user~= " + user);
        //HttpMessageConverter // 是实现JSON数据转换的核心步骤
        return user;
    }

    //响应用户下载文件的请求
    @RequestMapping(value = "/downFile")
    public ResponseEntity<byte[]> downFile(HttpSession session) throws Exception {
        //先获取到你要下载的文件 InputStream
        /**
         *
         * inputStream 变量将包含指向 "/img/aaa.jpg" 文件的输入流，你可以使用它来读取文件的内容，
         * 例如将文件内容发送给客户端，进行处理或者保存到其他位置。
         * 这种操作通常用于从 Web应用程序的资源目录中获取静态文件，如图像、样式表、JavaScript文件等
         */
        InputStream is = session.getServletContext().getResourceAsStream("/img/aaa.jpg");
        //开辟一个存放文件内容 byte 数组,因为 byte 是字节，因此可以返回二进制的文件【图片，视频。。】
        byte[] bytes = new byte[is.available()];
        is.read(bytes);
        //构建返回的 ResponseEntity<byte[]>
        HttpStatus statuts = HttpStatus.OK;//返回成功
        HttpHeaders headers = new HttpHeaders();//根据 http 协议这个就是告诉浏览器，这是返回的一个文件,浏览器就弹出小窗口
        headers.add("Content-Disposition", "attachment;filename=shunping.jpg");

        ResponseEntity<byte[]> responseEntity = new ResponseEntity<byte[]>(bytes, headers, statuts);
        return responseEntity;
    }

}