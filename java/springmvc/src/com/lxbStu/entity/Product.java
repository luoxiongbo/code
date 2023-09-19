package com.lxbStu.entity;
//-*- code = utf-8 -*-
//@Time : 2023-09-18 09:25:35
//@Authors : 罗雄波
//@File : Product.java
//@Software : IntelliJ IDEA

import java.math.BigDecimal;

public class Product {
    private Integer productId;
    private String productName;
    private BigDecimal price;
    private Integer storage;

    public Product() {
    }

    public Product(Integer productId, String productName, BigDecimal price, Integer storage) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.storage = storage;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStorage() {
        return storage;
    }

    public void setStorage(Integer storage) {
        this.storage = storage;
    }
}
