package com.xq.dao;

import com.xq.model.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by lu on 2017/4/5.
 */
@Repository
public interface PPDao {
    List<PP> selectPP(PP pp);
    int insertPP(PP pp);
    int updatePP(PP pp);
    //4.14新建项目时查找project表里面插入记录后的projectid
    int select_new_projectid(Project project);
    //新建项目时更新manager表里面的recentid
    int update_recentid(User user);
    //4.14.项目插入PP表
    int insert_PP(List<PP> list);
    //4.14先查询 projectid=0的Menu表中的所有信息
    List<Menu> select_Menu(int projectid);
    //4.1先将选择的安全等级父节点的信息插入menu表
    int insertFJ_menu(Menu menu);
    //4.2.新建项目时Menu表中的所有信息复制插入
    int insertMenu(Menu menu);
    //先查询 projectid=0的XML表中的所有信息
    List<Xml> selectXml(int projectid);
    //5.xml表中的所有信息复制插入
    int insertXml(Xml xml);



}
