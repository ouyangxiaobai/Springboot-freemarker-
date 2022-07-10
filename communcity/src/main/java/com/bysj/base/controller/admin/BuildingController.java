package com.bysj.base.controller.admin;

import com.bysj.base.bean.CodeMsg;
import com.bysj.base.bean.PageBean;
import com.bysj.base.bean.Result;
import com.bysj.base.entity.admin.Building;
import com.bysj.base.entity.admin.User;
import com.bysj.base.service.admin.BuildingService;
import com.bysj.base.service.admin.OperaterLogService;
import com.bysj.base.util.ValidateEntityUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/building")
public class BuildingController {
    @Autowired
    private BuildingService buildingService;

    @Autowired
    private OperaterLogService operaterLogService;

    /**
     * 楼栋列表页面
     * @param model
     * @param building
     * @param pageBean
     * @return
     */
    @RequestMapping(value="/list")
    public String list(Model model, Building building, PageBean<Building> pageBean){
        model.addAttribute("title", "楼栋列表");
        model.addAttribute("campus", building.getCampus());
        model.addAttribute("pageBean", buildingService.findList(building, pageBean));
        return "admin/building/list";
    }

    /**
     * 新增楼栋页面
     * @param model
     * @return
     */
    @RequestMapping(value="/add",method=RequestMethod.GET)
    public String add(Model model){
        model.addAttribute("building", buildingService.findAll());
        return "admin/building/add";
    }

    /**
     * 楼栋编辑页面
     * @param model
     * @return
     */
    @RequestMapping(value="/edit",method=RequestMethod.GET)
    public String edit(Model model,@RequestParam(name="id",required=true)Long id){
        model.addAttribute("building", buildingService.find(id));
        return "admin/building/edit";
    }

    /**
     * 楼栋添加表单提交处理
     * @param building
     * @return
     */
    @RequestMapping(value="/add",method=RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> add(Building building){
        //用统一验证实体方法验证是否合法
        CodeMsg validate = ValidateEntityUtil.validate(building);
        if(validate.getCode() != CodeMsg.SUCCESS.getCode()){
            return Result.error(validate);
        }

        if(buildingService.isExistCampusAndBno(building.getCampus(),building.getBno(), 0L)){
            return Result.error(CodeMsg.ADMIN_BUILDING_EXIST);
        }

        //到这说明一切符合条件，进行数据库新增
        if(buildingService.save(building) == null){
            return Result.error(CodeMsg.ADMIN_BUILDING_ADD_ERROR);
        }
        operaterLogService.add("添加楼栋，楼栋校区和楼号：" + building.getCampus()+building.getBno());
        return Result.success(true);
    }

    /**
     * 编辑楼栋信息表单提交处理
     * @param building
     * @return
     */
    @RequestMapping(value="/edit",method=RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> edit(Building building){
        //用统一验证实体方法验证是否合法
        CodeMsg validate = ValidateEntityUtil.validate(building);
        if(validate.getCode() != CodeMsg.SUCCESS.getCode()){
            return Result.error(validate);
        }
        if(building.getId() == null || building.getId().longValue() <= 0){
            return Result.error(CodeMsg.ADMIN_BUILDING_EXIST);
        }

        if(buildingService.isExistCampusAndBno(building.getCampus(),building.getBno(), building.getId())){
            return Result.error(CodeMsg.ADMIN_BUILDING_EXIST);
        }

        //到这说明一切符合条件，进行数据库保存
        Building findById = buildingService.find(building.getId());
        //讲提交的用户信息指定字段复制到已存在的user对象中,该方法会覆盖新字段内容
        BeanUtils.copyProperties(building, findById, "id","createTime","updateTime");
        if(buildingService.save(findById) == null){
            return Result.error(CodeMsg.ADMIN_USE_EDIT_ERROR);
        }
        operaterLogService.add("编辑楼栋，楼栋校区和楼号：" + building.getCampus()+building.getBno());
        return Result.success(true);
    }

    /**
     * 删除楼栋
     * @param id
     * @return
     */
    @RequestMapping(value="/delete",method=RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> delete(@RequestParam(name="id",required=true)Long id){
        try {
            buildingService.delete(id);
        } catch (Exception e) {
            return Result.error(CodeMsg.ADMIN_BUILDING_DELETE_ERROR);
        }
        operaterLogService.add("添加用户，用户ID：" + id);
        return Result.success(true);
    }
}
