package com.bysj.base.bean;
/**
 * 错误码统一处理类，所有的错误码统一定义在这里
 * @author Administrator
 *
 */
public class CodeMsg {

	private int code;//错误码
	
	private String msg;//错误信息
	
	/**
	 * 构造函数私有化即单例模式
	 * @param code
	 * @param msg
	 */
	private CodeMsg(int code,String msg){
		this.code = code;
		this.msg = msg;
	}
	
	public int getCode() {
		return code;
	}



	public void setCode(int code) {
		this.code = code;
	}



	public String getMsg() {
		return msg;
	}



	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	//通用错误码定义
	//处理成功消息码
	public static CodeMsg SUCCESS = new CodeMsg(0, "success");
	//非法数据错误码
	public static CodeMsg DATA_ERROR = new CodeMsg(-1, "非法数据！");
	public static CodeMsg CPACHA_EMPTY = new CodeMsg(-2, "验证码不能为空！");
	public static CodeMsg VALIDATE_ENTITY_ERROR = new CodeMsg(-3, "");
	public static CodeMsg SESSION_EXPIRED = new CodeMsg(-4, "会话已失效，请刷新页面重试！");
	public static CodeMsg CPACHA_ERROR = new CodeMsg(-5, "验证码错误！");
	public static CodeMsg USER_SESSION_EXPIRED = new CodeMsg(-6, "还未登录或会话失效，请重新登录！");
	public static CodeMsg UPLOAD_PHOTO_SUFFIX_ERROR = new CodeMsg(-7, "图片格式不正确！");
	public static CodeMsg UPLOAD_PHOTO_ERROR = new CodeMsg(-8, "图片上传错误！");
	public static CodeMsg SAVE_ERROR = new CodeMsg(-11, "保存失败，请联系管理员！");
	public static CodeMsg ORDER_SN_ERROR = new CodeMsg(-12, "订单编号错误！");
	public static CodeMsg PHONE_ERROR = new CodeMsg(-13, "手机号错误！");
	public static CodeMsg ORDER_AUTH_ERROR = new CodeMsg(-14, "\u8ba2\u5355\u9a8c\u8bc1\u5931\u8d25\uff0c\u8ba2\u5355\u7f16\u53f7\u6216\u624b\u673a\u53f7\u8f93\u5165\u6709\u8bef\u6216\u8005\u53ef\u80fd\u4f60\u8d2d\u4e70\u7684\u662f\u76d7\u7248\uff0c\u8bf7\u8054\u7cfb\u3010\u733f\u6765\u5165\u6b64\u3011\u5ba2\u670d\uff01");
	
	//后台管理类错误码
	//用户管理类错误
	public static CodeMsg ADMIN_USERNAME_EMPTY = new CodeMsg(-2000, "用户名不能为空！");
	public static CodeMsg ADMIN_PASSWORD_EMPTY = new CodeMsg(-2001, "密码不能为空！");
	public static CodeMsg ADMIN_NO_RIGHT = new CodeMsg(-2002, "您所属的角色没有该权限！");
	
	//登录类错误码
	public static CodeMsg ADMIN_USERNAME_NO_EXIST = new CodeMsg(-3000, "该用户名不存在！");
	public static CodeMsg ADMIN_PASSWORD_ERROR = new CodeMsg(-3001, "密码错误！");
	public static CodeMsg ADMIN_USER_UNABLE = new CodeMsg(-3002, "该用户已被冻结，请联系管理员！");
	public static CodeMsg ADMIN_USER_ROLE_UNABLE = new CodeMsg(-3003, "该用户所属角色状态不可用，请联系管理员！");
	public static CodeMsg ADMIN_USER_ROLE_AUTHORITES_EMPTY = new CodeMsg(-3004, "该用户所属角色无可用权限，请联系管理员！");
	
	//后台菜单管理类错误码
	public static CodeMsg ADMIN_MENU_ADD_ERROR = new CodeMsg(-4000, "菜单添加失败，请联系管理员！");
	public static CodeMsg ADMIN_MENU_EDIT_ERROR = new CodeMsg(-4001, "菜单编辑失败，请联系管理员！");
	public static CodeMsg ADMIN_MENU_ID_EMPTY = new CodeMsg(-4002, "菜单ID不能为空！");
	public static CodeMsg ADMIN_MENU_ID_ERROR = new CodeMsg(-4003, "菜单ID错误！");
	public static CodeMsg ADMIN_MENU_DELETE_ERROR = new CodeMsg(-4004, "改菜单下有子菜单，不允许删除！");
	//后台角色管理类错误码
	public static CodeMsg ADMIN_ROLE_ADD_ERROR = new CodeMsg(-5000, "角色添加失败，请联系管理员！");
	public static CodeMsg ADMIN_ROLE_NO_EXIST = new CodeMsg(-5001, "该角色不存在！");
	public static CodeMsg ADMIN_ROLE_EDIT_ERROR = new CodeMsg(-5002, "角色编辑失败，请联系管理员！");
	public static CodeMsg ADMIN_ROLE_DELETE_ERROR = new CodeMsg(-5003, "该角色下存在用户信息，不可删除！");
	//后台用户管理类错误码
	public static CodeMsg ADMIN_USER_ROLE_EMPTY = new CodeMsg(-6000, "请选择用户所属角色！");
	public static CodeMsg ADMIN_USERNAME_EXIST = new CodeMsg(-6001, "该用户名已存在，请换一个试试！");
	public static CodeMsg ADMIN_USE_ADD_ERROR = new CodeMsg(-6002, "用户添加失败，请联系管理员！");
	public static CodeMsg ADMIN_USE_NO_EXIST = new CodeMsg(-6003, "用户不存在！");
	public static CodeMsg ADMIN_USE_EDIT_ERROR = new CodeMsg(-6004, "用户编辑失败，请联系管理员！");
	public static CodeMsg ADMIN_USE_DELETE_ERROR = new CodeMsg(-6005, "该用户存在关联数据，不允许删除！");


