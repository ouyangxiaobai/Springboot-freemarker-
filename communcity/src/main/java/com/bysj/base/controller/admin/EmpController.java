package com.bysj.base.controller.admin;

import com.bysj.base.bean.CodeMsg;
import com.bysj.base.bean.PageBean;
import com.bysj.base.bean.Result;
import com.bysj.base.dao.admin.RoleDao;
import com.bysj.base.entity.admin.*;
import com.bysj.base.service.admin.*;
import com.bysj.base.util.StringUtil;
import com.bysj.base.util.ValidateEntityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/emp")
@Controller
public class EmpController {

    private Logger log = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private BuildingService buildingService;
    @Autowired
    private EmpService empService;
    @Autowired
    private OperaterLogService operaterLogService;
 @Autowired
    private RoleService roleService;

    /**
     * 员工列表展示
     * @param model
     * @param emp
     * @param pageBean
     * @return
     */

    @RequestMapping(value="/list")
    public String list(Model model, Emp emp, PageBean<Emp> pageBean){
        model.addAttribute("title", "维修工列表");
        model.addAttribute("name", emp.getName());
        PageBean<Emp> byName = empService.findByName(emp, pageBean);
        model.addAttribute("pageBean",byName );
        return "admin/emp/list";
    }

    /**
     * 角色添加页面
     * @param model
     * @return
     */
    @RequestMapping(value="/add",method=RequestMethod.GET)
    public String add(Model model){
        model.addAttribute("buidings", buildingService.findAll());
        model.addAttribute("empTypes", EmpType.values());
        return "admin/emp/add";
    }

    /**
     * 员工添加提交表单处理
     * @param emp
     * @return
     */
    @RequestMapping(value="/add",method=RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> add(Emp emp){

        String e = StringUtil.generateSn("E", "");
        emp.setNumber(e);
        emp.setPassword(emp.getNumber());

        if(emp == null){
            Result.error(CodeMsg.DATA_ERROR);
        }
        //用统一验证实体方法验证是否合法
        CodeMsg validate = ValidateEntityUtil.validate(emp);
        if(validate.getCode() != CodeMsg.SUCCESS.getCode()){
            return Result.error(validate);
        }
        if(emp.getBuilding() == null || emp.getBuilding().getId() == null){
            return Result.error(CodeMsg.ADMIN_EMP_BUILDING_EMPTY);
        }
        //判断用户名是否存在
        if(empService.isExistUsername(emp.getNumber(), 0l)){
            return Result.error(CodeMsg.ADMIN_EMP_NAME_EXIST);
        }

        emp.setRole(roleService.findByRoleType(RoleType.ELECTRICIAN));
        //表示验证都通过，开始添加数据库
        if(empService.save(emp) == null){
            Result.error(CodeMsg.ADMIN_EMP_ADD_ERROR);
        }


        //数据库添加操作成功,记录日志
        operaterLogService.add("添加员工信息【" + emp.getName() + "】");
        return Result.success(true);
    }

    /**
     * 员工编辑页面
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value="/edit",method=RequestMethod.GET)
    public String edit(@RequestParam(name="id",required=true)Long id, Model model){
        Emp emp = empService.find(id);
        model.addAttribute("emp", emp);
        model.addAttribute("buidings", buildingService.findAll());
        model.addAttribute("empTypes", EmpType.values());
        return "admin/emp/edit";
    }

    /**
     * 员工修改表单提交处理
     * @param
     * @return
     */
    @RequestMapping(value="/edit",method=RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> edit(Emp emp){
        //用统一验证实体方法验证是否合法
        CodeMsg validate = ValidateEntityUtil.validate(emp);
        if(validate.getCode() != CodeMsg.SUCCESS.getCode()){
            return Result.error(validate);
        }
        Emp existEmp = empService.find(emp.getId());
        if(existEmp == null){
            return Result.error(CodeMsg.ADMIN_ROLE_NO_EXIST);
        }
        existEmp.setName(emp.getName());
        existEmp.setNumber(emp.getNumber());
        existEmp.setSex(emp.getSex());
        existEmp.setEmpType(emp.getEmpType());
        existEmp.setEmail(emp.getEmail());
        existEmp.setMobile(emp.getMobile());
        existEmp.setBuilding(emp.getBuilding());
        existEmp.setHeadPic(emp.getHeadPic());
        existEmp.setPassword(emp.getPassword());
        if(empService.save(existEmp) == null){
            return Result.error(CodeMsg.ADMIN_ROLE_EDIT_ERROR);
        }
        log.info("编辑角色【"+emp+"】");
        operaterLogService.add("编辑角色【"+emp.getName()+"】");
        return Result.success(true);
    }

    /**
     * 删除员工信息
     * @param
     * @param id
     * @return
     */
    @RequestMapping(value="/delete",method=RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> delete(@RequestParam(name="id",required=true)Long id){
        try {
            empService.delete(id);
        } catch (Exception e) {
            return Result.error(CodeMsg.ADMIN_MENU_DELETE_ERROR);
        }
        //数据库添加操作成功,记录日志
        operaterLogService.add("删除菜单信息，菜单ID【" + id + "】");
        return Result.success(true);
    }



}
