package com.bysj.base.controller.admin;

import com.bysj.base.bean.CodeMsg;
import com.bysj.base.bean.PageBean;
import com.bysj.base.bean.Result;
import com.bysj.base.constant.SessionConstant;
import com.bysj.base.dao.admin.CoachDao;
import com.bysj.base.dao.admin.RoleDao;
import com.bysj.base.entity.admin.*;
import com.bysj.base.listener.SessionListener;
import com.bysj.base.service.admin.*;
import com.bysj.base.util.StringUtil;
import com.bysj.base.util.ValidateEntityUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequestMapping("/stu")
@Controller
public class StudentController {
    @Autowired
   private StudentService studentService;
    @Autowired
    private DormitoryService  dormitoryService;
    @Autowired
    CoachDao coachDao;
    @Autowired
    private RoleService roleService  ;


    @RequestMapping(value="/list")
    public String list(Model model, Student student, PageBean<Student> pageBean){
        model.addAttribute("title", "学生列表");
        model.addAttribute("name", student.getStudentName());
        model.addAttribute("pageBean", studentService.findByName(student, pageBean));
        return "admin/student/list";
    }

    /**
     * 学生修改
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(value="/edit",method=RequestMethod.GET)
    public String edit(Model model,@RequestParam(name="id")Long id){
        Student byID = studentService.findByID(id);
        List<Building> all2 = buildingService.findAll();
        List<Dormitory> byBuilding_id = dormitoryService.findByBuilding_Id(byID.getDormitory().getBuilding().getId());
        List<Coach> all1 = coachDao.findAll();
        model.addAttribute("coachs",all1);
        model.addAttribute("dormitorys",byBuilding_id);
        model.addAttribute("buildings",all2);
        model.addAttribute("student",byID );
        return "admin/student/edit";
    }

    @Autowired
   private BuildingService buildingService;
    /**
     * 角色添加页面
     * @param
     * @return
     */
    @RequestMapping(value="/add",method= RequestMethod.GET)
    public String add(Model model){
        //查出所有的宿舍
        List<Dormitory> all = dormitoryService.findAll();
        //查出所有辅导员
        List<Building> all2 = buildingService.findAll();

        List<Coach> all1 = coachDao.findAll();
        //model.addAttribute("dormitorys",all);
        model.addAttribute("coachs",all1);
        model.addAttribute("buildings",all2);

        return "admin/student/add";
    }

    @RequestMapping(value="delete",method= RequestMethod.POST)
    @ResponseBody
    public Result delete(long id){
        try {
            studentService.delete(id);
        } catch (Exception e) {

            return Result.error(CodeMsg.ADMIN_ROLE_DELETE_ERROR);
        }

        return Result.success(true);
    }
    @RequestMapping(value="/building",method= RequestMethod.POST)
    @ResponseBody
    public Result getuilding(long id){
        List<Dormitory> byBuilding_id = dormitoryService.findByBuilding_Id(id);
        return Result.success(byBuilding_id);
    }


    /**
     * 编辑用户信息表单提交处理
     * @param
     * @return
     */
    @RequestMapping(value="/add",method=RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> add(Student student){


        /**
         * 有id是修改需要判断
         * 无id是增加无需判断
         */
        if (student.getId()!=null){
            //用统一验证实体方法验证是否合法
            CodeMsg validate = ValidateEntityUtil.validate(student);
            if(validate.getCode() != CodeMsg.SUCCESS.getCode()){
                return Result.error(validate);
            }
            //到这说明一切符合条件，进行数据库保存
            Student byID = studentService.findByID(student.getId());
            //讲提交的用户信息指定字段复制到已存在的user对象中,该方法会覆盖新字段内容
            BeanUtils.copyProperties(student,byID, "id","createTime","updateTime","studentNo","role","password");
            studentService.save(byID);
        }else {
           if (!StringUtil.emailFormat(student.getEmail())){
            return   Result.error(CodeMsg.ADMIN_STUDENT_EMAIL);
           }
            if (!StringUtil.isMobile(student.getMobile())){
                return   Result.error(CodeMsg.ADMIN_STUDENT_MOBILE);
            }
            student.setRole(roleService.findByRoleType(RoleType.STUDENT));
            String s = StringUtil.generateSn("S", "");
            student.setStudentNo(s);
            student.setPassword(s);
            studentService.save(student);
        }
        return Result.success(true);
    }


    @Autowired
    private OperaterLogService operaterLogService;


    private Logger log = LoggerFactory.getLogger(SystemController.class);




}
