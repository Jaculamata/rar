package com.xq.service.impl;

import com.xq.dao.ReportDao;
import com.xq.model.MenuItem;
import com.xq.model.Report;
import com.xq.model.Xml;
import com.xq.service.ReportService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Liuwei on 2017/4/17.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ReportServiceImpl implements ReportService {
    @Resource
    private ReportDao reportDao;

    //4.17(lw)获取测试报告内容------4.27修改
    public List<Report> getReport(int projectid, String xml_title) {
        Report report = new Report();
        report.setProjectid(projectid);
        report.setXml_title(xml_title+"%");
        return reportDao.getReport(report);
    }
public int updateResult(Report report)
{
    System.out.println("detail in dao to map"+report.getDetail());
    System.out.println("projectid in dao to map"+report.getProjectid());
    System.out.println("xmltitle in dao to map"+report.getXml_title());
    int res = reportDao.update(report);
    System.out.println("dao update res "+res);
    if (res > 0){
        return 1;
    }
    return 0;
}
    public int  selectResult(Report report)
    {
        System.out.println("select detail in dao to map"+report.getDetail());
        System.out.println("select projectid in dao to map"+report.getProjectid());
        System.out.println(" select xmltitle in dao to map"+report.getXml_title());
        System.out.println("Dao slect");
        int res=reportDao.select(report);
        System.out.println("res");
        if(res!=0){
            System.out.println("return 1");
            return 1;
        }
        System.out.println("return 0");
        return 0;

    }
    public int deleteResult(Report report)
    {
        int res = reportDao.delete(report);
        if (res > 0){
            return 1;
        }
        return 0;
    }
    //4.17(lw)查找节点的text
    public String selectMenuText(MenuItem item) {
        return reportDao.selectMenuText(item);
    }

    //4.17(lw)获取项目名
    public String selectProjectName(int projectid) {
        return reportDao.selectProjectName(projectid);
    }

    //4.18(ht)保存测试结果到数据库
    public int saveReport(Report report) {
        System.out.println("save detail in dao to map"+report.getDetail());
        System.out.println("save projectid in dao to map"+report.getProjectid());
        System.out.println("save xmltitle in dao to map"+report.getXml_title());
        int res = reportDao.save(report);
        if (res > 0){
            return 1;
        }
        return 0;
    }
    //4.24(lw)查询所测安全等级脚本总数------4.27修改
    public int selectScriptNum(int projectid, String fj){
        MenuItem menuItem = new MenuItem();
        menuItem.setProjectid(projectid);
        menuItem.setFj(fj+"%");
        menuItem.setType("file");
        return reportDao.selectScriptNum(menuItem);
    }
    //4.24(lw)查询当前安全等级已测脚本数量------4.27修改
    public int selectTestedNum(int projectid, String xml_title){
        Report report = new Report();
        report.setProjectid(projectid);
        report.setXml_title(xml_title+"%");
        return reportDao.selectTestedNum(report);
    }

    //4.24(lw)查询当前安全等级测试结果为某值的数量------4.27修改
    public int selectResultNum(int projectid, String xml_title, String result){
        Report report = new Report();
        report.setProjectid(projectid);
        report.setXml_title(xml_title+"%");
        report.setResult(result);
        return reportDao.selectResultNum(report);
    }

    //4.27(lw)查询menu表中安全功能子目录
    public List<MenuItem> selectSecurityDir(int projectid, String fj) {
        MenuItem menuItem = new MenuItem();
        menuItem.setProjectid(projectid);
        menuItem.setFj(fj);
        return reportDao.selectSecurityDir(menuItem);
    }

    //4.27(lw)查询脚本内容
    public Xml selectXmlContent(int projectid, String xml_title) {
        Xml xml = new Xml();
        xml.setProjectid(projectid);
        xml.setTitle(xml_title);

        List<Xml> xmlList = reportDao.selectXmlContent(xml);
        if (xmlList.size() > 0) {
            return xmlList.get(0);
        }
        return null;
    }
    //5.9 查询某种类型脚本数量
    public  int selectScriptTypeNum(int projectid,String type){
        int result=0;
        System.out.println("sctn"+" projectid "+projectid+" type: "+type);
        Xml xml=new Xml();
        xml.setProjectid(projectid);
        xml.setType(type);
        result=reportDao.selectScriptTypeNum(xml);
        System.out.println("result"+result);
        return result;
    }

}
