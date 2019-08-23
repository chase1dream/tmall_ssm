package com.tmall.service;

import com.tmall.pojo.Product;

import java.util.List;

/**
 * @author tanquan
 * @description <p>
 * </p>
 * @date 2019/7/5 10:20
 */
public interface ProductService {
    void add(Product product);

    void delete(int id);

    void update(Product product);

    Product get(int id);

    List list(int cid);

    void setFirstProductImage(Product product);
}
