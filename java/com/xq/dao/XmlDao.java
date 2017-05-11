package com.xq.dao;

import com.xq.model.Xml;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface XmlDao {
    List<Xml> selectXml(Xml xml);

    int insertXml(Xml xml);

    int updateXml(Xml xml);

    int deleteXml(Xml xml);

    int insertStandard(Xml xml);

    int updateStandard(Xml xml);

    List<Xml> getAllXml(Xml xml);
    int destroy1(String id);
    //4.23新增
    List<Xml> getXmlFromModel(Xml xml);
}
