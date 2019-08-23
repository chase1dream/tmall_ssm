package com.tmall.controller;

import com.tmall.pojo.Product;
import com.tmall.pojo.PropertyValue;
import com.tmall.service.ProductService;
import com.tmall.service.PropertyValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author tanquan
 * @description <p>
 * </p>
 * @date 2019/7/14 下午 8:08
 */
@Controller
@RequestMapping("")
public class PropertyValueController {

    @Autowired
    PropertyValueService propertyValueService;
    @Autowired
    ProductService productService;

    // 产品属性值编辑
    @RequestMapping("admin_propertyValue_edit")
    public String edit(Model model, int pid) {
        // 根据产品id拿到需要编辑属性值的产品对象
        Product product = productService.get(pid);
        // 对产品进行初始化
        propertyValueService.init(product);
        // 再根据产品id拿到属性值集合
        List<PropertyValue> propertyValueList = propertyValueService.list(product.getId());
        // 存到model中
        model.addAttribute("product", product);
        model.addAttribute("propertyValueList",propertyValueList);
        return "admin/editPropertyValue";
    }

    // 产品属性更新
    @RequestMapping("admin_propertyValue_update")
    @ResponseBody
    public String update(PropertyValue propertyValue) {
        propertyValueService.update(propertyValue);
        return "success";
    }
}
