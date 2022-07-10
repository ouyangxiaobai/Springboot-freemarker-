package com.bysj.base.controller.admin;

import com.bysj.base.bean.CodeMsg;
import com.bysj.base.bean.PageBean;
import com.bysj.base.bean.Result;
import com.bysj.base.entity.admin.Building;
import com.bysj.base.entity.admin.Dormitory;
import com.bysj.base.service.admin.BuildingService;
import com.bysj.base.service.admin.DormitoryService;
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

import java.util.Date;

/****
 * 宿舍信息管理控制器
 *
 */
@Controller
@RequestMapping("/dormitory")
public class DormitoryController {

    @Autowired
    private DormitoryService dormitoryService;

    @Autowired
    private BuildingService buildingService;

    @Autowired
    private OperaterLogService operaterLogService;

    /***
     * 宿舍列表
     * @param model
     * @return
     */
    @RequestMapping(value = "/list")
    public String findAllDormitory(Model model,Dormitory dormitory, PageBean<Dormitory> pageBean){
        model.addAttribute("title", "宿舍列表");
        model.addAttribute("pageBean", dormitoryService.findList(dormitory, pageBean));
        model.addAttribute("roomNo",dormitory.getRoomNo());
       return "admin/dormitory/list";
    }

    /**
     * 跳转添加页面
     * @return
     */
    @RequestMapping(value = "/add",method = RequestMethod.GET)
    public String add(Model Model){
        Model.addAttribute("buils", buildingService.findAll());
         return "admin/dormitory/add";
    }

    /***
     * 添加宿舍
     * @param dormitory
     * @return
     */
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> add(Dormitory dormitory){
        //用统一验证实体方法验证是否合法
        CodeMsg validate = ValidateEntityUtil.validate(dormitory);
        if(validate.getCode() != CodeMsg.SUCCESS.getCode()){
            return Result.error(validate);
        }
        if(dormitory.getBuilding() == null || dormitory.getBuilding().getId() == null){
            return Result.error(CodeMsg.ADMIN_DORMITORY_EMPTY);
        }

        //判断所属楼栋宿舍号是否存在
        if(dormitoryService.isExistRoomNo(dormitory.getBuilding().getId(), dormitory.getRoomNo(),0l)){
            return Result.error(CodeMsg.ADMIN_DORMITORY_EXIST);
        }
        //到这说明一切符合条件，进行数据库新增
        if(dormitoryService.saveDormitory(dormitory) == null){
            return Result.error(CodeMsg.ADMIN_DORMITORY_ADD_ERROR);
        }
        operaterLogService.add("添加宿舍，宿舍号：" + dormitory.getRoomNo());
        return Result.success(true);
    }


    /***
     * 删除指定ID宿舍
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> deleteDormitory(@RequestParam(name="id",required=true)Long id){
        try {
            dormitoryService.deleteDormitory(id);
        } catch (Exception e) {
            return Result.error(CodeMsg.ADMIN_DORMITORY_DELETE_ERROR);
        }
        operaterLogService.add("添加用户，用户ID：" + id);
           return Result.success(true);
    }


    /**
     * 根据ID查询一条数据
     * @param Model
     * @param id
     * @return
     */
    @RequestMapping("/edit")
    public String findOneById(Model Model,@RequestParam(name="id",required=true)Long id){
       Model.addAttribute("dormitory", dormitoryService.find(id));
        Model.addAttribute("buils", buildingService.findAll());
       return "admin/dormitory/edit";
    }

    /***
     * 编辑宿舍
     * @param dormitory
     * @return
     */
    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> updateDormitory(Dormitory dormitory) {
        //用统一验证实体方法验证是否合法
        CodeMsg validate = ValidateEntityUtil.validate(dormitory);
        if (validate.getCode() != CodeMsg.SUCCESS.getCode()) {
            return Result.error(validate);
        }
        if (dormitory.getBuilding() == null || dormitory.getBuilding().getId() == null) {
            return Result.error(CodeMsg.ADMIN_DORMITORY_EMPTY);
        }
       if(dormitoryService.isExistRoomNos(dormitory.getRoomNo())){
            return Result.error(CodeMsg.ADMIN_DORMITORY_NOTNULL);
       }
        Long buildingId = dormitory.getBuilding().getId();
        Integer roomNo = dormitory.getRoomNo();
        //判断所属楼栋宿舍号是否存在
        if(dormitoryService.isExistRoomNo(buildingId,roomNo,dormitory.getId())){
            return  Result.error(CodeMsg.ADMIN_DORMITORY_EXIST);
        }
        dormitory.setCreateTime(new Date());
        //到这说明一切符合条件，进行数据库保存
        Dormitory findById = dormitoryService.find(dormitory.getId());
        //讲提交的用户信息指定字段复制到已存在的dormitory对象中,该方法会覆盖新字段内容
        BeanUtils.copyProperties(dormitory,findById , "id", "createTime", "updateTime","password");
        Dormitory dormitory2 = dormitoryService.saveDormitory(findById);

        if (dormitory2==null) {
            return Result.error(CodeMsg.ADMIN_USE_EDIT_ERROR);
        }
        operaterLogService.add("编辑宿舍，宿舍号：" + dormitory.getRoomNo());
        return Result.success(true);

    }
}
