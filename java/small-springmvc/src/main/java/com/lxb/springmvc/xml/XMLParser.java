package com.lxb.springmvc.xml;
//-*- code = utf-8 -*-
//@Time : 2023-09-21 15:51:14
//@Authors : 罗雄波
//@File : XMLParser.java
//@Software : IntelliJ IDEA

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;

public class XMLParser {
    public static String xmlPackageInfo(String xmlFile) {
        SAXReader reader = new SAXReader();
        // 注意这里的查看xml文件是根据类加载的资源加载出来的
        InputStream stream = XMLParser.class.getClassLoader().getResourceAsStream(xmlFile);
        try {
            Document document = reader.read(stream);
            Element rootElement = document.getRootElement();
            Element element = rootElement.element("component-scan");
            return element.attributeValue("base-package");
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
    }
}
