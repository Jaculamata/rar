package com.xq.service.impl;

import com.xq.dao.ProjectDao;
import com.xq.model.Project;
import com.xq.model.User;
import com.xq.service.ProjectService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by lu on 2017/4/10.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ProjectServiceImpl implements ProjectService{
    @Resource
    private ProjectDao projectDao;


    public List<Project> selectProject(String user) {
        return projectDao.selectProject(user);
    }


    public int updateid(User user) {
        //System.out.println("user:"+user.getUser()+"proid:"+user.getProjectid());
        return projectDao.updateid(user);
    }

    public int update_recentid(User user) {
        Integer recentid=projectDao.selet_recentid(user);
        System.out.println("recentid====="+recentid);
        if (recentid==null){
            return 0; //最近没有打开项目
        }
        else if (recentid==user.getProjectid()){

            projectDao.update_recentid(user);
            System.out.println("俩个projectid一样");
            return 1;
        }else {
            System.out.println("俩个projectid不一样");
            return 2;
        }

    }


    public int deleteProject(Project project) {
//        先删除project表中的
        int res=projectDao.deleteProject(project);
        if (res>0){
//            在删除menu表中的菜单
            int projectid=project.getProjectid();
            projectDao.deleteMenu(projectid);
            projectDao.deleteXml(projectid);
            projectDao.deletePP(projectid);
            return 1;
        }else {
            return 0;
        }

    }
}
