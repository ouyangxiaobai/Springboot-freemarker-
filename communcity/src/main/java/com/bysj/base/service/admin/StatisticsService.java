package com.bysj.base.service.admin;

import com.bysj.base.dao.admin.BuildingDao;
import com.bysj.base.dao.admin.EmpDao;
import com.bysj.base.entity.admin.Building;
import com.bysj.base.entity.admin.Emp;
import com.bysj.base.entity.admin.EmpType;
import com.bysj.base.entity.admin.Statistics;
import com.bysj.base.dao.admin.StatisticsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class StatisticsService {

    @Autowired
    private StatisticsDao statisticsDao;

    @Autowired
    private EmpDao empDao;

    @Autowired
    private BuildingDao buildingDao;

    /**
     * 楼栋统计任务
     * @return
     */
    public Statistics countByBuilding() {

        Statistics bean = new Statistics();

        List<Object> allCampus = statisticsDao.countBuilding();

        List<String> categories = new ArrayList<>();

        List<Integer> data = new ArrayList<>();

        //遍历添加横纵坐标数据
        for (Object o : allCampus) {
            Object[] group = (Object[]) o;

            //通过楼栋id取查询楼栋拿到楼栋地址给x坐标
            Long id = Long.valueOf(group[0].toString());
            Building building = buildingDao.find(id);

            categories.add(building.getCampus()+building.getBno());

            //纵坐标数据
            data.add(Integer.valueOf(group[1].toString()));
        }

        bean.setCategories(categories);
        bean.setData(data);

        return bean;
    }


    /**
     * 维修工种类统计任务
     * @return
     */
    public Statistics countByEmpType() {
        Statistics bean = new Statistics();
        List<Object> allEmpTypes = statisticsDao.countByEmpType();

        List<String> categories = new ArrayList<>();

        List<Integer> data = new ArrayList<>();

        //遍历添加横纵坐标数据
        for (Object o : allEmpTypes) {
            Object[] group = (Object[]) o;

            //x轴
            categories.add(EmpType.getByCode(Integer.valueOf(group[0].toString())));
            //y轴
            data.add(Integer.valueOf(group[1].toString()));
        }

        bean.setCategories(categories);
        bean.setData(data);

        return bean;
    }

    /**
     * 维修工统计任务
     * @return
     */
    public Statistics countByEmp() {
        Statistics bean = new Statistics();
        List<Object> allEmp = statisticsDao.countByEmp();

        List<String> categories = new ArrayList<>();

        List<Integer> data = new ArrayList<>();

        //遍历添加横纵坐标数据
        for (Object o : allEmp) {
            Object[] group = (Object[]) o;
            if (null == group[0]) {
                continue;
            }

            //通过维修工id拿到维修工名称
            Long id = Long.valueOf(group[0].toString());
            Emp emp = empDao.find(id);

            //x轴
            categories.add(emp.getName());

            //y轴
            data.add(Integer.valueOf(group[1].toString()));
        }

        bean.setCategories(categories);
        bean.setData(data);

        return bean;
    }
}
