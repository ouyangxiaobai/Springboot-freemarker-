package com.bysj.base.controller.admin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bysj.base.entity.admin.Statistics;
import com.bysj.base.service.admin.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/statistics")
public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;

    /**
     * 统计页面
     */
    @RequestMapping(value="/statistics")
    public String list(){
        return "admin/statistics/statistics";
    }

    /**
     * 按照楼栋统计
     * @return
     */
    @RequestMapping("/count/building")
    @ResponseBody
    public JSONObject countBuilding() {

        Statistics dataBean = statisticsService.countByBuilding();

        return (JSONObject) JSON.toJSON(dataBean);
    }


    /**
     * 按照维修种类统计
     * @return
     */
    @RequestMapping("/count/empType")
    @ResponseBody
    public JSONObject countEmpType() {

        Statistics dataBean = statisticsService.countByEmpType();

        return (JSONObject) JSON.toJSON(dataBean);
    }


    /**
     * 按照维修工统计
     * @return
     */
    @RequestMapping("/count/emp")
    @ResponseBody
    public JSONObject countEmp() {

        Statistics dataBean = statisticsService.countByEmp();

        return (JSONObject) JSON.toJSON(dataBean);
    }


}
