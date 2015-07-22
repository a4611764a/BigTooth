package com.zb.bittooth;


import com.zb.bittooth.utils.SDCardUtils;

public class Urls {
	
	
	// public static String
	// url_base="http://192.168.1.103:8080/ibamboo_weixin/";// 本地
	public static String url_base = "http://www.ibamboo.cc/ibamboo_weixin/";// 外网
	/************* 图片存储路径 ********************/
	public static String imageCachePath = SDCardUtils.getExternalStoragePath()
			+ "/bigtooth/image/";// 应用图片存储路径
	/************* 笑料 ********************/
	public static String url_get_jokes = url_base + "BigToothServlet";// 获取笑料
	/************* 我的 ********************/
	public static String url_post_uuid = url_base + "InsertUserServlet";// 上传手机设备标识符
	public static String url_post_userInfo = url_base
			+ "InsertUserLocationServlet";// 上传用户手机location信息
	/************* 大牙 ********************/
	public static String url_post_userRegister = url_base
			+ "InsertUserNameAndImageServlet";// 上传用户手机注册信息
	public static String url_get_tagList = url_base + "TagListServlet";// 获取标签列表
	public static String url_get_userInfor = url_base + "UserInfor";// 判断用户是否登录并拉去用户信息
	
	/************* 微信 ********************/
	public static String WX_APP_ID="";
}
