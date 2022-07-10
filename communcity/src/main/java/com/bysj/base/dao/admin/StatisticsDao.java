package com.bysj.base.dao.admin;


import com.bysj.base.entity.admin.Emp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface StatisticsDao extends JpaRepository<Emp,Long> {

    //按照楼栋统计任务量
    @Query(value = "SELECT c.building_id,count(c.building_id)\n" +
            "FROM\n" +
            "(select b.id,b.building_id\n" +
            "FROM bysj_repairs a,bysj_dormitory b\n" +
            "WHERE a.dormitory_id=b.id) c\n" +
            "GROUP BY c.building_id\n ", nativeQuery = true)
    List<Object> countBuilding();

    //按照维修工类型统计任务量
    @Query(value = "SELECT emp_type , count(emp_type) FROM bysj_repairs GROUP BY emp_type ", nativeQuery = true)
     List<Object> countByEmpType();

    //按照维修工统计任务量
    @Query(value = "SELECT emp_id, count(emp_id) FROM bysj_repairs GROUP BY emp_id", nativeQuery = true)
     List<Object> countByEmp();


}
