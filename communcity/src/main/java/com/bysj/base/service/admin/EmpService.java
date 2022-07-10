package com.bysj.base.service.admin;

import com.bysj.base.bean.PageBean;
import com.bysj.base.dao.admin.EmpDao;
import com.bysj.base.dao.admin.RoleDao;
import com.bysj.base.dao.admin.UserDao;
import com.bysj.base.entity.admin.Building;
import com.bysj.base.entity.admin.Emp;
import com.bysj.base.entity.admin.EmpType;
import com.bysj.base.entity.admin.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmpService {


    @Autowired
   private EmpDao empDao;

    /**
     * 判断工号是否存在，添加和编辑均可判断
     * @param
     * @param id
     * @return
     */
    public boolean isExistUsername(String number,Long id){
        Emp emp = empDao.findByNumber(number);
        if(emp != null){
            //表示用户名存在，接下来判断是否是编辑用户的本身
            if(emp.getId().longValue() != id.longValue()){
                return true;
            }
        }
        return false;
    }

    /**
     *  根据员工工号查找员工信息
     * @param number
     * @return
     */


    public Emp findByNumber(String number){
        return  empDao.findByNumber(number);
    }
    /**
     * 员工添加/编辑
     * @param emp
     * @return
     */
    @Transactional(rollbackFor=Exception.class)
    public Emp save(Emp emp){
        return empDao.save(emp);
    }

   public List<Long> findByEmpType(EmpType empType){
        return  empDao.findByEmpType(empType);
   }

    /**
     * 获取所有员工列表
     * @return
     */
    public List<Emp> findAll(){
        return empDao.findAll();
    }

    /**
     * 根据id查询员工
     * @param id
     * @return
     */
    public Emp find(Long id){
        return empDao.find(id);
    }


    /**
     * 根据id删除一条员工记录
     * @param id
     */
    public void delete(Long id){
        empDao.deleteById(id);
    }

    /**
     * 分页按员工名称搜索员工列表
     * @param emp
     * @param pageBean
     * @return
     */
    public PageBean<Emp> findByName(Emp emp, PageBean<Emp> pageBean){

        ExampleMatcher withMatcher = ExampleMatcher.matching().withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains());
        withMatcher = withMatcher.withIgnorePaths("sex","take");
        Example<Emp> example = Example.of(emp, withMatcher);
        Pageable pageable = PageRequest.of(pageBean.getCurrentPage()-1, pageBean.getPageSize());
        Page<Emp> findAll = empDao.findAll(example, pageable);
        pageBean.setContent(findAll.getContent());
        pageBean.setTotal(findAll.getTotalElements());
        pageBean.setTotalPage(findAll.getTotalPages());
        return pageBean;
    }

        public Emp findByName(String name){
      return empDao.findByName(name);
    }


    public List<Emp> findByBuilding(Building building) {
        return empDao.findByBuilding(building);
    }



}
