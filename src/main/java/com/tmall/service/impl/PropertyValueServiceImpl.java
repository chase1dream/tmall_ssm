package com.tmall.service.impl;

import com.tmall.mapper.PropertyValueMapper;
import com.tmall.pojo.Product;
import com.tmall.pojo.Property;
import com.tmall.pojo.PropertyValue;
import com.tmall.pojo.PropertyValueExample;
import com.tmall.service.PropertyService;
import com.tmall.service.PropertyValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author tanquan
 * @description <p>
 * </p>
 * @date 2019/7/14 下午 7:39
 */
@Service
public class PropertyValueServiceImpl implements PropertyValueService {
    @Autowired
    PropertyValueMapper propertyValueMapper;
    @Autowired
    PropertyService propertyService;

    // 该方法用来初始化PropertyValue
    @Override
    public void init(Product product) {
        //  根据产品获取分类id，然后获取这个分类下的所有属性集合
        List<Property> propertyList = propertyService.list(product.getCid());
        // 用属性id和产品id去查询，看看这个属性和这个产品，是否已经存在属性值
        for (Property pt : propertyList) {
            PropertyValue pv = get(pt.getId(), product.getId());
            // 如果不存在，那么就创建一个属性值，并设置其属性和产品，接着插入到数据库中
            if (pv == null) {
                pv = new PropertyValue();
                pv.setPid(product.getId());
                pv.setPtid(pt.getId());
                propertyValueMapper.insert(pv);
            }
        }
    }

    // 更新属性值
    @Override
    public void update(PropertyValue propertyValue) {
        propertyValueMapper.updateByPrimaryKeySelective(propertyValue);
    }

    // 根据属性id和产品id获取PropertyValue对象
    @Override
    public PropertyValue get(int ptid, int pid) {
        PropertyValueExample example = new PropertyValueExample();
        example.createCriteria().andPidEqualTo(ptid).andPidEqualTo(pid);
        List<PropertyValue> propertyValueList = propertyValueMapper.selectByExample(example);
        if (propertyValueList.isEmpty()) {
            return null;
        }
        return propertyValueList.get(0);
    }

    // 根据产品id获取所有的属性值
    @Override
    public List<PropertyValue> list(int pid) {
        PropertyValueExample example = new PropertyValueExample();
        example.createCriteria().andPidEqualTo(pid);
        List<PropertyValue> propertyValueList = propertyValueMapper.selectByExample(example);
        for (PropertyValue pv : propertyValueList) {
            Property property = propertyService.get(pv.getPid());
            pv.setProperty(property);
        }
        return propertyValueList;
    }
}
