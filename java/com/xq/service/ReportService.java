package com.xq.service;

import com.xq.model.MenuItem;
import com.xq.model.Report;
import com.xq.model.Xml;

import java.util.List;

/**
 * Created by Liuwei on 2017/4/17.
 */
public interface ReportService {
    //4.17(lw)获取测试报告内容------4.27修改
    List<Report> getReport(int projectid, String xml_title);
    //4.17(lw)查找节点的text
    String selectMenuText(MenuItem item);
    //4.17(lw)获取项目名
    String selectProjectName(int projectid);
    //4.18(ht)保存测试结果到数据库
    int saveReport(Report report);
    //4.24(lw)查询所测安全等级脚本总数------4.27修改
    int selectScriptNum(int projectid, String fj);
    //4.24(lw)查询当前安全等级已测脚本数量------4.27修改
    int selectTestedNum(int projectid, String xml_title);
    //4.24(lw)查询当前安全等级测试结果为某值的数量------4.27修改
    int selectResultNum(int projectid, String xml_title, String result);
    //4.27(lw)查询menu表中安全功能子目录
    List<MenuItem> selectSecurityDir(int projectid, String fj);
    //4.27(lw)查询脚本内容
    Xml selectXmlContent(int projectid, String xml_title);
    //5.9查询某一类型的脚本的数量
    int selectScriptTypeNum(int projectid,String type);
    int updateResult(Report report);
    int deleteResult(Report report);
    int selectResult(Report report);
}
