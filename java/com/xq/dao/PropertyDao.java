package com.xq.dao;

import com.xq.model.Property;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by lu on 2017/4/10.
 */
@Repository
public interface PropertyDao {
    List<Property> selectProperty();
}
