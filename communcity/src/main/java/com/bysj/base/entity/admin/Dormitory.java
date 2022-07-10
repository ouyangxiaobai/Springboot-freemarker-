package com.bysj.base.entity.admin;

import com.bysj.base.annotion.ValidateEntity;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * 宿舍信息
 */
@Entity
@Table(name = "bysj_dormitory")
@EntityListeners(AuditingEntityListener.class)
public class Dormitory extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "building_id",nullable = false)
    @ValidateEntity(required = true , errorRequiredMsg="所属楼栋不能为空!")
    private  Building  building;  //楼栋



    @Column(name = "room_No",nullable=false,length=6)
    @ValidateEntity(required = true  , errorRequiredMsg="房间号不能为空!")
    private  Integer roomNo;   //房间号

    @Column(name = "note",nullable = false,length = 20)
    private  String  note;   //备注


    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public Integer getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(Integer roomNo) {
        this.roomNo = roomNo;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "Dormitory{" +
                "building=" + building +
                ", roomNo=" + roomNo +
                ", note='" + note + '\'' +
                '}';
    }

}
