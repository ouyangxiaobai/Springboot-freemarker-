package com.bysj.base.constant;

import java.util.Arrays;
import java.util.List;

/**
 * 系统运行时的常量
 * @author Administrator
 *
 */
public class RuntimeConstant {

	//登录拦截器无需拦截的url
	public static List<String> loginExcludePathPatterns = Arrays.asList(
			"/system/login",
			"/system/auth_order",
			"/admin/css/**",
			"/admin/fonts/**",
			"/admin/js/**",
			"/admin/images/**",
			"/error",
			"/cpacha/generate_cpacha"
	);
	//权限拦截器无需拦截的url
	public static List<String> authorityExcludePathPatterns = Arrays.asList(
			"/system/login",
			"/system/auth_order",
			"/system/index",
			"/system/no_right",
			"/admin/css/**",
			"/admin/fonts/**",
			"/admin/js/**",
			"/admin/images/**",
			"/dormitory/save",
			"/error",
			"/admin/coach/**",
			"/cpacha/generate_cpacha",
			"/system/logout",
			"/system/update_userinfo",
			"/system/update_pwd",
			"/photo/view",
			"/work/**",
			"/statistics/count/**",
			"/stu/building"
	);
}
