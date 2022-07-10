package com.bysj.base.service.admin;

import com.bysj.base.bean.CodeMsg;
import com.bysj.base.bean.PageBean;
import com.bysj.base.bean.Result;
import com.bysj.base.dao.admin.RepairsDao;
import com.bysj.base.entity.admin.Emp;
import com.bysj.base.entity.admin.EmpType;
import com.bysj.base.entity.admin.Repairs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class RepairsService {

    @Autowired
    private RepairsDao repairsDao ;


    /**
     * 根据Id 查询分配信息
     * @param id
     * @return
     */
    public Repairs findByID(Long id){
        /**
         * 使用jpa自带的方法
         *         studentDao.getOne();当我查询一个不存在的id数据时，直接抛出异常，因为它返回的是一个引用，简单点说就是一个代理对象
         *         studentDao.findOne() 当我查询一个不存在的id数据时，返回的值是null.
         *         studentDao.findById(id) ; .findById(id).get()使用时，如果数据库中查询无符合条件的记录便会抛出异常
         */

        Optional<Repairs> byId = repairsDao.findById(id);

        return  byId.isPresent()?byId.get():null;
    }
    /**
     * 根据Id 查询分配信息
     * @param
     * @return
     */
    public List<Repairs> findAll(){
        return  repairsDao.findAll();
    }
    /**
     * 根据Id 删除
     * @param id
     * @return
     */
    public void delete(long id ){
        repairsDao.deleteById(id);
    }
    /**
     * 修改分配信息
     * @param
     * @return
     */
    public Repairs save(Repairs repairs){
        return repairsDao.save(repairs);
    }

    /**
     * 根据Id查找空闲的维修工Id
     * @param id
     * @return
     */
    public Long findNull(Long id){
        List<BigInteger> ids = repairsDao.findNull(id);

        if (CollectionUtils.isEmpty(ids)) {
            return null;
        }
        return ids.get(0).longValue();
    }

    /**
     * 根据Id查找任务量最少的的维修工Id
     * @param id
     * @return
     */
    public  Long  findEmpId(Long id){
        return  repairsDao.findEmpId(id);
    }


    /**
     * 根据BuildingId和empType查找任务量最少的的维修工Id
     * @param id empType
     * @return
     */
    public  Long  findEmpByBuildingAndEmpType(Long id,int empType){
        return repairsDao.findEmpByBuildingAndEmpType(id, empType);
    }
    /**
     * 根据BuildingId和empType查找空闲师傅的维修工Id
     * @param id empType
     * @return
     */
    public  Long findFreeEmpByBuildingAndEmpType(Long id,int empType){
        List<BigInteger> ids = repairsDao.findFreeEmpByBuildingAndEmpType(id, empType);
        if (CollectionUtils.isEmpty(ids)) {
            return null;
        }
        return ids.get(0).longValue();
    }

    public List<Repairs> findByDormitoryIdAndEmpTypeAndStatus(Long dormitoryId, EmpType empType,int status){
        return repairsDao.findByDormitoryIdAndEmpTypeAndStatus(dormitoryId, empType,status);
    }

    /**
     *修改维修工的Id
     *
     * @return
     */


    public Repairs  updateEmpId(Repairs repairs){
        return repairsDao.save(repairs);
    }

    /**
     * 根据ID查询所有的维修工
     * @param id
     * @return
     */

    public  List<Emp> findAllEmpId(long id){

        List<Object> allEmp = repairsDao.findAllEmpId(id);

        List<Emp> emps = new ArrayList<>();

        //遍历添加数据
        for (Object o : allEmp) {
            Object[] group = (Object[]) o;

            Emp emp = new Emp();
            emp.setId(Long.valueOf(group[0].toString()));
            emp.setName(group[1].toString());

            emps.add(emp);
        }
        return emps;

    }

    /**
     * 分页查询 分配任务列表
     * @param
     * @param pageBean
     * @return
     */
    public PageBean<Repairs> findByName(Repairs repairs, PageBean<Repairs> pageBean){
        ExampleMatcher withMatcher = ExampleMatcher.matching().withMatcher("student.id", ExampleMatcher.GenericPropertyMatchers.contains());
        withMatcher = withMatcher.withIgnorePaths("empType","emp","status","student.dormitory","student.studentSex","student.counsellorId","student.role");
        Example<Repairs> example = Example.of(repairs, withMatcher);
        Pageable pageable = PageRequest.of(pageBean.getCurrentPage()-1, pageBean.getPageSize());
        Page<Repairs> findAll = repairsDao.findAll(example, pageable);
        pageBean.setContent(findAll.getContent());
        pageBean.setTotal(findAll.getTotalElements());
        pageBean.setTotalPage(findAll.getTotalPages());
        return pageBean;
    }


    /**
     * 分页查询 分配任务列表
     * @param
     * @param pageBean
     * @return
     */

    public PageBean<Repairs> findAllPage(PageBean<Repairs> pageBean) {

        Pageable pageable = PageRequest.of(pageBean.getCurrentPage()-1, pageBean.getPageSize());
        Page<Repairs> findAll = repairsDao.findAll(pageable);
        pageBean.setContent(findAll.getContent());
        pageBean.setTotal(findAll.getTotalElements());
        pageBean.setTotalPage(findAll.getTotalPages());
        return pageBean;
    }

    /**
     * 根据Id查询 所有的信息
     * @param
     * @return
     */
    public List<Repairs> findByEmpId(Long  Id) {
         return repairsDao.findByEmpId(Id);
    }
}
