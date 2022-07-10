package com.bysj.base.entity.admin;


import com.bysj.base.annotion.ValidateEntity;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table( name ="bysj_repairs")
@EntityListeners(AuditingEntityListener.class)
public class Repairs  extends BaseEntity{

    @JoinColumn(name = "student_id")
    @ManyToOne
    private Student student;



    //映射多对一的关联关系
    //使用 @ManyToOne来应收多对一的关联关系
    //使用
    /***
     * 宿舍信息
     */
    @JoinColumn(name = "dormitory_id")
    @ManyToOne
    private  Dormitory dormitory;
    /**
     * 维修分类
     */
    @Enumerated
    @Column(name = "emp_type", length = 2)
    private EmpType empType;
    /***
     * 维修工信息
     */
    @JoinColumn(name = "emp_id")
    @ManyToOne
    private Emp emp;
    /***
     * 状态
     */
   @Column(name = "status")
    private int status; //0 未完成 1完成
    @Column(name = "repairs_remark" )
    private String  remark;

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Dormitory getDormitory() {
        return dormitory;
    }

    public void setDormitory(Dormitory dormitory) {
        this.dormitory = dormitory;
    }

    public EmpType getEmpType() {
        return empType;
    }

    public void setEmpType(EmpType empType) {
        this.empType = empType;
    }

    public Emp getEmp() {
        return emp;
    }

    public void setEmp(Emp emp) {
        this.emp = emp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "Repairs{" +
                "student=" + student +
                ", dormitory=" + dormitory +
                ", empType=" + empType +
                ", emp=" + emp +
                ", status=" + status +
                ", remark='" + remark + '\'' +
                '}';
    }
}
