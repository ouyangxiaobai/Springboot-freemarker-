package com.bysj.base.service.admin;

import com.bysj.base.bean.PageBean;
import com.bysj.base.dao.admin.StudentDao;
import com.bysj.base.entity.admin.Student;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    private StudentDao studentDao ;


    public Student findByID(Long id){
        /**
         * 使用jpa自带的方法
         *         studentDao.getOne();当我查询一个不存在的id数据时，直接抛出异常，因为它返回的是一个引用，简单点说就是一个代理对象
         *         studentDao.findOne() 当我查询一个不存在的id数据时，返回的值是null.
         *         studentDao.findById(id) ; .findById(id).get()使用时，如果数据库中查询无符合条件的记录便会抛出异常
         */

        Optional<Student> byId = studentDao.findById(id);

        return  byId.isPresent()?byId.get():null;
    }

    public List<Student> findAll(){
        return  studentDao.findAll();
    }

    public void delete(long id ){
        studentDao.deleteById(id);
    }

    public Student save(Student student){
        return studentDao.save(student);
    }

    /**
     * 分页按角色名称搜索角色列表
     * @param student
     * @param pageBean
     * @return
     */
    public PageBean<Student> findByName(Student student, PageBean<Student> pageBean){
        ExampleMatcher withMatcher = ExampleMatcher.matching().withMatcher("studentName", ExampleMatcher.GenericPropertyMatchers.contains());
        withMatcher = withMatcher.withIgnorePaths("studentSex","counsellorId","dormitoryId");
        Example<Student> example = Example.of(student, withMatcher);
        Pageable pageable = PageRequest.of(pageBean.getCurrentPage()-1, pageBean.getPageSize());
        Page<Student> findAll = studentDao.findAll(example, pageable);
        pageBean.setContent(findAll.getContent());
        pageBean.setTotal(findAll.getTotalElements());
        pageBean.setTotalPage(findAll.getTotalPages());
        return pageBean;
    }

    /**
     * 跳轉學生添加
     * @return
     */
    @RequestMapping(value="/add",method= RequestMethod.GET)
    public String add(){

        return "admin/student/add";
    }

    /**
     * 根据学号查询
     * @param no
     * @return
     */
    public Student findByStudentNo(String no){
        return studentDao.findByStudentNo(no);
    }

    /**
     * 根据名字查查询
     * @param StuName
     * @return
     */
    public Student  findByStudentName(String StuName){
        return studentDao.findByStudentName(StuName);
    }

    /**
     * 返回用户总数
     * @return
     */
    public long total(){
        return studentDao.count();
    }
}
