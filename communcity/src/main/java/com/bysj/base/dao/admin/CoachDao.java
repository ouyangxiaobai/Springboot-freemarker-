package com.bysj.base.dao.admin;

import com.bysj.base.entity.admin.Coach;


import com.bysj.base.entity.admin.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

@Repository
public interface CoachDao extends JpaRepository<Coach ,Long> {

    /**
     * 按照用户名查找用户信息
     * @param name
     * @return
     */
    public Coach findByname(String name);

    /**
     * 根据用户id查询
     * @param id
     * @return
     */
    @Query("select c from Coach c where id = :id")
    public Coach find(@Param("id")Long id);
}
