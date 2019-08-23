package com.tmall.service;

import com.tmall.pojo.Product;
import com.tmall.pojo.PropertyValue;

import java.util.List;

/**
 * @author tanquan
 * @description <p>
 * </p>
 * @date 2019/7/14 下午 7:35
 */
public interface PropertyValueService {
    void init(Product product);
    void update(PropertyValue propertyValue);
    PropertyValue get(int ptid, int pid);
    List<PropertyValue> list(int pid);
}
