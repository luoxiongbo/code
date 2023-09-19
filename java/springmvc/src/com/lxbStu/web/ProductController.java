package com.lxbStu.web;
//-*- code = utf-8 -*-
//@Time : 2023-09-18 09:28:28
//@Authors : 罗雄波
//@File : ProductController.java
//@Software : IntelliJ IDEA

import com.lxbStu.entity.Product;
import com.lxbStu.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ProductController {
    @Autowired
    private ProductService productService;

    @RequestMapping("/getProductList")
    public ModelAndView getProductList() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("productList");
        List<Product> productList = productService.getProductList();
        modelAndView.addObject(productList);
        return modelAndView;
    }

    @RequestMapping("/getProduct")
    public String getProduct(Integer productId, Model model) {
        Product productById = productService.getProductById(productId);
        model.addAttribute("product", productById);
        return "product";
    }
}
