package com.xq.controller;

import com.xq.model.Menu;
import com.xq.model.MenuItem;
import com.xq.service.MenuService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xq on 2017/3/15.
 */
@Controller
@RequestMapping(value = "/etree")
public class MenuController {
    @Resource
    private MenuService menuService;
//  根据父节点获取到子节点集合
    @RequestMapping(value = "/get_data.do",produces = "application/json; charset=utf-8")
    @ResponseBody
    public List<MenuItem> get_data(@RequestParam(value = "id",defaultValue = "-1") String id, HttpServletRequest request){
        System.out.println("the id given to get_data is"+id);
        List<MenuItem> list = new ArrayList<MenuItem>();
        if ("-1".equals(id)){
            System.out.println("传入的id是-1");
            MenuItem item = new MenuItem();
            item.setId("0.");
            item.setText("安全等级标准框架");
            item.setState("closed");
            item.setType("directory");
//            return item;
            list.add(item);
        }else {
            System.out.println("id exists ,so get its children ");
            //4.12获取上次打开的projectid
            Integer projectid;
            String description;
            String user=request.getSession().getAttribute("loginUser").toString();
            if (request.getSession().getAttribute("projectid")==null||request.getSession().getAttribute("projectid")==""){
                System.out.println("从数据库中获取最近的projectid");

                projectid=menuService.getProjectid(user);


                //将数据库中查询出来的projectid放入session中
                request.getSession().setAttribute("projectid",projectid);

            }else {
                //如果session里面有projectid，则不用再去查数据库
                System.out.println("从session里面获取projectid");
                projectid=Integer.parseInt(request.getSession().getAttribute("projectid").toString());
            }
            description=menuService.getDescription(user,projectid);
            request.getSession().setAttribute("grade",description);
            System.out.println("从数据库中获取最近的level"+description);
            if (projectid==null){   //如果最近没有打开项目或者session里面也没有
                /*MenuItem item = new MenuItem();
                item.setId("0.");
                item.setText("安全等级标准框架");
                item.setState("closed");
                item.setType("directory");
                list.add(item);*/
                System.out.println("projectid==null");
                return null;
            }else {

                MenuItem item = new MenuItem();
                item.setFj(id);
                item.setProjectid(projectid);
                list = menuService.getChildren(item);
            }

        }
        return list;
    }

