package com.bysj.base.entity.admin;



import javax.persistence.*;

@Entity
@Table(name = "bysj_coach")
public class Coach extends BaseEntity{

    @Column(name = "name")
    private  String  name;

    @Column(name = "sex")
    private  int  sex;

    @Column(name = "phone")
    private  String phone;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "Coach{" +
                "name='" + name + '\'' +
                ", sex=" + sex +
                ", phone=" + phone +
                '}';
    }
}
