package com.xq.dao;

import com.xq.model.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UserDao {
    List<User> log(User user);
    List<User> getAllUser();
    //3.30查询用户名是否已经存在
    List<User> getUserName(String username);
    //3.30修改密码
    List<User> getPwd(User user);
    int changePwd(User user);
    //3.30管理员获取所有用户
    List<User> getTestUser(String role);
    //3.30管理员新增用户
    int addUser(User user);
    //3.30管理员删除用户
    int deleteUser(User user);
}
