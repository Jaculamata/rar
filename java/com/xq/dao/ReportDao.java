package com.xq.dao;

import com.xq.model.MenuItem;
import com.xq.model.Report;
import com.xq.model.Xml;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Liuwei on 2017/4/17.
 */
@Repository
public interface ReportDao {
    //4.17(lw)获取测试报告内容------4.27修改
    List<Report> getReport(Report report);
    //4.17(lw)查找节点的text
    String selectMenuText(MenuItem item);
    //4.17(lw)获取项目名
    String selectProjectName(int projectid);
    //4.18(ht)保存测试结果到数据库
    int save(Report report);
    int delete(Report report);
    int update(Report report);
    int select(Report report);
    //4.24(lw)查询所测安全等级脚本总数------4.27修改
    int selectScriptNum(MenuItem item);
    //4.24(lw)查询当前安全等级已测脚本数量------4.27修改
    int selectTestedNum(Report report);
    //4.24(lw)查询当前安全等级测试结果为某值的数量------4.27修改
    int selectResultNum(Report report);
    //5.9查询某一类型脚本数量
    int selectScriptTypeNum(Xml xml);
    //4.27(lw)查询menu表中安全功能子目录
    List<MenuItem> selectSecurityDir(MenuItem item);
    //4.27(lw)查询脚本内容
    List<Xml> selectXmlContent(Xml xml);
}
