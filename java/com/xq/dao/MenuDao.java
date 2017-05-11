package com.xq.dao;

import com.xq.model.Menu;
import com.xq.model.MenuItem;
import com.xq.model.Project;
import com.xq.model.Xml;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by xq on 2017/3/15.
 */
@Repository
public interface MenuDao {
    List<MenuItem> getChildren(MenuItem item);
    Integer maxId(MenuItem item);
    void updateFj(MenuItem item);
    int insertFj(MenuItem item);
    int updateText(MenuItem item);
    int destroy(MenuItem item);
    int destroyAll(MenuItem item);
    Integer countFj(MenuItem item);

    //4.9先查询xml表中有没有内容
    Integer selectXml(Xml xml);
    //4.9删除xml表中的内容
    int deleteXml(Xml xml);
    //4.9查询所有子节点的xml
    Integer selectAll(Xml xml);
    //4.9删除所有子节点的xml
    int deleteAll(Xml xml);

    List<MenuItem> getChildren2(String fj);

    //4.12获取上次打开的projectid
    public Integer getProjectid(String user);
public String getDescription(Project project);
    //   4.18先判断同一个文件夹下面有没有该文件名，若有则返回1.不让修改
    List<Menu> selectText(Menu menu);
}