    @RequestMapping(value = "/get_treegrid",produces = "application/json; charset=utf-8")
    @ResponseBody
    public List<MenuItem> get_treegrid(@RequestParam(value = "id",defaultValue = "-1") String id,HttpServletRequest request){
        System.out.println("the id given to get_treedrid is"+id);
        id=id.split(",")[0];
        System.out.println("the id after split is"+id);
        List<MenuItem> list = new ArrayList<MenuItem>();
        if ("-1".equals(id)){
            System.out.println("传入的id是-1");
            MenuItem item = new MenuItem();
            item.setId("0.");
            item.setText("安全等级标准框架");
            item.setState("closed");
            item.setType("directory");
//            return item;
            list.add(item);
        }else {
            System.out.println("id exists ,so get its children ");
            //4.12获取上次打开的projectid
            Integer projectid;
            String user=request.getSession().getAttribute("loginUser").toString();
            String description;// 安全等级
            if (request.getSession().getAttribute("projectid")==null||request.getSession().getAttribute("projectid")==""){
                System.out.println("从数据库中获取最近的projectid");

                projectid=menuService.getProjectid(user);

                //将数据库中查询出来的projectid放入session中 将安全等级也放入
                request.getSession().setAttribute("projectid",projectid);

            }else {
                //如果session里面有projectid，则不用再去查数据库
                System.out.println("从session里面获取projectid");
                projectid=Integer.parseInt(request.getSession().getAttribute("projectid").toString());

            }
            description=menuService.getDescription(user,projectid);
            request.getSession().setAttribute("grade",description);
            System.out.println("save grade"+description);
            if (projectid==null){   //如果最近没有打开项目或者session里面也没有
                /*MenuItem item = new MenuItem();
                item.setId("0.");
                item.setText("安全等级标准框架");
                item.setState("closed");
                item.setType("directory");
                list.add(item);*/
                System.out.println("projectid==null");
                return null;
            }else {

                MenuItem item = new MenuItem();
                item.setFj(id);
                item.setProjectid(projectid);
                list = menuService.getChildren(item);
            }

        }
        return list;
    };

//    @RequestMapping(value = "/treegrid", produces = "application/json; charset=utf-8")
//    @ResponseBody
//    public List<MenuItem> treegrid(@RequestParam(value = "id", defaultValue = "-1") String id) {
//        List<MenuItem> list = new ArrayList<MenuItem>();
//        if("-1".equals(id))
//        {
//            MenuItem item =new MenuItem();
//            item.setId("0.");
//            item.setState("closed");
//            item.setType("directory");
//            item.setFj("");
//            item.setText("安全等级标准框架");
//            list.add(item);
//        }
//        else
//        {
//            list=menuService.getChildren(id);
//        }
//        return list;
//
//    }
    //  新建文件夹
    @RequestMapping(value = "/createDirectory",produces = "application/json; charset=utf-8")
    @ResponseBody
    public MenuItem createDirectory(@RequestParam(value = "parentId",defaultValue = "-1") String fj,HttpServletRequest request){
        System.out.println(fj);
        int projectid=Integer.parseInt(request.getSession().getAttribute("projectid").toString());
        MenuItem item = new MenuItem();
        item.setFj(fj);
        item.setProjectid(projectid);
        return menuService.createDirectory(item);
    }

//  新建节点
    @RequestMapping(value = "/create",produces = "application/json; charset=utf-8")
    @ResponseBody
    public MenuItem create(@RequestParam(value = "parentId",defaultValue = "-1") String fj/*,String runType*/,HttpServletRequest request){
        System.out.println(fj);
        int projectid=Integer.parseInt(request.getSession().getAttribute("projectid").toString());

        System.out.println("projectid:"+projectid);
        MenuItem item = new MenuItem();
        item.setFj(fj);
        item.setProjectid(projectid);
//        item.setRuntype(runType);
        MenuItem result= menuService.create(item);

        return result;
    }

//    测试最大id
    @RequestMapping(value = "/maxId",produces = "application/json; charset=utf-8")
    @ResponseBody
    public String maxId(@RequestParam(value = "parentId",defaultValue = "-1") String fj,HttpServletRequest request){
        System.out.println(fj);

        int projectid=Integer.parseInt(request.getSession().getAttribute("projectid").toString());
        MenuItem item = new MenuItem();
        item.setFj(fj);
        item.setProjectid(projectid);
        return menuService.maxId(item)+"";
    }

//    修改节点信息
    @RequestMapping(value = "/update",produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map update(@RequestParam(value = "id") String id, @RequestParam(value = "text")String text,HttpServletRequest request){
        System.out.println(id+"==="+text);
        String[] arr=id.split("\\.");
        String fj="";
        for (int i=0;i<arr.length-1;i++){
            fj=fj+arr[i]+".";
        }
        System.out.println("fj===="+fj);
//        先判断同一个文件夹下面有没有该文件名，若有则返回1.不让修改
        int projectid=Integer.parseInt(request.getSession().getAttribute("projectid").toString());
        List<Menu> list=menuService.selectText(fj,text,projectid);
        Map<String,String> map = new HashMap<String, String>();
        if (list.size()>0){
            map.put("success","false");
            return map;   //这里返回map.put("success","false");表示节点名已存在，不能修改
        }else {
            int res=menuService.update(id,text,projectid);
            if (res>0)
            {
                map.put("success","true");
                map.put("id",id);
                map.put("text",text);
                return map;
            }
            map.put("success","false1");
            return map;  //这里表示修改操作失败
        }


    }
//      删除节点
    @RequestMapping(value = "/destroy",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map destroy(@RequestParam(value = "id") String id,HttpServletRequest request){
        System.out.println("id===="+id);
        int projectid=Integer.parseInt(request.getSession().getAttribute("projectid").toString());
        int r = menuService.destroy(id,projectid);
        Map<String,Boolean> map1 = new HashMap<String, Boolean>();
        System.out.println("r===="+r);
        if (r>0){
            System.out.println("删除节点成功");
            map1.put("success",true);
        }else {
            System.out.println("删除节点失败");
            map1.put("success",false);
        }
        return map1;
    }

}
