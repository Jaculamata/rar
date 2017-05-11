package com.xq.service.impl;

import com.xq.dao.XmlDao;
import com.xq.model.Xml;
import com.xq.service.XmlService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by lu on 2017/3/13.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class XmlServiceImpl implements XmlService {
    @Resource
    private XmlDao xmlDao;

    public Xml selectXml(Xml xml) {
        List<Xml> list = xmlDao.selectXml(xml);
        if (list.size()==0) return null;
        return list.get(0);
    }
    //保存脚本内容到数据库
    public String UploadXml(Xml xml) {
        Xml rs = selectXml(xml);
        int res = 0;
//        如果数据库中没有该记录则插入新的记录
        if (null == rs){
           res = xmlDao.insertXml(xml);
        }else {
//        如果有该标题的记录，则更新
            res = xmlDao.updateXml(xml);
        }
        //List<Xml> list = xmlDao.UploadXml(xml);
        //int res=xmlDao.UploadXml(xml);

        if (res>0) {
            return "success";
        }
        return "fail";
    }

    //保存标准规范内容到数据库
    public String UploadStandard(Xml xml){
        Xml rs = selectXml(xml);
        int res = 0;
//        如果数据库中没有该记录则插入新的记录
        if (null == rs){
            res = xmlDao.insertStandard(xml);
        }else {
//        如果有该标题的记录，则更新
            res = xmlDao.updateStandard(xml);
        }
        //List<Xml> list = xmlDao.UploadXml(xml);
        //int res=xmlDao.UploadXml(xml);

        if (res>0) {
            return "success";
        }
        return "fail";
    }

    public String deleteXml(Xml xml) {
        int res = xmlDao.deleteXml(xml);
        if (res>0){
            return "success";
        }
        return "fail";
    }

    public List<Xml> getAllXml(Xml xml) {
        return xmlDao.getAllXml(xml);
    }

    //4.23优化新建项目后，若xml内容为空则从模板项目中查询xml内容
    public List<Xml> getXmlFromModel(Xml xml) {
        return xmlDao.getXmlFromModel(xml);
    }
}
