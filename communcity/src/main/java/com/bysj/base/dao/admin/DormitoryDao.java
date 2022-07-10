package com.bysj.base.dao.admin;

import com.bysj.base.entity.admin.Dormitory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/***
 * 宿舍数据处理层
 */
@Repository
public interface DormitoryDao extends JpaRepository<Dormitory,Long> {


    @Query("select d from Dormitory d where id = :id")
    Dormitory find(@Param("id") Long id);

   /* *//**
     *  根据所属楼栋和房间号查询
     * @param buildingId
     * @param roomNo
     * @return
     *//*
    @Query(value = "select * from bysj_building b,bysj_dormitory d where b.id = d.building_id and b.id= :buildingId and d.room_no= :roomNo" ,nativeQuery = true)
    Dormitory findByBuildingIdAndRoomNo(@Param("buildingId") Long buildingId , @Param("roomNo") Integer roomNo);
*/
    List<Dormitory> findByBuilding_Id(Long id);
    Dormitory findByRoomNo(@Param("roomNo") Integer roomNo);

    Dormitory findByBuildingIdAndRoomNo(@Param("buildingId") Long buildingId, @Param("roomNo") Integer roomNo);
}
