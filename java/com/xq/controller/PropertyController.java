package com.xq.controller;

import com.xq.model.Property;
import com.xq.service.PropertyService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by lu on 2017/4/5.
 */
@Controller
public class PropertyController {
    @Resource
    private PropertyService propertyService;

    //查找PP
    @RequestMapping(value = "/selectProperty.do", produces = "application/json;charset=utf-8")
    @ResponseBody
    public List<Property> selectProperty(){
        List<Property> propertyList =  propertyService.selectProperty();
        return propertyList;
    }


}
