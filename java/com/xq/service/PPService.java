package com.xq.service;

import com.xq.model.*;

import java.util.List;

public interface PPService {
    //4.5上传jar文件
    String insertJar(PP pp);
    //4.6查找jar包
    PP selectPP(PP pp);
    //新建项目时查找project表里面插入记录后的projectid
    int select_new_projectid(Project project);
    //新建项目时更新manager表里面的recentid
    int update_recentid(User user);
//3.新建项目时将所有信息存入PP表里面
    //int insert_PP(PP pp);
    int insert_PP(List<PP> list);
    //先查询 projectid=0的Menu表中的所有信息
    List<Menu> select_Menu(int projectid);
    //4.新建项目时Menu表中的所有信息复制插入
    //4.1先将选择的安全等级父节点的信息插入menu表
    int insertFJ_menu(Menu menu);
    //4.2再将子节点信息插入menu表中
    int insertMenu(Menu menu);
    //先查询 projectid=0的XML表中的所有信息
    List<Xml> selectXml(int projectid);
    //5.xml表中的所有信息复制插入
    int insertXml(Xml xml);

}
