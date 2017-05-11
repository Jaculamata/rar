package com.xq.service;

import com.xq.model.Menu;
import com.xq.model.MenuItem;
import com.xq.model.Project;

import java.util.List;

/**
 * Created by xq on 2017/3/15.
 */
public interface MenuService {
    List<MenuItem> getChildren(MenuItem item);
    List<MenuItem> getChildren2(String fj);
//    List<MenuItem> getTgChildren(String fj);
    MenuItem create(MenuItem item);
    MenuItem createDirectory(MenuItem item);
    int maxId(MenuItem item);
    int update(String id, String text, int projectid);
    int destroy(String id, int projectid);
    //4.12获取上次打开的projectid
    Integer getProjectid(String user);
    String getDescription(String user,int projectid);
//    4.18先判断同一个文件夹下面有没有该文件名，若有则返回1.不让修改
    List<Menu> selectText(String fj, String text, int projectid);
}
