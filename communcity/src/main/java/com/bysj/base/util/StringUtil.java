package com.bysj.base.util;

import com.alibaba.fastjson.JSONObject;
import com.bysj.base.bean.CodeMsg;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

/**
 * 项目通用工具类
 * @author Administrator
 *
 */
public class StringUtil {
	
	
	/**
	 * 返回指定格式的日期字符串
	 * @param date
	 * @param formatter
	 * @return
	 */
	public static String getFormatterDate(Date date,String formatter){
		SimpleDateFormat sdf = new SimpleDateFormat(formatter);
		return sdf.format(date);
	}
	
	/**
	 * 判断请求是否是ajax
	 * @param request
	 * @return
	 */
	public static boolean isAjax(HttpServletRequest request){
		String header = request.getHeader("X-Requested-With");
		if("XMLHttpRequest".equals(header))return true;
		return false;
	}
	
	/**
	 * 从流读取字符串
	 * @param inputStream
	 * @return
	 */
	public static String getStringFromInputStream(InputStream inputStream){
		String string = "";
		try {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"GB2312"));
			String buf = null;
			try {
				while((buf = bufferedReader.readLine()) != null){
					string += buf;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				try {
					bufferedReader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return string;
	}
	public static Integer getUUIDInOrderId(){
		Integer orderId=UUID.randomUUID().toString().hashCode();
		orderId = orderId < 0 ? -orderId : orderId; //String.hashCode() 值会为空
		return orderId;
	}
	public static String generateSn(String prefix,String suffix){
		Calendar now = Calendar.getInstance();
		//System.out.println("年: " + now.get(Calendar.YEAR));
		String s =  getUUIDInOrderId().toString().replace("-","");
		return prefix + now.get(Calendar.YEAR)+s.substring(0,6).hashCode() + suffix;
	}

	/**
	 * 验证输入的邮箱格式是否符合
	 * @param email
	 * @return 是否合法
	 */
	public static boolean emailFormat(String email)
	{
		boolean tag = true;
		final String pattern1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		final Pattern pattern = Pattern.compile(pattern1);
		final Matcher mat = pattern.matcher(email);
		if (!mat.find()) {
			tag = false;
		}
		return tag;
	}

	/**
	 * 验证是否是手机号
	 * @param
	 * @return
	 */
	public static boolean  isMobile(String str) {
		Pattern p = null;
		Matcher m = null;
		boolean b = false;
		String s2="^[1](([3|5|8][\\d])|([4][5,6,7,8,9])|([6][5,6])|([7][3,4,5,6,7,8])|([9][8,9]))[\\d]{8}$";// 验证手机号
		if(StringUtils.isNotBlank(str)){
			p = Pattern.compile(s2);
			m = p.matcher(str);
			b = m.matches();
		}
		return b;
	}


	/**
	 * 生成唯一字符串
	 * @return
	 */
	public static String generateSn(){
		return UUID.randomUUID().toString().toUpperCase().replace("-", "");
	}

	public static String getMac(){
		String mac = "";
		try {
			InetAddress localHost = InetAddress.getLocalHost();
			byte[] hardwareAddress = NetworkInterface.getByInetAddress(localHost).getHardwareAddress();
			StringBuffer sb = new StringBuffer("");
			for(int i=0; i<hardwareAddress.length; i++) {
				//字节转换为整数
				int temp = hardwareAddress[i]&0xff;
				String str = Integer.toHexString(temp);
				//System.out.println("每8位:"+str);
				if(str.length()==1) {
					sb.append("0"+str);
				}else {
					sb.append(str);
				}
			}
			mac = sb.toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mac.toUpperCase();
	}



	public static String readFileToString(File file){
		String string = "";
		if(file != null){
			try {
				BufferedReader br = new BufferedReader(new FileReader(file));
				String line = null;
				while ((line = br.readLine()) != null) {
					string += line;
				}
				br.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return string;
	}


}
