package com.bysj.base.entity.admin;

import com.bysj.base.annotion.ValidateEntity;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name="bysj_emp")
@EntityListeners(AuditingEntityListener.class)
public class Emp extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private static final int USER_SEX_MAN = 1;//性别男

    private static final int EMP_SEX_WOMAN = 2;//性别女

    private static final int EMP_SEX_UNKONW = 0;//性别未知


    @ValidateEntity(required = true,errorRequiredMsg="员工姓名不能为空!",minLength = 2,errorMinLengthMsg=("员工姓名必须大于两个字符小于20个字符!"),
            maxLength = 20,errorMaxLengthMsg=("员工姓名必须大于两个字符小于20个字符!"))
    @Column(name = "name")
    private String name; //员工姓名

    @ValidateEntity(required=false)
    @Column(name = "sex", length = 1)
    private int sex = EMP_SEX_WOMAN;//员工性别

    @ValidateEntity(required=true,errorRequiredMsg="员工工号不能为空!")
    @Column(name = "number")
    private String number;//员工工号

    @ValidateEntity(required=true,errorRequiredMsg="员工密码不能为空!")
    @Column(name = "password")
    private String password;//员工密码

    @ValidateEntity(required=false)
    @Column(name="mobile",length=12)
    private String mobile;//员工手机号

    @ValidateEntity(required=false)
    @Column(name="head_pic",length=128)
    private String headPic;//员工头像

    @ManyToOne
    @JoinColumn(name="building_bno")
    private Building building;//楼栋

    @Enumerated
    @Column(name = "emp_type",length = 3)
    private EmpType empType;//员工种类

    @ValidateEntity(required=true)
    @Column(name="email",length=32)
    private String email;//员工邮箱


    @ManyToOne
    @JoinColumn(name="role_id")
    private Role role;//员工所属角色


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public EmpType getEmpType() {
        return empType;
    }

    public void setEmpType(EmpType empType) {
        this.empType = empType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Emp{" +
                "name='" + name + '\'' +
                ", sex=" + sex +
                ", number='" + number + '\'' +
                ", mobile='" + mobile + '\'' +
                ", headPic='" + headPic + '\'' +
                ", building=" + building +
                ", empType=" + empType +
                ", email='" + email + '\'' +
                ", role=" + role +
                '}';
    }
}
