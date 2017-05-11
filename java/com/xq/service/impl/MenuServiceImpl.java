package com.xq.service.impl;

import com.xq.dao.MenuDao;
import com.xq.model.Menu;
import com.xq.model.MenuItem;
import com.xq.model.Project;
import com.xq.model.Xml;
import com.xq.service.MenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xq on 2017/3/15.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MenuServiceImpl implements MenuService {
    @Resource
    private MenuDao menuDao;

    public List<MenuItem> getChildren(MenuItem item1) {
        String fj=item1.getFj();
        //String fj=map.get("fj").toString();
        System.out.println("the id given to MenuService getChildren is "+fj);
        List<MenuItem> res = new ArrayList<MenuItem>();
        List<MenuItem> list = menuDao.getChildren(item1);

        for (MenuItem item:list){
            item.setId(fj+""+item.getId()+".");
            res.add(item);
        }
        return res;
    };
    public List<MenuItem> getChildren2(String fj) {
        System.out.println("the id given to MenuService getChildren2 is "+fj);
        List<MenuItem> res = new ArrayList<MenuItem>();
        List<MenuItem> list = menuDao.getChildren2(fj);
        for (MenuItem item:list){
            item.setId(fj+""+item.getId()+".");
            res.add(item);
        }
        return res;
    }
//public List<MenuItem> getTgChildren(String fj){
//
//    List<MenuItem> list = menuDao.getChildren(fj);
//
//    return list;
//}
    public int maxId(MenuItem item){
        return menuDao.maxId(item);
    }

    public MenuItem createDirectory(MenuItem item){
        //        子节点的最大值
        int id=0;
        int level=0;
        Integer tmpid=menuDao.maxId(item);
        //Integer tmpid=menuDao.maxId(map);
//        没有子节点，则从1开始
        if (null==tmpid){
            id = 1;
        }else {
            id =  tmpid+1; // 有，则取最大值加1
        }
        String fj=item.getFj();
        //String fj=map.get("fj").toString();
        String[] sarr=fj.split("\\.");
        level = sarr.length;
        System.out.println(id+"==="+level);
        int i=0;
        String fstr="";
        for (;i<sarr.length-1;i++){
            fstr += sarr[i]+".";
        }
        String pid=sarr[i];
        System.out.println(fstr+"==="+pid);
//        fstr pid 组合为输入父节点的真实id，更新状态为closed

        MenuItem item1 = new MenuItem();
        int projectid=item.getProjectid();
        item1.setProjectid(projectid);
        item1.setFj(fstr);
        item1.setId(pid);
        item1.setState("closed");
        item1.setType("directory");
        menuDao.updateFj(item1);

/*        Map<String,String> map1=new HashMap<String, String>();
        String projectid=map.get("projectid").toString();
        map1.put("projectid",projectid);
        map1.put("fj",fstr);
        map1.put("id",pid);
        map1.put("state","closed");
        map1.put("type","directory");
        menuDao.updateFj(map1);*/

        MenuItem item2 = new MenuItem();
        item2.setId(id+"");
        item2.setFj(fj);
        item2.setLevel(level);
        item2.setProjectid(projectid);
        item2.setText("新文件夹");
        item2.setState("closed");
        item2.setType("directory");
        int r = menuDao.insertFj(item2);
//        插入新节点成功，则返回新增节点
        if (r>0) {
            item2.setId(fj+id+".");
            return item2;
        }
        return null;
    }

    public MenuItem create(MenuItem item) {
//        子节点的最大值
        int id=0;
        int level=0;
        Integer tmpid=menuDao.maxId(item);
        //Integer tmpid=menuDao.maxId(map);
//        没有子节点，则从1开始
        if (null==tmpid){
            id = 1;
        }else {
            id =  tmpid+1; // 有，则取最大值加1
        }
        //String fj=map.get("fj").toString();
        String fj=item.getFj();
        String[] sarr=fj.split("\\.");
        level = sarr.length;
        System.out.println(id+"==="+level);
        int i=0;
        String fstr="";
        for (;i<sarr.length-1;i++){
            fstr += sarr[i]+".";
        }
        String pid=sarr[i];
        System.out.println(fstr+"==="+pid);
//        fstr pid 组合为输入父节点的真实id，更新状态为closed
        int projectid=item.getProjectid();
        MenuItem item1 = new MenuItem();
        item1.setProjectid(projectid);
        item1.setFj(fstr);
        item1.setId(pid);
        item1.setState("closed");
        item1.setType("directory");
        menuDao.updateFj(item1);

/*        Map<String,String> map1=new HashMap<String, String>();
        String projectid=map.get("projectid").toString();
        map1.put("projectid",projectid);
        map1.put("fj",fstr);
        map1.put("id",pid);
        map1.put("state","closed");
        map1.put("type","directory");
        menuDao.updateFj(map1);*/

        MenuItem item2 = new MenuItem();
        item2.setProjectid(projectid);
        item2.setId(id+"");
        item2.setFj(fj);
        item2.setLevel(level);
        item2.setText("新节点");
        item2.setState("open");
        item2.setType("file");
//        item2.setRuntype(item.getRuntype());
        int r = menuDao.insertFj(item2);
//        插入新节点成功，则返回新增节点
        if (r>0) {
            item2.setId(fj+id+".");
            return item2;
        }
        return null;
    }

    public int update(String id,String text,int projectid) {
        String[] sarr = id.split("\\.");
        String fj = "";
        for (int i=0; i<sarr.length-1; i++){
            fj = fj+sarr[i]+".";
        }
        String sid = sarr[sarr.length-1];
        System.out.println(fj+"\n"+sid);
        MenuItem item = new MenuItem();
        item.setFj(fj);
        item.setProjectid(projectid);
        item.setId(sid);
        item.setText(text);
//        item.setType("directory");
/*        Map<String,String> map = new HashMap<String, String>();
        map.put("fj",fj);
        map.put("id",sid);
        map.put("text",text);
        map.put("projectid",projectid);*/
        return menuDao.updateText(item);
    }

    public int destroy(String id,int projectid) {
        System.out.println("id=="+id+"projectid==="+projectid);
        String[] sarr = id.split("\\.");
        String fj = "";
        for (int i=0; i<sarr.length-1; i++){
            fj = fj+sarr[i]+".";
        }
        String sid = sarr[sarr.length-1];
        System.out.println(fj+"\n"+sid);
        MenuItem item = new MenuItem();
        item.setFj(fj);
        item.setProjectid(projectid);
        item.setId(sid);
/*        Map<String,String> map = new HashMap<String, String>();
        map.put("fj",fj);
        map.put("id",sid);
        map.put("projectid",projectid);*/
        // 1.先删除自己 再删除其下所有子节点
        System.out.println("fj="+item.getFj()+"projectid="+item.getProjectid()+"id="+item.getId());
        int res=menuDao.destroy(item);
        if (res>0){
            //删除xml内容
            Xml xml =new Xml();
            xml.setProjectid(projectid);
            xml.setTitle(id+"%");
            int selectXml = menuDao.selectXml(xml);
            System.out.println("删除一个节点的xml:title:"+xml.getTitle()+":Projectid："+xml.getProjectid());
            System.out.println("selectXml:"+selectXml);
            if (selectXml > 0) {
                System.out.println("删除一个节点的xml内容");
                menuDao.deleteXml(xml);
            }
            /*map = new HashMap<String, String>();
            map.put("fj",id+"%");
            map.put("projectid",projectid);*/
//        删除其下所有子节点
            item.setFj(id+"%");
            item.setProjectid(projectid);
            menuDao.destroyAll(item);
        System.out.println("这里执行了ya1-------");
            int selectXml1 = menuDao.selectAll(xml);
            System.out.println("selectXml1:"+selectXml1);
            System.out.println("删除所有子节点的xml:title:"+xml.getTitle()+":Projectid："+xml.getProjectid());
            if (selectXml1 > 0) {
                System.out.println("删除所有子节点的xml内容");
                menuDao.deleteAll(xml);
            }
//        2.如果删除点是其父节点的唯一子节点，还需要改变其父节点的状态
            item.setFj(fj);
            item.setProjectid(projectid);
            /*map = new HashMap<String, String>();
            map.put("fj",fj);
            map.put("projectid",projectid);*/
            int r = menuDao.countFj(item);
            System.out.println("查询父节点的子节点个数r======"+r);
            if (r==0){
                sarr = fj.split("\\.");
                String pj = "";
                for (int i=0; i<sarr.length-1; i++){
                    pj = pj+sarr[i]+".";
                }
                sid = sarr[sarr.length-1];
                item.setProjectid(projectid);
                item.setFj(pj);
                item.setId(sid);
                item.setState("closed");
                item.setType("directory");
                /*map = new HashMap<String, String>();
                map.put("projectid",projectid);
                map.put("fj",pj);
                map.put("id",sid);
                map.put("state","open");
                map.put("type","file");*/
                menuDao.updateFj(item);
                //return 1;
            }
            }else {
                return 0;
            }

        return 1;
    }

//4.12查询最近一次的Projectid
    public Integer getProjectid(String user) {

        return menuDao. getProjectid(user);
    }

    //4.12查询最近一次的安全等级
    public String getDescription(String user,int projectid) {
        Project project=new Project();
        project.setUser(user);
        project.setProjectid(projectid);
        return menuDao.getDescription(project);
    }
//   4.18先判断同一个文件夹下面有没有该文件名，若有则返回1.不让修改
    public List<Menu> selectText(String fj, String text, int projectid) {
        Menu menu =new Menu();
        menu.setProjectid(projectid);
        menu.setText(text);
        menu.setFj(fj);
        return menuDao.selectText(menu);
    }
}
