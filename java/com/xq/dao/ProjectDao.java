package com.xq.dao;

import com.xq.model.Project;
import com.xq.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by lu on 2017/4/11.
 */
@Repository
public interface ProjectDao {
    List<Project> selectProject(String user);
    int updateid(User user);
    Integer selet_recentid(User user);
    int update_recentid(User user);
    int deleteProject(Project project);
    int deleteMenu(int projectid);
    int deleteXml(int projectid);
    int deletePP(int projectid);

}
