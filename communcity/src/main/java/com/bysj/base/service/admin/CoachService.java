package com.bysj.base.service.admin;

import com.bysj.base.bean.PageBean;
import com.bysj.base.dao.admin.CoachDao;
import com.bysj.base.entity.admin.Building;
import com.bysj.base.entity.admin.Coach;
import com.bysj.base.entity.admin.Menu;
import com.bysj.base.entity.admin.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Service
public class CoachService {
    @Autowired
    private CoachDao coachDao;

    public PageBean<Coach> findlist(Coach coach, PageBean<Coach> pageBean){
        ExampleMatcher withMatcher = ExampleMatcher.matching().withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains());
        withMatcher = withMatcher.withIgnorePaths("sex");
        Example<Coach> example = Example.of(coach, withMatcher);
        Pageable pageable = PageRequest.of(pageBean.getCurrentPage()-1, pageBean.getPageSize());
        Page<Coach> findAll =coachDao .findAll(example, pageable);
        pageBean.setContent(findAll.getContent());
        pageBean.setTotal(findAll.getTotalElements());
        pageBean.setTotalPage(findAll.getTotalPages());
        return pageBean;
    }

    public  List<Coach> findAll(){
        return coachDao.findAll();
    }
    /**
     * 根据用户id查询
     * @param id
     * @return
     */
    public Coach find(Long id){
        return coachDao.find(id);
    }

    /**
     * 按照用户名查找用户
     * @param name
     * @return
     */
    public Coach findByname(String name){
        return coachDao.findByname(name);
    }

    /**
     * 判断用户名是否存在，添加和编辑均可判断
     * @param name
     * @param id
     * @return
     */
    public boolean isExistname(String name,Long id){
        Coach coach = coachDao.findByname(name);
        if(coach != null){
            //表示用户名存在，接下来判断是否是编辑用户的本身
            if(coach.getId().longValue() != id.longValue()){
                return true;
            }
        }
        return false;
    }

    /**
     * 用户添加/编辑操作
     * @param coach
     * @return
     */
    public Coach save(Coach coach){
        return coachDao.save(coach);
    }

    /**
     * 按照用户id删除
     * @param id
     */
    public void delete(Long id){
        coachDao.deleteById(id);
    }
    /**
     * 返回用户总数
     * @return
     */
    public long total(){
        return coachDao.count();
    }
}
