package com.lxbStu.service.impl;
//-*- code = utf-8 -*-
//@Time : 2023-09-18 09:32:17
//@Authors : 罗雄波
//@File : ProductServiceImpl.java
//@Software : IntelliJ IDEA

import com.lxbStu.dao.ProductDao;
import com.lxbStu.entity.Product;
import com.lxbStu.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("productService")
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;
    @Override
    public List<Product> getProductList() {
        return productDao.getProductList();
    }
    @Override
    public Product getProductById(Integer productId) {
        return productDao.getProductById(productId);
    }
}
