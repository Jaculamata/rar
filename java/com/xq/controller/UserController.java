package com.xq.controller;

import com.xq.model.User;
import com.xq.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import org.apache.log4j.Logger;

@Controller
//@RequestMapping("/user")
public class UserController {

//    private Logger log = Logger.getLogger(UserController.class);
    @Resource
    private UserService userService;
//用户登录
    @RequestMapping(value = "/login", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String login(String username, String password,String role,HttpServletRequest request){
        User user = new User();
        user.setUser(username);
        user.setPassword(password);
        System.out.println(username+":"+password);
        String res=userService.log(user);
        System.out.println(res);
        System.out.println(role);
        //把用户对象放入到session中，将会触发LoginListenner中的attributeAdded事件

        Map<String,Object> userSession = new HashMap<String, Object>();
        userSession.put("username",username);
        userSession.put("password",password);
        userSession.put("role",role);
        userSession.put("request",request);
        //String[] sarr = res.split("#");
        if(res.contains("success1")){
            //request.getSession().setAttribute("loginUser", userSession);
            request.getSession().setAttribute("loginUser", username);
            System.out.println("session is============"+request.getSession().getAttribute("loginUser"));
            if(role.equals("admin")){
                System.out.println("test");
                return "1";  //管理员界面

            }
            return "2";  //普通用户界面
        }
        else if(res.contains("success2")) {
            //request.getSession().setAttribute("loginUser", userSession);
            request.getSession().setAttribute("loginUser", username);
            System.out.println("session is============"+request.getSession().getAttribute("loginUser"));
            if(role.equals("user")){
                return "2";  //测试员界面
            }
            return "0";
        }else {
            return "0";
        }

    }
    //退出登录
    @RequestMapping(value = "/logout", produces = "text/plain; charset=utf-8")
    @ResponseBody
    public String logout(HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession(false);//防止创建Session
        if(session == null){
            return "1";
        }
        //session.removeAttribute("loginUser");
        //session.removeAttribute("projectid");
        session.invalidate();
        return "1";
    }

//查询所有用户
    @RequestMapping("/showUser")
    public String showUser(HttpServletRequest request, Model model){
//        log.info("查询所有用户信息");
        List<User> userList = userService.getAllUser();
        model.addAttribute("userList",userList);
        for (int i=0; i<userList.size(); i++)
            System.out.println(userList.get(i));
        return "showUser";
    }

    //3.30判断用户名是不是唯一的,返回1表示用户名不存在,返回0表示用户名已存在
    @RequestMapping(value = "/selectUser.do",produces = "application/json; charset=utf-8")
    @ResponseBody
    public String selectUserName(String username){
        System.out.println("username");
        String res = userService.getUserName(username);
        System.out.println("查询到用户名已存在"+res);
        if(res.contains("success")){
            return "0";
        }
        return "1";
    }

    //3.31查询用户
    @RequestMapping(value = "/selectTestUser.do",produces = "application/json; charset=utf-8")
    @ResponseBody
    public List<User> selectTestUser(String username){
        System.out.println("username");
        List<User> userList = userService.selectUser(username);
        if(userList.size()>0){
            return userList;
        }
        return null;
    }

    //3.30修改密码
    @RequestMapping(value = "/changePwd.do", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String changePWd(String oldPwd,String newPwd,HttpServletRequest request){
        String username = request.getSession().getAttribute("loginUser").toString();
        User user=new User();
        user.setUser(username);
        user.setPassword(oldPwd);
        System.out.println(username+":"+oldPwd);
        String res=userService.getPwd(user);
        if(res.contains("success")){
            if(oldPwd.equals(newPwd)){
                return "3";
            }
            user.setPassword(newPwd);
            String res1=userService.changePwd(user);
            System.out.println("修改密码"+res);

            if(res1.contains("success")){
                return "1";
            }
            return "0";

        }
        return "2";

    }

    //3.30管理员查询所有用户,要求用户名唯一
    @RequestMapping(value = "/showTestUser.do",produces = "application/json; charset=utf-8")
    @ResponseBody
    public List<User> showUser(){
        String role="admin";
        List<User> userList = userService.getTestUser(role);
       if(userList.size()>0){
           return userList;
       }
        return null;
    }

    //3.30管理员新增用户
    @RequestMapping(value = "/addUser.do",produces = "application/json; charset=utf-8")
    @ResponseBody
    public String addUser(String username, String password){
        User user = new User();
        user.setUser(username);
        user.setPassword(password);
        String role="user";
        user.setRole(role);
        System.out.println(username+":"+password);

        String res0 = userService.getUserName(username);
        if(res0.contains("success")){
            return "2";
        }
        String res=userService.addUser(user);
        System.out.println("新增用户"+res);

        if(res.contains("success")){
            return "1";
        }
        return "0";
    }

    //3.30管理员删除用户
    @RequestMapping(value = "/deleteUser.do",produces = "application/json; charset=utf-8")
    @ResponseBody
    public String deleteUser(String username){
        User user = new User();
        user.setUser(username);
        System.out.println("username是"+username);
        String res=userService.deleteUser(user);
        System.out.println("删除用户"+res);

        if(res.contains("success")){
            return "1";
        }
        return "0";
    }
    //4.4重置测试员密码
    @RequestMapping(value = "/resetUserPwd.do", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String resetUserPwd(String username){
        User user = new User();
        System.out.println(username+":"+username);
        user.setUser(username);
        user.setPassword("888888");
        String res = userService.changePwd(user);
        System.out.println("重置密码"+res);
        if(res.contains("success")){
            return "1";
        }
        return "0";
    }
}
