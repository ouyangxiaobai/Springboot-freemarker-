package com.bysj.base.dao.admin;

import com.bysj.base.entity.admin.Emp;
import com.bysj.base.entity.admin.EmpType;
import com.bysj.base.entity.admin.Repairs;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigInteger;
import java.util.List;

@Repository
public interface RepairsDao extends JpaRepository<Repairs, Long> {

    /**
     * 根据Id查找空闲的维修工Id
     * @param id
     * @return
     */
    @Query(value = "select suoyou.all_id\n" +
            "FROM \n" +
            "(SELECT c.id as all_id\n" +
            "FROM bysj_emp c , (SELECT b.building_id,a.emp_type FROM `bysj_repairs` a , bysj_dormitory b WHERE a.dormitory_id=b.id and a.id= :id) d \n" +
            "WHERE c.building_bno=d.building_id and d.emp_type=c.emp_type) AS suoyou\n" +
            "WHERE suoyou.all_id NOT IN (\n" +
            "SELECT DISTINCT f.emp_id as part_id\n" +
            "FROM bysj_repairs f, \n" +
            "(SELECT c.id FROM bysj_emp c , (SELECT b.building_id,a.emp_type FROM `bysj_repairs` a , bysj_dormitory b WHERE a.dormitory_id=b.id and a.id= :id) d \n" +
            "WHERE c.building_bno=d.building_id and d.emp_type=c.emp_type) g\n" +
            "WHERE f.emp_id=g.id AND f.status=0 )" ,nativeQuery = true)
     public List<BigInteger> findNull(@Param("id")Long id);


    /**
     * 根据Id查找空闲的维修工Id
     * @param id
     * @return
     */
    @Query(value = "select suoyou.all_id\n" +
            "FROM \n" +
            "(SELECT c.id as all_id\n" +
            "FROM bysj_emp c  \n" +
            "WHERE  c.building_bno= :id and c.emp_type= :empType) AS suoyou\n" +
            "WHERE suoyou.all_id NOT IN (\n" +
            "SELECT DISTINCT f.emp_id as part_id\n" +
            "FROM bysj_repairs f, \n" +
            "(SELECT c.id FROM bysj_emp c \n" +
            "WHERE c.building_bno= :id and c.emp_type= :empType) g\n" +
            "WHERE f.emp_id=g.id AND f.status=0 )" ,nativeQuery = true)
      List<BigInteger> findFreeEmpByBuildingAndEmpType(@Param("id")Long id,@Param("empType") int empType);


    /**
     * 根据Id查找任务量最少的的维修工Id
     * @param id
     * @return
     */
    @Query(value = "SELECT z.emp_id \n" +
            "FROM (SELECT f.emp_id \n" +
            "FROM bysj_repairs f, \n" +
            "(SELECT c.id FROM bysj_emp c , (SELECT b.building_id,a.emp_type FROM `bysj_repairs` a , bysj_dormitory b WHERE a.dormitory_id=b.id and a.id=?) d \n" +
            "WHERE c.building_bno=d.building_id  and d.emp_type=c.emp_type) g\n" +
            "WHERE f.emp_id=g.id and f.status=0) z\n" +
            "GROUP BY z.emp_id \n" +
            "ORDER BY count(z.emp_id) asc\n" +
            "LIMIT 1",nativeQuery = true)
    public  Long findEmpId(@Param("id")Long id);

    /**
     * 根据BuildingId和empType查找任务量最少的的维修工Id
     * @param id
     * @return
     */
    @Query(value = "SELECT z.emp_id \n" +
            "FROM (SELECT f.emp_id \n" +
            "FROM bysj_repairs f, \n" +
            "(SELECT c.id FROM bysj_emp c \n" +
            "WHERE c.building_bno= :id  and c.emp_type= :empType) g\n" +
            "WHERE f.emp_id=g.id and f.status=0) z\n" +
            "GROUP BY z.emp_id\n" +
            "ORDER BY count(z.emp_id) asc\n" +
            "LIMIT 1",nativeQuery = true)
     Long findEmpByBuildingAndEmpType(@Param("id")Long id,@Param("empType") int empType);


    /**
     * 根据Id查找同一楼号该类的所有维修工Id
     * @param id
     * @return
     */
    @Query(value = "SELECT c.id ,c.name\n" +
            "FROM bysj_emp c , (SELECT b.building_id,a.emp_type FROM `bysj_repairs` a , bysj_dormitory b WHERE a.dormitory_id=b.id and a.id= :id) d \n" +
            "WHERE c.building_bno=d.building_id and d.emp_type=c.emp_type",nativeQuery = true)
    public  List<Object>  findAllEmpId(@Param("id")long id);

    /**
     * 根据维修工Id 查询维修工的信息
     * @param Id
     * @return
     */
    public List<Repairs> findByEmpId(Long  Id);

    List<Repairs> findByDormitoryIdAndEmpTypeAndStatus(Long dormitoryId, EmpType empType,int status);


}
