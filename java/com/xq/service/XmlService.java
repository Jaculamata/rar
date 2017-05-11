package com.xq.service;

import com.xq.model.Xml;

import java.util.List;

public interface XmlService {
    Xml selectXml(Xml xml);
    String UploadXml(Xml xml);
    String UploadStandard(Xml xml);
    String deleteXml(Xml xml);
    List<Xml> getAllXml(Xml xml);
    List<Xml> getXmlFromModel(Xml xml);

}
