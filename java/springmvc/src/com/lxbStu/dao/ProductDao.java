package com.lxbStu.dao;
//-*- code = utf-8 -*-
//@Time : 2023-09-18 09:36:54
//@Authors : 罗雄波
//@File : ProductDao.java
//@Software : IntelliJ IDEA

import com.lxbStu.entity.Product;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.*;

@Repository
public class ProductDao {
    private static Map<Integer, Product> products = null;
    static {
        products = new HashMap<Integer, Product>();
        Product product = new Product();
        product.setProductId(1);
        product.setProductName("茅台");
        product.setPrice(new BigDecimal(9999));
        Product product1 = new Product();
        product1.setProductId(2);
        product1.setProductName("五粮液");
        product1.setPrice(new BigDecimal(8888));
        Product product2 = new Product();
        product2.setProductId(3);
        product2.setProductName("信阳毛尖");
        product2.setPrice(new BigDecimal(7777));
        Product product3 = new Product();
        product3.setProductId(4);
        product3.setProductName("深州大蜜桃");
        product3.setPrice(new BigDecimal(6666));
        products.put(product.getProductId(), product);
        products.put(product1.getProductId(), product1);
        products.put(product2.getProductId(), product2);
        products.put(product3.getProductId(), product3);
    }
    public List getProductList() {
        List productList = new ArrayList();
        Set<Integer> keys = products.keySet();
        for (Integer key : keys) {
            Product product = products.get(key);
            productList.add(product);
        }
        return productList;
    }
    public Product getProductById(Integer productId) {
        return products.get(productId);
    }
}
