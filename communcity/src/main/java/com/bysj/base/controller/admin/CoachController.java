package com.bysj.base.controller.admin;

import com.bysj.base.bean.CodeMsg;
import com.bysj.base.bean.PageBean;
import com.bysj.base.bean.Result;
import com.bysj.base.dao.admin.CoachDao;
import com.bysj.base.entity.admin.Coach;

import com.bysj.base.service.admin.CoachService;
import com.bysj.base.service.admin.OperaterLogService;

import com.bysj.base.util.ValidateEntityUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/coach")
@Controller
public class CoachController {
    @Autowired
    private CoachService coachService;
    @Autowired
    private CoachDao coachDao;
    @Autowired
    private OperaterLogService operaterLogService;

    /*
    * 跳转查询页面
    * */
    @RequestMapping(value = "/list")
    public  String  findlist(Model model , Coach coach, PageBean<Coach> pageBean){
        model.addAttribute("title","辅导员列表");
        model.addAttribute("pageBean",coachService.findlist(coach,pageBean));
        return "admin/coach/list";
    }

    /**
     * 新增辅导员跳转页面
     * @param model
     * @return
     */
    @RequestMapping(value="/add",method=RequestMethod.GET)
    public String add(Model model){

        return "admin/coach/add";
    }

    /**
     * 辅导员添加表单提交处理
     * @param coach
     * @return
     */
    @RequestMapping(value="/add",method=RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> add(Coach coach){
        //用统一验证实体方法验证是否合法
        CodeMsg validate = ValidateEntityUtil.validate(coach);
        if(validate.getCode() != CodeMsg.SUCCESS.getCode()){
            return Result.error(validate);
        }

        //判断辅导员名是否存在
        if(coachService.isExistname(coach.getName(), 0l)){
            return Result.error(CodeMsg.COACH_ADD_EXIST_ERROR);
        }
        //到这说明一切符合条件，进行数据库新增
        if(coachService.save(coach) == null){
            return Result.error(CodeMsg.COACH_ADD_ERROR);
        }
        operaterLogService.add("添加辅导员，辅导员名：" + coach.getName());
        return Result.success(true);
    }
    /**
     * 辅导员编辑页面
     * @param model
     * @return
     */
    @RequestMapping(value="/edit",method=RequestMethod.GET)
    public String edit(Model model,@RequestParam(name="id",required=true)Long id){
        Coach coach = coachService.find(id);
        model.addAttribute("coach", coachService.find(id));
        return "admin/coach/edit";
    }
    /**
     * 编辑辅导员信息表单提交处理
     * @param coach
     * @return
     */
    @RequestMapping(value="/edit",method=RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> edit(Coach coach){
        //用统一验证实体方法验证是否合法
        CodeMsg validate = ValidateEntityUtil.validate(coach);
        if(validate.getCode() != CodeMsg.SUCCESS.getCode()){
            return Result.error(validate);
        }

        if(coach.getId() == null || coach.getId().longValue() <= 0){
            return Result.error(CodeMsg.COACH_EDITID_EXIST_ERROR);
        }
        if(coachService.isExistname(coach.getName(), coach.getId())){
            return Result.error(CodeMsg.COACH_EDITNAME_EXIST_ERROR);
        }
        //到这说明一切符合条件，进行数据库保存
        Coach findById= coachService.find(coach.getId());
        //讲提交的辅导员信息指定字段复制到已存在的user对象中,该方法会覆盖新字段内容
        BeanUtils.copyProperties(coach, findById, "id","createTime","updateTime");
        if(coachService.save(findById) == null){
            return Result.error(CodeMsg.COACH_EDIT_ERROR);
        }
        operaterLogService.add("编辑辅导员名，辅导员名：" + coach.getName());
        return Result.success(true);
    }

    /**
     * 删除辅导员
     * @param id
     * @return
     */
    @RequestMapping(value="/delete",method=RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> delete(@RequestParam(name="id",required=true)Long id){
        try {
            coachService.delete(id);
        } catch (Exception e) {
            return Result.error(CodeMsg.COACH_DELETE_ERROR);
        }
        operaterLogService.add("删除辅导员，辅导员ID：" + id);
        return Result.success(true);
    }
}