	//后台用户修改密码类错误码
	public static CodeMsg ADMIN_USER_UPDATE_PWD_ERROR = new CodeMsg(-7000, "旧密码错误！");
	public static CodeMsg ADMIN_USER_UPDATE_PWD_EMPTY = new CodeMsg(-7001, "新密码不能为空！");
	public static CodeMsg ADMIN_UPDATE_PWD_ERROR = new CodeMsg(-7002, "新密码不能为空长度大于4位");

	
	//后台用户修改密码类错误码
	public static CodeMsg ADMIN_DATABASE_BACKUP_NO_EXIST = new CodeMsg(-8000, "备份记录不存在！");

	//楼栋管理类错误码
	public static CodeMsg ADMIN_BUILDING_EXIST = new CodeMsg(-9001, "该楼栋已存在，请换一个试试！");
	public static CodeMsg ADMIN_BUILDING_ADD_ERROR = new CodeMsg(-9002, "楼栋添加失败，请联系管理员！");
	public static CodeMsg ADMIN_BUILDING_EDIT_ERROR = new CodeMsg(-9004, "楼栋编辑失败，请联系管理员！");
	public static CodeMsg ADMIN_BUILDING_DELETE_ERROR = new CodeMsg(-9005, "该楼栋存在关联数据，不允许删除！");


	//维修工维护错误码
	public static CodeMsg ADMIN_EMP_EXIST = new CodeMsg(-6661, "该维修工已存在，请换一个试试！");
	public static CodeMsg ADMIN_EMP_ADD_ERROR = new CodeMsg(-6662, "维修工添加失败，请联系管理员！");
	public static CodeMsg ADMIN_EMP_EDIT_ERROR = new CodeMsg(-6663, "维修工编辑失败，请联系管理员！");
	public static CodeMsg ADMIN_EMP_DELETE_ERROR = new CodeMsg(-6664, "该维修工存在关联数据，不允许删除！");
	public static CodeMsg ADMIN_EMP_NAME_EXIST = new CodeMsg(-6665, "该用户名已存在，请换一个试试！");
	public static CodeMsg ADMIN_EMP_BUILDING_EMPTY = new CodeMsg(-6666, "请选择所属楼号！");


	//宿舍管理类错误码
	public static CodeMsg ADMIN_DORMITORY_EXIST = new CodeMsg(-10001, "该宿舍已存在，请换一个试试！");
	public static CodeMsg ADMIN_DORMITORY_ADD_ERROR = new CodeMsg(-10002, "宿舍添加失败，请联系管理员！");
	public static CodeMsg ADMIN_DORMITORY_EDIT_ERROR = new CodeMsg(-10004, "宿舍编辑失败，请联系管理员！");
	public static CodeMsg ADMIN_DORMITORY_DELETE_ERROR = new CodeMsg(-10005, "该宿舍存在关联数据，不允许删除！");
	public static CodeMsg ADMIN_DORMITORY_EMPTY = new CodeMsg(-10006, "请选择用户所属楼栋！");
	public static CodeMsg ADMIN_DORMITORY_NOTNULL = new CodeMsg(-10007, "该宿舍不存在！");


  //学生管理
    public static CodeMsg ADMIN_STUDENT_EMPTY = new CodeMsg(-11001, "学生姓名错误！");
	public static CodeMsg ADMIN_STUDENT_STUDENTNO = new CodeMsg(-11002, "没有该学号！");


	//维修状态错误码
	public static CodeMsg ADMIN_MAINTENANCE_EXIST = new CodeMsg(-12001, "该任务已完成！");
	public static CodeMsg ADMIN_MAINTENANCE_EDIT_ERROR = new CodeMsg(-12002, "维修任务完成失败，请联系管理员！");
	public static CodeMsg ADMIN_STUDENT_EMAIL= new CodeMsg(-12003, "邮箱错误！");
	public static CodeMsg ADMIN_STUDENT_MOBILE= new CodeMsg(-12004, "电话格式错误！");


	//维修工分配错误码
	public static CodeMsg ADMIN_REPAIRS_ERROR= new CodeMsg(-13001, "该订单已提交，还未完成，请勿重复提交！");
	public static CodeMsg ADMIN_REPAIRS_EMPTYPE_EMPTY= new CodeMsg(-13001, "请等待管理员分配！");

	//邮件错误码
	public static CodeMsg MAIL_ERROR = new CodeMsg(-14001, "邮箱发送失败，邮箱格式错误！");
	public static CodeMsg MAIL_SUCCESS = new CodeMsg(-14002, "邮箱发送失败，邮箱格式错误！");

	//辅导员错误吗
	public static CodeMsg COACH_ADD_ERROR = new CodeMsg(-15001, "辅导员添加失败,请联系管理员！");
	public static CodeMsg COACH_ADD_EXIST_ERROR = new CodeMsg(-15002, "辅导员名称已存在,请更换！");
	public static CodeMsg COACH_EDITID_EXIST_ERROR=new CodeMsg(-15003,"该辅导员不存在！");
	public static CodeMsg COACH_EDITNAME_EXIST_ERROR=new CodeMsg(-15004,"辅导员姓名已存在！");
	public static CodeMsg COACH_EDIT_ERROR=new CodeMsg(-15005,"辅导员编辑失败！");
	public static CodeMsg COACH_DELETE_ERROR = new CodeMsg(-15006, "该辅导员下存在关联数据，不允许删除！");

}
