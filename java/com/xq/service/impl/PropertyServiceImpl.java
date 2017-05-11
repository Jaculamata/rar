package com.xq.service.impl;

import com.xq.dao.PropertyDao;
import com.xq.model.Property;
import com.xq.service.PropertyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by lu on 2017/4/10.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class PropertyServiceImpl implements PropertyService{
    @Resource
    private PropertyDao propertyDao;

    public List<Property> selectProperty() {
        return propertyDao.selectProperty();
    }
}
