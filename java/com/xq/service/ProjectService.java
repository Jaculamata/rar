package com.xq.service;

import com.xq.model.Project;
import com.xq.model.User;

import java.util.List;

public interface ProjectService {
    //4.11lu,查找project默认值
    List<Project> selectProject(String user);

    //4.12lu,插入最近打开项目的Projectid
    int updateid(User user);
    //4.13lu,如果删除的这个项目正好是最近打开的项目，则清空recent_projectid
    int update_recentid(User user);
    //4.13lu,删除项目时删除project表中的内容
    int deleteProject(Project project);
}
