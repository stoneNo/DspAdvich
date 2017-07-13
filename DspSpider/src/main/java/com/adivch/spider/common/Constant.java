package com.adivch.spider.common;

import java.util.HashMap;
import java.util.Map;

/**
 * @author StoneTian 2016年5月23日
 */
public interface Constant {

	public static String SUCCESS_CODE = "1000";
	public static String ACCOUNT_ERROR_CODE = "1001";
	public static String PWD_ERROR_CODE = "1002";
	public static String ACCOUNT_FREEZE = "2000";

	public static String MODIFY_PWD_SUCCESS = "3001";
	public static String MODIFY_PWD_FAIL = "3002";
	public static String OLD_PWD_INPUT_FAIL = "3003";
	
	
	public static final String ADD_CUSTOMER_FAIL = "4002";
	public static final String ADD_OREADY_EXIT = "4003";
	
	public static final String DELETE_CUSTOMER_FAIL = "5002";
	
	
	
	public static final String FILE_NOT_FOUND = "6000";

	public static final String RESULT_NOT_FOUND = "8000";
	
	public static final String RESULT_PARAMETER_ERROR= "8100";
	
	public static final String REQUEST_ERROR = "9000";
	
	public static final String RESULT_EMPTY = "8000";
	
	//邮件地址不存在
	public static final String EMAIL_NOTEXIST = "7000";
	//邮件发送失败
	public static final String EMAIL_SEND_FAIL = "7001";
	// 没找到需要发送的报表数据
	public static final String EMAIL_SEND_NOCONTENT = "7002";
	
	//每天后天从谷歌同步关键字的数量
	public static final int SYN_COUNT = 100;
	
	public static final int PAST_DAYS = -180;
	
	public static  Map<Object, Object> keywordsSearchVolumeCacheMap = new HashMap<Object, Object>();
}
