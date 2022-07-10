package com.bysj.base.controller.admin;

import com.bysj.base.bean.CodeMsg;
import com.bysj.base.bean.PageBean;
import com.bysj.base.bean.Result;
import com.bysj.base.entity.admin.*;
import com.bysj.base.service.admin.*;
import com.bysj.base.util.SessionUtil;
import com.bysj.base.util.ValidateEntityUtil;
import org.aspectj.apache.bcel.classfile.Code;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/req")
public class RepairsController {
    @Autowired
    private RepairsService repairsService;
     @Autowired
     private StudentService studentService;

    @Autowired
    private EmpService empService;

    @Autowired
    private DormitoryService dormitoryService;

     @Autowired
     private MailService mailService;


    @RequestMapping(value="/list")
    public String list(Model model, Repairs repairs, PageBean<Repairs> pageBean){
        User loginedUser = SessionUtil.getLoginedUser();
        Student byID = studentService.findByID(loginedUser.getId());
        repairs.setStudent(byID);
        model.addAttribute("title", "维修管理");
        model.addAttribute("pageBean", repairsService.findByName(repairs, pageBean));
        return "admin/repairs/list";
    }
    @RequestMapping(value="delete",method= RequestMethod.POST)
    @ResponseBody
    public Result delete(long id){
        try {
            repairsService.delete(id);
        } catch (Exception e) {

            return Result.error(CodeMsg.ADMIN_ROLE_DELETE_ERROR);
        }

        return Result.success(true);
    }

    /**
     * 添加页面
     * @param
     * @return
     */
    @RequestMapping(value="/add",method= RequestMethod.GET)
    public String add(Model model, HttpServletRequest request){
        Student admin_stu =(Student)request.getSession().getAttribute("admin_stu");


        List<Emp> emps = empService.findByBuilding(admin_stu.getDormitory().getBuilding());

        List<EmpType> empTypes = emps.stream().map(o -> o.getEmpType()).distinct().collect(Collectors.toList());
        //查出所有的宿舍
        List<Dormitory> all = dormitoryService.findAll();
        //查出该类型所有的维修工信息

        //查出所有修理类型
        model.addAttribute("empTypes", EmpType.values());
        model.addAttribute("dormitorys",all);
        return "admin/repairs/add";
    }


