package com.xq.controller;

import com.xq.model.Project;
import com.xq.model.User;
import com.xq.service.MenuService;
import com.xq.service.ProjectService;
import com.xq.service.ReportService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by lu on 2017/4/11.
 */
@Controller
public class ProjectController {
    @Resource
    private ProjectService projectService;
    @Resource
    private MenuService menuService;
    @Resource
    private ReportService reportService;

    //查找Project
    @RequestMapping(value = "/selectProject.do", produces = "application/json;charset=utf-8")
    @ResponseBody
    public List<Project> selectProject(HttpServletRequest request){
        //Project project=new Project();
        String user=request.getSession().getAttribute("loginUser").toString();
        List<Project> res =  projectService.selectProject(user);
        return res;
    }

    //打开一个Project新项目
    @RequestMapping(value = "/openProject.do", produces = "application/json;charset=utf-8")
    @ResponseBody
    public String openProject(int projectid,HttpServletRequest request){
        //String user="admin";
        String user=request.getSession().getAttribute("loginUser").toString();
        //将最新打开的项目id存入数据库
        User user1=new User();
        user1.setUser(user);
        user1.setProjectid(projectid);
        System.out.println("user:"+user+";projectid:"+projectid);
        int res=projectService.updateid(user1);
        //System.out.println("res===="+res);
        if (res>0){
            //将新打开项目的projectid放入session中
            request.getSession().setAttribute("projectid",projectid);
            return "1"; //返回一表示项目id插入成功
        }
       return "0";
    }

    //删除一个项目
    @RequestMapping(value = "/deleteProject.do", produces = "application/json;charset=utf-8")
    @ResponseBody
    public String deleteProject(int projectid,HttpServletRequest request){
        if (projectid == 0){
            return "3";  //删除项目为模板项目，不能删除
        }
        //String user="user1";
        String user=request.getSession().getAttribute("loginUser").toString();
        //将最新打开的项目id存入数据库
        User user1=new User();
        user1.setUser(user);
        user1.setProjectid(projectid);
        System.out.println("user:"+user+";projectid:"+projectid);
        //如果删除的这个项目正好是最近打开的项目，则清空recent_projectid，同时清空session
        HttpSession session=request.getSession();
        int r=projectService.update_recentid(user1);
        //System.out.println("r===="+r);

//        删除project表和menu表和xml表中的中的
        Project project =new Project();
        project.setUser(user);
        project.setProjectid(projectid);
        int res=projectService.deleteProject(project);

        if (res>0){
           if (r==1){
                //删除的projectid正好等于打开的id则清空session里面的id
                session.removeAttribute("projectid");
                return "1"; //返回1表示删除项目id和打开的id相同
            }else {
                return "2"; //返回2表示删除项目id和打开的id不相同
            }

            //return "1";
        }else {
            return "0";
        }
    }


    //4.24(lw)显示项目名
    @RequestMapping(value = "/showProjectName.do", produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String showProjectName(HttpServletRequest request){
        Integer projectid;
        String projectName = "";
        //如果session中有projectid，则使用，否则查找数据库manager表中的projectid
        if (request.getSession().getAttribute("projectid") == null||request.getSession().getAttribute("projectid") == ""){
            String user = request.getSession().getAttribute("loginUser").toString();
            System.out.println("showProjectName user===" + user);
            projectid = menuService.getProjectid(user);
            //将数据库中查询出来的projectid放入session中
            request.getSession().setAttribute("projectid",projectid);
        }else {
            //如果session里面有projectid，则不用再去查数据库
            System.out.println("从session里面获取projectid");
            projectid = Integer.parseInt(request.getSession().getAttribute("projectid").toString());
        }
        if (projectid != null){
            projectName = reportService.selectProjectName(projectid);
        }
        return projectName;
    }
}
