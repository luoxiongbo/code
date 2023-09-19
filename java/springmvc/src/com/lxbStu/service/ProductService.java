package com.lxbStu.service;
//-*- code = utf-8 -*-
//@Time : 2023-09-18 09:31:44
//@Authors : 罗雄波
//@File : ProductService.java
//@Software : IntelliJ IDEA

import com.lxbStu.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> getProductList();

    Product getProductById(Integer productId);
}
