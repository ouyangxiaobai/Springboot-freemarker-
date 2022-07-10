package com.bysj.base.dao.admin;

import com.bysj.base.entity.admin.Building;
import com.bysj.base.entity.admin.Emp;
import com.bysj.base.entity.admin.EmpType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface EmpDao extends JpaRepository<Emp, Long> {

    /**
     * 通过员工id查询员工信息
     * @param id
     * @return
     */
    @Query("select e from Emp e where e.id = :id")
    Emp find(@Param("id") Long id);


    /**
     * 按照员工工号查询员工信息
     * @param
     * @return
     */
    Emp findByNumber(String number);


    /***
     * 查找员工信息通过员工名字
     * @param name
     * @return
     */
   Emp findByName(String name);

   @Query("select e from Emp e where e.building = :building")
   List<Emp> findByBuilding(@Param("building") Building building);

  @Query("SELECT id from Emp e WHERE e.empType=2")
   List<Long> findByEmpType(@Param("empType") EmpType empType);

}
