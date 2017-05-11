package com.xq.controller;

import com.xq.model.*;
import com.xq.service.PPService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by lu on 2017/4/5.
 */
@Controller
public class PPController {
    @Resource
    private PPService ppService;

    //查找PP
    @RequestMapping(value = "/selectPP.do", produces = "application/json;charset=utf-8")
    @ResponseBody
    public PP selectPP(int projectid, int propertyid) {
        PP pp = new PP();
        pp.setProjectid(projectid);
        pp.setPropertyid(propertyid);
        PP res = ppService.selectPP(pp);
        return res;
    }

    //上传Jar包
    @RequestMapping(value = "/importDriver.do", method = RequestMethod.POST, produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String uploadDriver(HttpServletRequest request, HttpServletResponse response)
            throws IllegalStateException, IOException {
        //解析器解析request的上下文
        CommonsMultipartResolver multipartResolver =
                new CommonsMultipartResolver(request.getSession().getServletContext());
        //先判断request中是否包涵multipart类型的数据，如果js中通过普通的ajax-form传参，则该处则不会继续执行
        if (multipartResolver.isMultipart(request)) {
            //再将request中的数据转化成multipart类型的数据
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            String str = "/"; //文件夹地址，通常是根据他获取到文件全路径
            String path1 = request.getSession().getServletContext().getRealPath(str);//获取到tomcat下该文件的全路径

            path1 = path1.replaceAll("\\\\", "/");
            if (!path1.endsWith("/")) {
                path1 = path1 + "/";
            }
            path1 = path1 + "driverLib";
            //如果没有文件夹则新建一个文件夹driverLib
            File folder = new File(path1);
            if (!folder.isDirectory()) {
                folder.mkdir();
                System.out.println("新建文件夹" + folder.getName());
            }

            System.out.println("jar包上传的绝对路径是" + path1);
            Iterator iter = multiRequest.getFileNames(); //获取文件名称
            while (iter.hasNext()) {
                MultipartFile file = multiRequest.getFile((String) iter.next());
                if (file != null) {
                    String fileName = file.getOriginalFilename();

                    path1 = path1 + "/" + fileName;
                    File localFile = new File(path1);


                    //写文件到本地
                    file.transferTo(localFile);
                    //获取传入的值
                    String value = path1;
                    //int projectid=1;
                    //int propertyid=1;
                    int projectid = Integer.parseInt(request.getParameter("projectid"));
                    int propertyid = Integer.parseInt(request.getParameter("propertyid"));
                    //将路径写入数据库中
                    PP pp = new PP();
                    pp.setProjectid(projectid);
                    pp.setPropertyid(propertyid);
                    pp.setValue(value);
                    String res = ppService.insertJar(pp);
                    if (res.contains("success")) {
                        System.out.println("jar包路径保存成功");
                        return "1";
                    } else {
                        System.out.println("jar包路径保存失败");
                        return "0";
                    }

                }
            }
        }
        System.out.println("文件上传失败");
        return "0";
    }

    //创建一个新项目
    @RequestMapping(value = "/createProject.do", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String creatProject(HttpServletRequest request, HttpServletResponse response) throws IllegalStateException, IOException {
        //String user="user1";
        String user = request.getSession().getAttribute("loginUser").toString();
//1.上传jsonStr
//        获取json字符串
        String jsonStr = request.getParameter("json");
        System.out.println("json===" + jsonStr);
        JSONArray jsonArray = JSONArray.fromObject(jsonStr);
        System.out.println("JSONARRAY====" + jsonArray);
        System.out.println("数组长度====" + jsonArray.size());
        List<Project> list = new ArrayList<Project>();
        List<PP> list1 =new ArrayList<PP>();

        for (int i = 0; i < jsonArray.size(); i++) {
            Project project = new Project();
            PP pp = new PP();
            //System.out.println("这里把Json字符串放入json对象中");
            JSONObject jsonJ = jsonArray.getJSONObject(i);
//            将传入的jsonStr里面数据库的名字作为项目名称
            String value = jsonJ.getString("value");
            System.out.println("value===="+value);
            project.setName(value);
            list.add(project);

            int propertid = Integer.parseInt(jsonJ.getString("propertyid"));
            pp.setPropertyid(propertid);
            //System.out.println("pp.propertyid===="+pp.getPropertyid());
            pp.setValue(value);
            list1.add(pp);
        }
//确定安全等级
        String grade=list.get(1).getName();
        System.out.println("用户选择的安全等级为"+grade);
        request.getSession().setAttribute("grade", grade);
        int id;
        String fj="0.";
        String text="安全标记保护级";
        String title="0.3.";
        if (grade.equals("安全标记保护级")){
            id=3;
            title="0.3.";
            text="安全标记保护级";

        }else if (grade.equals("系统审计保护级")){
            id=2;
            title="0.2.";
            text="系统审计保护级";

        }else {
            id=1;
            title="0.1.";
            text="用户自主保护级";
        }

//        1.先向table_project表里面插入一条记录，然后返回插入后的projectid
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = dateFormat.format(new Date());
        System.out.println("time====" + time);
        /*for(int i=0;i<list.size();i++){
            //System.out.println("list===="+list.get(i));
            System.out.println("list's name===="+list.get(i).getName());
        }*/


        String name = list.get(0).getName();
        System.out.println("新建项目名name=="+name+"*******新建项目user===="+user);
        Project project = new Project();
        PP pp = new PP();


        project.setTime(time);
        project.setName(name);
        project.setUser(user);
        project.setDescription(grade);
        ppService.select_new_projectid(project);
        int new_projectid =project.getProjectid();  //返回新建project的projectid
        System.out.println("返回的自增id===="+new_projectid);
        //System.out.println("新建项目的projectid==="+new_projectid);

        //2.将新的projectid存入session和manager数据库中
        request.getSession().setAttribute("projectid", new_projectid);
        System.out.println("session's projectid====="+request.getSession().getAttribute("projectid"));
        User user1 = new User();
        user1.setUser(user);
        user1.setProjectid(new_projectid);
        System.out.println("manager里面projectid===="+user1.getProjectid());
        int update_recentid = ppService.update_recentid(user1);
        System.out.println("更新manager里面的recent_projectid成功");


        //3.将所有信息存入PP表里面
        /*for (int i=0;i<list1.size();i++){
            System.out.println("list1==="+list1.get(i));
        }*/
        for (int i = 0; i < list1.size(); i++) {
            list1.get(i).setProjectid(new_projectid);
            //ppService.insert_PP(pp);
        }
        ppService.insert_PP(list1);
        System.out.println("所有信息插入PP表里面成功");

        //4.Menu表中的所有信息复制插入
        int projectid = 0;
/*        List<Menu> list_menu = ppService.select_Menu(projectid);

        System.out.println("list_menu.size()==="+list_menu.size());

        Menu menu = new Menu();
        for (int i = 0; i < list_menu.size(); i++) {

            list_menu.get(i).setProjectid(new_projectid);
           // System.out.println("menu_projectid===="+list_menu.get(i).getProjectid());
            ppService.insertMenu(list_menu.get(i));
        }*/


        //4.1先将选择的安全等级父节点的信息插入menu表
        Menu menu=new Menu();
        menu.setId(id);
        menu.setFj(fj);
        menu.setProjectid(new_projectid);
        menu.setLevel(1);
        menu.setState("closed");
        menu.setType("directory");
        menu.setText(text);
        ppService.insertFJ_menu(menu);

       //4.2再将子节点信息插入menu表中,只传new_projectid和fj+id过去
        Menu menu1=new Menu();
        menu1.setProjectid(new_projectid);
        menu1.setFj(title+"%");
        ppService.insertMenu(menu1);
//        ppService.insertMenu(new_projectid);
        System.out.println("向menu表中插入数据成功");

        //5.xml表中的所有信息复制插入
        //int projectid=0;
        /*List<Xml> xmlList = ppService.selectXml(projectid);
        System.out.println("xmlList.size()===="+xmlList.size());

        Xml xml = new Xml();
        for (int i = 0; i < xmlList.size(); i++) {
            xmlList.get(i).setProjectid(new_projectid);
            xmlList.get(i).setDescription(null);
            xmlList.get(i).setContent(null);
            System.out.println("xml_projectid===="+xmlList.get(i).getProjectid());
            ppService.insertXml(xmlList.get(i));
        }*/
        Xml xml=new Xml();
        xml.setProjectid(new_projectid);
        xml.setTitle(title+"%");
        ppService.insertXml(xml);
//        ppService.insertXml(new_projectid);
        System.out.println("xml中插入信息成功");

//2.上传jar包
        //解析器解析request的上下文
        System.out.println("这里开始上传jar文件");
        CommonsMultipartResolver multipartResolver =
                new CommonsMultipartResolver(request.getSession().getServletContext());
        //先判断request中是否包涵multipart类型的数据，如果js中通过普通的ajax-form传参，则该处则不会继续执行
        if (multipartResolver.isMultipart(request)) {
            //再将request中的数据转化成multipart类型的数据
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            String str = "/"; //文件夹地址，通常是根据他获取到文件全路径
            String path1 = request.getSession().getServletContext().getRealPath(str);//获取到tomcat下该文件的全路径

            path1 = path1.replaceAll("\\\\", "/");
            if (!path1.endsWith("/")) {
                path1 = path1 + "/";
            }
            path1 = path1 + "driverLib";
            //如果没有文件夹则新建一个文件夹driverLib
            File folder = new File(path1);
            if (!folder.isDirectory()) {
                folder.mkdir();
                System.out.println("新建文件夹" + folder.getName());
            }

            System.out.println("jar包上传的绝对路径是" + path1);
            Iterator iter = multiRequest.getFileNames(); //获取文件名称
            while (iter.hasNext()) {
                MultipartFile file = multiRequest.getFile((String) iter.next());
                if (file != null) {
                    String fileName = file.getOriginalFilename();

                    path1 = path1 + "/" + fileName;
                    File localFile = new File(path1);


                    //写文件到本地
                    file.transferTo(localFile);
                    //获取传入的值
                    String value = path1;
                    //int projectid=1;
                    //int propertyid=1;
                    //int projectid=Integer.parseInt(request.getParameter("projectid"));
                    int propertyid = 19;
                    //将路径写入数据库中
                    //PP pp = new PP();
                    pp.setProjectid(new_projectid);
                    pp.setPropertyid(propertyid);
                    pp.setValue(value);
                    String res = ppService.insertJar(pp);
                    if (res.contains("success")) {
                        System.out.println("jar包路径保存成功");
                        return "1";
                    } else {
                        System.out.println("jar包路径保存失败");
                        return "0";
                    }

                }
            }
        }

        return "1";
    }


    //测试创建一个新项目接收到的json字符串
    @RequestMapping(value = "/createProject1.do", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String test_creatProject(HttpServletRequest request, HttpServletResponse response) throws IllegalStateException, IOException {
        //String user="user1";
        String user = request.getSession().getAttribute("loginUser").toString();
//1.上传jsonStr
//        获取json字符串
        String jsonStr = request.getParameter("json");
        System.out.println("json===" + jsonStr);
        JSONArray jsonArray = JSONArray.fromObject(jsonStr);
        System.out.println("JSONARRAY====" + jsonArray);
        System.out.println("数组长度====" + jsonArray.size());
        return "1";

}


}
