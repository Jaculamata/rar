package com.xq.service.impl;

import com.xq.dao.UserDao;
import com.xq.model.User;
import com.xq.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {
    
    @Resource
    private UserDao userDao;

    public String log(User user) {
        List<User> list = userDao.log(user);
        if (list.size()>0) {
            String role=list.get(0).getRole();
            if(role.equals("admin")){  //如果是管理员则返回success1
                return "success1";
            }else if(role.equals("user"))
            {
                return "success2";  //如果是普通用户则返回success2
            }

        }
        return "fail#0";  //如果是查询失败则返回fail
    }

    public List<User> getAllUser() {
        return userDao.getAllUser();
    }
    //3.30查询用户名是否已经存在
    public String getUserName(String username) {
        List<User> list = userDao.getUserName(username);
        if (list.size()>0) {
            return "success";
        }
        return "fail";
    }

    //3.31查询用户
    public List<User> selectUser(String username){
        return userDao.getUserName(username);
    }

    public String getPwd(User user) {
        List<User> list = userDao.getPwd(user);
        if (list.size()>0) {
            return "success";
        }
        return "fail";
    }

    //3.30用户修改密码
    public String changePwd(User user) {
        int res=userDao.changePwd(user);
        if (res>0){
            return "success";
        }
        return "fail";
    }
    //3.30管理员获取所有用户
    public List<User> getTestUser(String role) {
        return userDao.getTestUser(role);
    }

    public String addUser(User user) {
        int res=userDao.addUser(user);
        if (res>0){
            return "success";
        }
        return "fail";
    }

    public String deleteUser(User user) {
        int res=userDao.deleteUser(user);
        if (res>0){
            return "success";
        }
        return "fail";
    }
}