    /**
     * 维修上报表单处理
     * @param
     * @return
     */
    @RequestMapping(value="/add",method=RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> add(Repairs repairs) {

        Dormitory repairsDormitory = repairs.getDormitory();
        Dormitory dormitory = dormitoryService.find(repairsDormitory.getId());

        Long id = dormitory.getBuilding().getId();
        int code = repairs.getEmpType().getCode();

        List<Repairs> empType = repairsService.findByDormitoryIdAndEmpTypeAndStatus(dormitory.getId(), EmpType.getEmpByCode(code),0);
        if (!CollectionUtils.isEmpty(empType)) {
            return Result.error(CodeMsg.ADMIN_REPAIRS_ERROR);
        }
        /**
         * 有id是修改需要判断
         * 无id是增加无需判断
         */
        CodeMsg validate = ValidateEntityUtil.validate(repairs);
        if(validate.getCode() != CodeMsg.SUCCESS.getCode()){
            return Result.error(validate);
        }

        if (repairs.getStudent().getStudentNo().equals("") ||repairs.getStudent().getStudentNo()==null||repairs.getStudent().getStudentName().equals("") ||repairs.getStudent().getStudentName()==null){
            return Result.error(CodeMsg.DATA_ERROR);
        }
        Student studentNo = studentService.findByStudentNo(repairs.getStudent().getStudentNo());
        if (studentNo==null){
            return Result.error(CodeMsg.ADMIN_STUDENT_STUDENTNO);
        }
        if (repairs.getStudent().getStudentName()==null||!studentNo.getStudentName().equals(repairs.getStudent().getStudentName())){
            return Result.error(CodeMsg.ADMIN_STUDENT_EMPTY);
        }

        repairs.setStudent(studentNo);

        Long freeEmp = repairsService.findFreeEmpByBuildingAndEmpType(id, code);//查出匹配的空闲的师傅

        Long leastEmp = null;
        if (Objects.isNull(freeEmp)) {//若没查出空闲的师傅，则查匹配的任务量少的师傅
            leastEmp = repairsService.findEmpByBuildingAndEmpType(id, code);
        }

        if (repairs.getId()!=null){
            //用统一验证实体方法验证是否合法
          
            //到这说明一切符合条件，进行数据库保存
            Repairs byID = repairsService.findByID(repairs.getId());
            //讲提交的用户信息指定字段复制到已存在的user对象中,该方法会覆盖新字段内容
            System.out.println(byID+"---------------" +byID.getCreateTime());
            BeanUtils.copyProperties(repairs,byID, "id","createTime","updateTime");

            Emp emp = new Emp();

            if (Objects.nonNull(freeEmp)) { //如果查到有空闲的维修师傅
                emp.setId(freeEmp);
                byID.setEmp(emp);
                repairsService.save(byID);
                Emp emailEmp = empService.find(freeEmp);
               //mailService.send(emailEmp.getEmail(), "任务发配", Objects.nonNull(byID.getRemark()) ? byID.getRemark() : "有任务，需快速完成");
                return Result.success(true);
            }
            //没有空闲的维修师傅就找出任务量最少的维修师傅

            if (Objects.isNull(leastEmp)) {
                byID.setEmp(null);
            } else {
                emp.setId(leastEmp);
                byID.setEmp(emp);
            }

            repairsService.save(byID);
            if (Objects.nonNull(leastEmp)) {
                Emp emailEmp = empService.find(leastEmp);
                //mailService.send(emailEmp.getEmail(), "任务发配", Objects.nonNull(byID.getRemark()) ? byID.getRemark() : "有任务，需快速完成");
            }
        }else {
            Repairs repairsSave = repairsService.save(repairs);

            if (Objects.nonNull(repairsSave)) {

                //查询维修工的Id
                Emp emp =  new Emp();
                Emp emailEmp = new Emp();
                if(Objects.nonNull(freeEmp)){
                    emp.setId(freeEmp);
                    repairsSave.setEmp(emp);
                    repairsService.updateEmpId(repairsSave);
                    emailEmp = empService.find(emp.getId());
                   // mailService.send(emailEmp.getEmail(), "任务发配", Objects.nonNull(repairsSave.getRemark()) ? repairsSave.getRemark() : "有任务，需快速完成");
                    return Result.success(true);
                }

                if(leastEmp!=null){
                    emp.setId(leastEmp);
                    repairsSave.setEmp(emp);
                    repairsService.updateEmpId(repairsSave);
                    emailEmp = empService.find(emp.getId());
                    //mailService.send(emailEmp.getEmail(), "任务发配", Objects.nonNull(repairsSave.getRemark()) ? repairsSave.getRemark() : "有任务，需快速完成");
                }
            }
            System.out.println(repairsSave.getId()+"打印Id");
        }

        return Result.success(true);
    }


    /**
     * 订单修改
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(value="/edit",method=RequestMethod.GET)
    public String edit(Model model,@RequestParam(name="id")Long id){

       Repairs repairs = repairsService.findByID(id);
       Dormitory repairsDormitory = repairs.getDormitory();
        Dormitory dormitory = dormitoryService.find(repairsDormitory.getId());

        List<Emp> emps = empService.findByBuilding(dormitory.getBuilding());

       List<EmpType> empTypes = emps.stream().map(o -> o.getEmpType()).distinct().collect(Collectors.toList());

        //查出所有的宿舍
        List<Dormitory> all = dormitoryService.findAll();
        //查出所有修理类型
        model.addAttribute("empTypes", EmpType.values());
        model.addAttribute("dormitorys",all);
        model.addAttribute("repairs",repairsService.findByID(id));
        return "admin/repairs/edit";
    }

    /**
     * 编辑分配页面
     * @param
     * @return
     */
    @RequestMapping(value="/repairs",method=RequestMethod.GET)
    public String editAllot(Model model,@RequestParam(name="id")Long id){
        //查出所有的宿舍
        List<Dormitory> all = dormitoryService.findAll();
        List<Emp> emps = repairsService.findAllEmpId(id);
        List<EmpType> empTypes = emps.stream().map(o -> o.getEmpType()).collect(Collectors.toList());
        //查出所有修理类型
        model.addAttribute("emps", emps);
        model.addAttribute("empTypes", empTypes);
        model.addAttribute("dormitorys",all);
        model.addAttribute("repairs",repairsService.findByID(id));
        return "admin/repairs/repair";
    }

    /**
     * 编辑分配表单提交处理
     * @param
     * @return
     */
    @RequestMapping(value="/repairs",method=RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> editAllot(Repairs repairs) {
        /**
         * 有id是修改需要判断
         * 无id是增加无需判断
         */
        CodeMsg validate = ValidateEntityUtil.validate(repairs);
        if (validate.getCode() != CodeMsg.SUCCESS.getCode()) {
            return Result.error(validate);
        }
        if (repairs.getStudent().getStudentNo().equals("") || repairs.getStudent().getStudentNo() == null || repairs.getStudent().getStudentName().equals("") || repairs.getStudent().getStudentName() == null) {
            return Result.error(CodeMsg.DATA_ERROR);
        }
        Student studentNo = studentService.findByStudentNo(repairs.getStudent().getStudentNo());
        if (studentNo == null) {
            return Result.error(CodeMsg.ADMIN_STUDENT_STUDENTNO);
        }
        if (repairs.getStudent().getStudentName() == null || !studentNo.getStudentName().equals(repairs.getStudent().getStudentName())) {
            return Result.error(CodeMsg.ADMIN_STUDENT_EMPTY);
        }
        repairs.setStudent(studentNo);
        if (repairs.getId() != null) {
            //用统一验证实体方法验证是否合法

            //到这说明一切符合条件，进行数据库保存
            Repairs byID = repairsService.findByID(repairs.getId());
            //讲提交的用户信息指定字段复制到已存在的user对象中,该方法会覆盖新字段内容
            System.out.println(byID+"---------------" +byID.getCreateTime());
            BeanUtils.copyProperties(repairs, byID, "id", "createTime", "updateTime");
            repairsService.save(byID);
            Emp emailEmp = empService.find(repairs.getEmp().getId());
            mailService.send(emailEmp.getEmail(), "任务发配", Objects.nonNull(repairs.getRemark()) ? repairs.getRemark() : "任务接收人修改为您，请快完成");
        }
        return Result.success(true);
    }


}
