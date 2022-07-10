package com.bysj.base.service.admin;

import com.bysj.base.bean.PageBean;
import com.bysj.base.dao.admin.DormitoryDao;
import com.bysj.base.entity.admin.Dormitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DormitoryService {

    @Autowired
   private DormitoryDao dormitoryDao;


    /***
     * 宿舍分页查询
     * @param dormitory
     * @param pageBean
     * @return
     */
    public PageBean<Dormitory> findList(Dormitory dormitory, PageBean<Dormitory> pageBean){
        Example<Dormitory> example = Example.of(dormitory);
        Pageable pageable = PageRequest.of(pageBean.getCurrentPage()-1, pageBean.getPageSize());
        Page<Dormitory> findAll = dormitoryDao.findAll(example,pageable);
        pageBean.setContent(findAll.getContent());
        pageBean.setTotal(findAll.getTotalElements());
        pageBean.setTotalPage(findAll.getTotalPages());
        return pageBean;
    }

    /***
     * 宿舍全查询
     * @return
     */
    public List<Dormitory> findAll(){
        return dormitoryDao.findAll();
    }

    public List<Dormitory> findByBuilding_Id(Long id){
        return dormitoryDao.findByBuilding_Id(id);
    }

    /***
     * 判断用户名是否存在，添加和编辑均可判断
     * @param buildingId
     * @param roomNo
     * @return
     */
    public boolean isExistRoomNo(Long buildingId,Integer  roomNo,Long id){
        Dormitory dormitory = dormitoryDao.findByBuildingIdAndRoomNo(buildingId, roomNo);
        if(dormitory != null){ //101 roomNo=101
            //表示用户名存在，接下来判断是否是编辑用户的本身
             if(dormitory.getId().longValue() != id.longValue()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 添加宿舍
     * @param dormitory
     * @return
     */
    public Dormitory saveDormitory(Dormitory dormitory){
        return dormitoryDao.save(dormitory);
    }


    /***
     * 删除宿舍
     * @param id
     */
    public void deleteDormitory(Long id) {
         dormitoryDao.deleteById(id);
    }

    /***
     * 根据ID查询
     * @param id
     * @return
     */
    public Dormitory find(Long id) {
        Dormitory one = dormitoryDao.find(id);
        return one;
    }

    public Boolean isExistRoomNos(Integer roomNo2){
        Dormitory dormitory = dormitoryDao.findByRoomNo(roomNo2);
        if(dormitory!=null){
            if(dormitory.getRoomNo().longValue()!=roomNo2.longValue()){
                return true;
            }
        }
        return false;
    }

}
