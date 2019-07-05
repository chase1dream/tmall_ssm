package com.tmall.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tmall.pojo.Category;
import com.tmall.pojo.Page;
import com.tmall.pojo.Product;
import com.tmall.pojo.Property;
import com.tmall.service.CategoryService;
import com.tmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.List;

/**
 * @author tanquan
 * @description <p>
 * </p>
 * @date 2019/7/5 10:35
 */
@Controller
@RequestMapping("")
public class ProductController {
    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductService productService;

    @RequestMapping("admin_product_add")
    public String add(Model model, Product product) {
        product.setCreateDate(new Date());
        productService.add(product);
        return "redirect:admin_product_list?cid=" + product.getCid();
    }

    @RequestMapping("admin_product_delete")
    public String delete(int id) {
        Product product = productService.get(id);
        productService.delete(id);
        return "redirect:admin_product_list?cid=" + product.getCid();
    }

    @RequestMapping("admin_product_edit")
    public String edit(Model model, int id) {
        Product product = productService.get(id);
        Category c = categoryService.get(product.getCid());
        product.setCategory(c);
        model.addAttribute("p", product);
        return "admin/editProduct";
    }

    @RequestMapping("admin_product_update")
    public String update(Product product) {
        productService.update(product);
        return "redirect:admin_product_list?cid=" + product.getCid();
    }

    @RequestMapping("admin_product_list")
    public String list(Integer cid, Model model, Page page) {
        Category c = categoryService.get(cid);

        PageHelper.offsetPage(page.getStart(), page.getCount());
        List<Property> ps = productService.list(cid);

        int total = (int) new PageInfo<>(ps).getTotal();
        page.setTotal(total);
        page.setParam("&cid=" + c.getId());

        model.addAttribute("ps", ps);
        model.addAttribute("c", c);
        model.addAttribute("page", page);

        return "admin/listProduct";
    }
}
