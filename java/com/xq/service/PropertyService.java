package com.xq.service;

import com.xq.model.Property;

import java.util.List;

public interface PropertyService {
    //4.10lu,查找property默认值
    List<Property> selectProperty();
}
