package com.bysj.base.entity.admin;



import com.bysj.base.annotion.ValidateEntity;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 *登录如何判断是学生或者是维修师傅
 *  x1.登录进来就是用户然后安排身份
 *  2.两种登录 分成学生登录和维修师傅登录
 *  x3.做一个用户的表负责 密码和账号
 */
@Entity
@Table( name ="bysj_student")
@EntityListeners(AuditingEntityListener.class)
public class Student extends BaseEntity {

    //姓名、性别、学号、对应辅导员、以及相应的宿舍
    /**
     * 姓名
     */
    @ValidateEntity(required=true,requiredLeng=true,minLength=1,maxLength=18,errorRequiredMsg="角色名称不能为空!",errorMinLengthMsg="角色名称长度需大于1!",errorMaxLengthMsg="角色名称长度不能大于18!")
    @Column(name = "student_name"  )
    private String studentName;

    /**
     * 性别
     */
    @Column(name = "student_sex" )
    private int studentSex;
    /**
     * 学号 、 账号
     */
    @Column(name = "student_no" )
    private String studentNo;


    @ValidateEntity(required=true,errorRequiredMsg="员工密码不能为空!")
    @Column(name = "password")
    private String password;//学生密码


    /**
     * 辅导员 id
     *对应辅导员id
     */
    @JoinColumn(name = "counsellor_id")
    @ManyToOne
    private Coach counsellorId ;

    /**
     * 宿舍號
     */
    //映射多对一的关联关系
    //使用 @ManyToOne来应收多对一的关联关系
    //使用
    @JoinColumn(name = "room_id")
    @ManyToOne
    private  Dormitory dormitory;

    /**
     * 学生邮箱
     */
    @Column(name="email",length=32)
    @ValidateEntity(required=true)
    private String email;//用户邮箱


    /**
     /**
     * 联系方式必填
     */
    @Column(name = "mobile" )
    @ValidateEntity(required=true,requiredLeng=true,minLength=11,maxLength=11,errorRequiredMsg="联系方式不能为空!",errorMinLengthMsg="联系方式长度需大于11!",errorMaxLengthMsg="联系方式长度不能大于11!")
    private String  mobile;

    @ValidateEntity(required=false)
    @Column(name="head_pic",length=128)
    private String headPic;//用户头像

    @ManyToOne
    @JoinColumn(name="role_id")
    private Role role;//用户所属角色

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public int getStudentSex() {
        return studentSex;
    }

    public void setStudentSex(int studentSex) {
        this.studentSex = studentSex;
    }

    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    public Coach getCounsellorId() {
        return counsellorId;
    }

    public void setCounsellorId(Coach counsellorId) {
        this.counsellorId = counsellorId;
    }

    public Dormitory getDormitory() {
        return dormitory;
    }

    public void setDormitory(Dormitory dormitory) {
        this.dormitory = dormitory;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentName='" + studentName + '\'' +
                ", studentSex=" + studentSex +
                ", studentNo='" + studentNo + '\'' +
                ", password='" + password + '\'' +
                ", counsellorId=" + counsellorId +
                ", dormitory=" + dormitory +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", headPic='" + headPic + '\'' +
                ", role=" + role +
                '}';
    }
}
