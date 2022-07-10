package com.bysj.base.admin.interceptor;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.alibaba.fastjson.JSON;
import com.bysj.base.bean.CodeMsg;
import com.bysj.base.constant.SessionConstant;
import com.bysj.base.entity.admin.Menu;
import com.bysj.base.entity.admin.User;
import com.bysj.base.util.MenuUtil;
import com.bysj.base.util.StringUtil;

/**
 * 登录拦截器
 * @author Administrator
 *
 */
@Component
public class LoginInterceptor implements HandlerInterceptor{

	private Logger log = LoggerFactory.getLogger(LoginInterceptor.class);

	
	@Override
	public boolean  preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
		String requestURI = request.getRequestURI();
		HttpSession session = request.getSession();
		Object attribute = session.getAttribute(SessionConstant.SESSION_USER_LOGIN_KEY);
		if(attribute == null){
			log.info("用户还未登录或者session失效,重定向到登录页面,当前URL=" + requestURI);
			//首先判断是否是ajax请求
			if(StringUtil.isAjax(request)){
				//表示是ajax请求
				try {
					response.setCharacterEncoding("UTF-8");
					response.getWriter().write(JSON.toJSONString(CodeMsg.USER_SESSION_EXPIRED));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return false;
			}
			//说明是普通的请求，可直接重定向到登录页面
			//用户还未登录或者session失效,重定向到登录页面
			try {
				response.sendRedirect("/system/login");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		}
		log.info("该请求符合登录要求，放行" + requestURI);
		if(!StringUtil.isAjax(request)){
			//若不是ajax请求，则将菜单信息放入页面模板变量
			User user = (User)attribute;
			List<Menu> authorities = user.getRole().getAuthorities();
			request.setAttribute("userTopMenus", MenuUtil.getTopMenus(authorities));
			List<Menu> secondMenus = MenuUtil.getSecondMenus(user.getRole().getAuthorities());
			request.setAttribute("userSecondMenus", secondMenus);
			request.setAttribute("userThirdMenus", MenuUtil.getChildren(MenuUtil.getMenuIdByUrl(requestURI,secondMenus),authorities));

		}
		return true;
	}
}
