package com.bysj.base.service.admin;

import com.bysj.base.bean.PageBean;
import com.bysj.base.dao.admin.BuildingDao;
import com.bysj.base.entity.admin.Building;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BuildingService {

    @Autowired
    private BuildingDao buildingDao;

    /**
     * 楼栋添加/编辑操作
     * @param building
     * @return
     */
    public Building save(Building building) {
        return buildingDao.save(building);
    }

    /**
     * 根据用户id查询
     * @param id
     * @return
     */
    public Building find(Long id){
        return buildingDao.find(id);
    }



    /**
     * 分页查询用户列表
     * @param building
     * @param pageBean
     * @return
     */
    public PageBean<Building> findList(Building building, PageBean<Building> pageBean){
        ExampleMatcher withMatcher = ExampleMatcher.matching().withMatcher("campus", ExampleMatcher.GenericPropertyMatchers.contains());
        withMatcher = withMatcher.withIgnorePaths("bno");
        Example<Building> example = Example.of(building, withMatcher);
        Pageable pageable = PageRequest.of(pageBean.getCurrentPage()-1, pageBean.getPageSize());
        Page<Building> findAll = buildingDao.findAll(example, pageable);
        pageBean.setContent(findAll.getContent());
        pageBean.setTotal(findAll.getTotalElements());
        pageBean.setTotalPage(findAll.getTotalPages());
        return pageBean;
    }

    /**
     * 按照楼栋id删除
     * @param id
     */
    public void delete(Long id) {
        buildingDao.deleteById(id);
    }

    /**
     * 获取所有的角色列表
     * @return
     */
    public List<Building> findAll(){
        return buildingDao.findAll();
    }

    /**
     * 判断楼栋是否存在，添加和编辑均可判断
     * @param campus
     * @param bno
     * @param id
     * @return
     */
    public boolean isExistCampusAndBno(String campus,Integer bno,Long id){
        if (null == id) {
            return false;
        }

        Building addBuilding = buildingDao.findByCampusAndBno(campus, bno);

        if(addBuilding != null){
            //表示用户名存在，接下来判断是否是编辑用户的本身
            if(addBuilding.getId().longValue() != id.longValue()){
                return true;
            }
        }
        return false;
    }

}
