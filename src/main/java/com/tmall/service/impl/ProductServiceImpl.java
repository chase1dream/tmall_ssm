package com.tmall.service.impl;

import com.tmall.mapper.ProductMapper;
import com.tmall.pojo.Category;
import com.tmall.pojo.Product;
import com.tmall.pojo.ProductExample;
import com.tmall.pojo.ProductImage;
import com.tmall.service.CategoryService;
import com.tmall.service.ProductImageService;
import com.tmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author tanquan
 * @description <p>
 * </p>
 * @date 2019/7/5 10:24
 */
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductMapper productMapper;
    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductImageService productImageService;

    @Override
    public void add(Product product) {
        productMapper.insert(product);
    }

    @Override
    public void delete(int id) {
        productMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Product product) {
        productMapper.updateByPrimaryKey(product);
    }

    @Override
    public Product get(int id) {
        Product product = productMapper.selectByPrimaryKey(id);
        setCategory(product);
        return product;
    }

    @Override
    public List list(int cid) {
        ProductExample example = new ProductExample();
        example.createCriteria().andCidEqualTo(cid);
        example.setOrderByClause("id desc");
        List<Product> list = productMapper.selectByExample(example);
        setCategory(list);
        return list;
    }

    // 增加方法 setFirstProductImage(Product p)：
    // 根据pid和图片类型查询出所有的单个图片，然后把第一个取出来放在firstProductImage上
    @Override
    public void setFirstProductImage(Product product) {
        // 拿到所有图片集合
        List<ProductImage> imageList = productImageService.list(product.getId(), ProductImageService.type_single);
        // 对集合进行非空判断，获取到第一张图片，并设置到product对象中
        if (!imageList.isEmpty()) {
            ProductImage productImage = imageList.get(0);
            product.setFirstProductImage(productImage);
        }
    }



    public void setCategory(List<Product> ps) {
        for (Product p : ps)
            setCategory(p);
    }

    public void setCategory(Product p) {
        int cid = p.getCid();
        Category c = categoryService.get(cid);
        p.setCategory(c);
    }
}
