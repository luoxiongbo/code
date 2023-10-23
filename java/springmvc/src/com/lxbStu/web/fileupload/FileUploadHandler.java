package com.lxbStu.web.fileupload;
//-*- code = utf-8 -*-
//@Time : 2023-09-24 15:45:30
//@Authors : 罗雄波
//@File : FileUploadHandler.java
//@Software : IntelliJ IDEA

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

@Controller
public class FileUploadHandler {
    // 搞懂每一个地方, 不留余地
    @RequestMapping("/fileUpload")
    public String fileUpload(@RequestParam(value = "file") MultipartFile file, HttpServletRequest request) throws IOException {

        String filename = file.getOriginalFilename();
        System.out.println("上传文件名 : " + filename);
        // 通过上下文来获取资源
        String path = request.getServletContext().getRealPath("/img/" + filename);
        // 可以是 inputStream 也可以是 path
        File file1 = new File(path);
        file.transferTo(file1);
        return "success";
    }
}
