package com.bysj.base.service.admin;
/**
 * 后台角色操作service
 */
import java.util.List;

import com.bysj.base.entity.admin.RoleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bysj.base.bean.PageBean;
import com.bysj.base.dao.admin.RoleDao;
import com.bysj.base.entity.admin.Role;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Transient;

@Service
public class RoleService {
	
	@Autowired
	private RoleDao roleDao;
	
	/**
	 * 角色添加/编辑
	 * @param role
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public Role save(Role role){
		return roleDao.save(role);
	}
	
	/**
	 * 获取所有的角色列表
	 * @return
	 */
	public List<Role> findAll(){
		return roleDao.findAll();
	}
	
	/**
	 * 分页按角色名称搜索角色列表
	 * @param role
	 * @param pageBean
	 * @return
	 */
	public PageBean<Role> findByName(Role role,PageBean<Role> pageBean){
		ExampleMatcher withMatcher = ExampleMatcher.matching().withMatcher("name", GenericPropertyMatchers.contains());
		withMatcher = withMatcher.withIgnorePaths("status");
		Example<Role> example = Example.of(role, withMatcher);
		Pageable pageable = PageRequest.of(pageBean.getCurrentPage()-1, pageBean.getPageSize());
		Page<Role> findAll = roleDao.findAll(example, pageable);
		pageBean.setContent(findAll.getContent());
		pageBean.setTotal(findAll.getTotalElements());
		pageBean.setTotalPage(findAll.getTotalPages());
		return pageBean;
	}
	
	/**
	 * 根据id查询角色
	 * @param id
	 * @return
	 */
	public Role find(Long id){
		return roleDao.find(id);
	}

	/**
	 * 根据nanme查询角色
	 * @param
	 * @return
	 */
	public Role find(String name){
		return roleDao.findByName(name);
	}

	public 	Role findByRoleType(RoleType roleType){
		return roleDao.findByRoleType(roleType).get(0);
	}
	/**
	 * 根据id删除一条记录
	 * @param id
	 */
	public void delete(Long id){
		roleDao.deleteById(id);
	}

}
