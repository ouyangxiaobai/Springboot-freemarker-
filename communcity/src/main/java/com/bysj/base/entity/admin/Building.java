package com.bysj.base.entity.admin;

import com.bysj.base.annotion.ValidateEntity;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * 楼栋信息
 */

@Entity
@Table(name="bysj_building")
@EntityListeners(AuditingEntityListener.class)
public class Building extends BaseEntity{

    private static final long serialVersionUID = 1L;

    @ValidateEntity(required = true, errorRequiredMsg = "楼号不能为空")
    @Column(name = "bno", nullable = false, length = 4)
    private Integer bno;//楼号

    @ValidateEntity(required = true, errorRequiredMsg = "校区不能为空")
    @Column(name = "campus", nullable = false, length = 128)
    private String campus;//校区

    @ValidateEntity(required = true, errorRequiredMsg = "地址不能为空")
    @Column(name = "address", nullable = false, length = 128)
    private String address;//地址

    public Integer getBno() {
        return bno;
    }

    public void setBno(Integer bno) {
        this.bno = bno;
    }

    public String getCampus() {
        return campus;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Building{" +
                "bno=" + bno +
                ", campus='" + campus + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
