package com.xq.service.impl;

import com.xq.dao.PPDao;
import com.xq.model.*;
import com.xq.service.PPService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;

/**
 * Created by lu on 2017/4/5.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class PPServiceImpl implements PPService {
    @Resource
    private PPDao ppDao;

    public PP selectPP(PP pp) {
        List<PP> list = ppDao.selectPP(pp);
        if (list.size()==0) return null;
        return list.get(0);
    }

    //4.14新建项目时查找project表里面插入记录后的projectid
    public int select_new_projectid(Project project) {
        return ppDao.select_new_projectid(project);
    }

    //新建项目时更新manager表里面的recentid
    public int update_recentid(User user) {
        System.out.println("user===="+user.getUser()+"id===="+user.getProjectid());
        return ppDao.update_recentid(user);
    }

    //3.将所有信息存入PP表里面
    public int insert_PP(List<PP> list) {
        return ppDao.insert_PP(list);
    }

    //4.先查询 projectid=0的Menu表中的所有信息
    public List<Menu> select_Menu(int projectid) {
        return ppDao.select_Menu(projectid);
    }

    //4.1先将选择的安全等级父节点的信息插入menu表
    public int insertFJ_menu(Menu menu) {
        return ppDao.insertFJ_menu(menu);
    }

    //4.2新建项目时再将子节点信息插入Menu表中,Menu表的所有信息复制插入
    public int insertMenu(Menu menu) {
        //System.out.println("menu===="+menu.getId()+":"+menu.getProjectid()+":"+menu.getFj()+":"+menu.getLevel()+":"+menu.getText()+":"+menu.getState());
        return ppDao.insertMenu(menu);
    }

   //先查询 projectid=0的XML表中的所有信息
    public List<Xml> selectXml(int projectid) {
        return ppDao.selectXml(projectid) ;
    }

    //5.xml表中的所有信息复制插入
    public int insertXml(Xml xml) {
       // System.out.println("ppDao.insertXml(xml)===="+ppDao.insertXml11(xml));
        return ppDao.insertXml(xml);
    }

    //保存jar包路径到数据库
    public String insertJar(PP pp){
        PP rs = selectPP(pp);
        int res = 0;
//        如果数据库中没有该记录则插入新的记录
        if (rs==null){
            res = ppDao.insertPP(pp);
        }else {
            System.out.println("更新数据库pp");
//        如果有该jar包的记录，则删除源文件并更新数据库
            String path1=rs.getValue();
            System.out.println("数据库中查询到的path1是"+path1);
            File localFile = new File(path1);
            if (localFile.exists()){
                localFile.delete();
            }
            res = ppDao.updatePP(pp);
        }
        if (res>0) {
            System.out.println("更新或插入成功pp");
            return "success";
        }
        return "fail";
    }
}
