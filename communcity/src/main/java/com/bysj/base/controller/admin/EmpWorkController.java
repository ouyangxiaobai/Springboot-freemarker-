package com.bysj.base.controller.admin;

import com.bysj.base.bean.CodeMsg;
import com.bysj.base.bean.Result;
import com.bysj.base.entity.admin.*;
import com.bysj.base.service.admin.EmpService;
import com.bysj.base.service.admin.MailService;
import com.bysj.base.service.admin.RepairsService;
import com.bysj.base.service.admin.StudentService;
import com.bysj.base.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/***
 * 维修内容控制器
 */
@Controller
@RequestMapping("/work")
public class EmpWorkController {
    @Autowired
    private EmpService empService;
    @Autowired
    private RepairsService repairsService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private MailService mailService;
    /***
     * 维修内容列表
     * @param model
     * @return
     */
     @RequestMapping(value = "/list")
    public String list(Model model){
         //获取用户数据
         User loginUser = SessionUtil.getLoginedUser();
         //拿到对应的NAME值
         String roleName = loginUser.getRole().getRoleType().getName();
         System.out.println(loginUser);
         //判断哪个角色
         if(RoleType.ELECTRICIAN.getName().equals(roleName)) {
             Emp emp = empService.find(loginUser.getId());
             Long empId = emp.getId();
             model.addAttribute("repairs", repairsService.findByEmpId(empId));
         }
         if(RoleType.SUPER_ADMINISTRATOR.getName().equals(roleName)){
             model.addAttribute("repairs",repairsService.findAll());
         }
         return "admin/empwork/list";

     }
    /**
     * 维修完成
     * @param id
     * @return
     */
    @RequestMapping(value = "/maintenance",method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> editMte(@RequestParam(name = "id")Long id){
        System.out.println("-----------------"+id);
        //根据ID查出Repairs对象
        Repairs repairs = repairsService.findByID(id);
        //判断是否已完成
        if(repairs.getStatus()==1){
            return Result.error(CodeMsg.ADMIN_MAINTENANCE_EXIST);
        }
        if(repairs.getStatus()==0){
            //定义已完成
            repairs.setStatus(1);
            //收件人的邮箱  主题完成  内容完成任务
            //获取收件人
            Student byStudentNo = studentService.findByStudentNo(repairs.getStudent().getStudentNo());
            String email = byStudentNo.getEmail();
            //证明状态是对的，进行修改数据库
            repairsService.save(repairs);
            //定义主题内容
            final  String subject="任务完成!";
            final  String context="任务完成!请查收";
            mailService.send(email,subject,context);
        }
        return Result.success(true);
    }

}
