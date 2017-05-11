package com.xq.service;

import com.xq.model.User;

import java.util.List;

public interface UserService {
    String log(User user);
    List<User> getAllUser();
    //3.30查询用户名是否已经存在
    String getUserName(String username);
    //3.30修改密码
    String getPwd(User user);
    String changePwd(User user);
    //3.30管理员查询所有测试员信息
    List<User> getTestUser(String role);
    //3.30管理员新增用户
    String addUser(User user);
    //3.30管理员删除用户
    String deleteUser(User user);
    //3.31查找用户
    List<User> selectUser(String username);
}
