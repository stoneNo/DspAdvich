package com.adivch.spider.controller.base;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;

import com.adivch.spider.common.Constant;



/**
 * @author StoneTian
 *2017年7月11日
 */
public abstract class BaseController {

	protected static ObjectMapper mapper = new ObjectMapper();

	protected static JsonFactory factory = mapper.getJsonFactory();
	public BaseController() {
	}
	protected void writeJSON(HttpServletResponse response, String json)
			throws IOException {
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().write(json);
	}

	protected void writeJSON(HttpServletResponse response, Object obj)
			throws IOException {
		if(obj==null){
			writeJSON(response, Constant.RESULT_NOT_FOUND);
		}
		else
		{
			response.setContentType("text/html;charset=utf-8");
			JsonGenerator responseJsonGenerator = factory.createJsonGenerator(
					response.getOutputStream(), JsonEncoding.UTF8);
			responseJsonGenerator.writeObject(obj);
		}
		
	}

	public void writeResult(HttpServletResponse response,String resultCode) throws IOException{
		writeResult(response, resultCode, null);
	}
	public void writeResult(HttpServletResponse response, String resultCode,String message)
			throws IOException {
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append(
				'"' + "resultCode" + '"' + ":" + '"' + resultCode + '"' + ",");
		if(message==null || message.equals("")){
			switch (resultCode) {
				case Constant.ACCOUNT_ERROR_CODE :
					message = "账户错误";
					break;
				case Constant.PWD_ERROR_CODE :
					message = "密码错误";
					break;
				case Constant.ACCOUNT_FREEZE :
					message = "账户失效";
					break;
				case Constant.SUCCESS_CODE :
					message = "操作成功";
					break;
				case Constant.MODIFY_PWD_SUCCESS :
					message = "修改密码成功";
					break;
				case Constant.MODIFY_PWD_FAIL :
					message = "修改密码失败";
					break;
				case Constant.OLD_PWD_INPUT_FAIL :
					message = "抱歉，旧密码与原密码输入不一致";
					break;
				case Constant.DELETE_CUSTOMER_FAIL:
					message = "删除客户失败!";
					break;
				case Constant.ADD_CUSTOMER_FAIL:
					message = "添加新客户操作失败!";
					break;
				case Constant.REQUEST_ERROR:
					message = "请求结果错误！";
					break;
				case Constant.ADD_OREADY_EXIT:
					message = "客户存在!";
					break;
				case Constant.EMAIL_NOTEXIST:
					message = "邮件地址不存在!";
					break;
				case Constant.EMAIL_SEND_FAIL:
					message = "邮件发送失败,请稍后重试!";
					break;
				case Constant.EMAIL_SEND_NOCONTENT:
					message = "请先定制有效的报告模板信息";
					break;
				case Constant.RESULT_EMPTY:
					message = "没有找到您要的数据!";
					sb.append('"' + "total" + '"' + ":"  + 0 +"," );
					sb.append('"' + "rows" + '"' + ":"  + "[]" +"," );
					break;
					
			}
		}
		sb.append('"' + "message" + '"' + ":" + '"' + message + '"');
		sb.append("}");
		response.getWriter().write(sb.toString());
		return;
	}

	public void writeJSON(HttpServletResponse response,int resultTypeResult) throws IOException{
		JsonGenerator responseJsonGenerator = factory.createJsonGenerator(
				response.getOutputStream(), JsonEncoding.UTF8);
		responseJsonGenerator.writeStartObject();
		responseJsonGenerator.writeObjectField("resultType", resultTypeResult+"");
		responseJsonGenerator.writeEndObject();
		responseJsonGenerator.close();
	}
	public void writeJSON(JsonGenerator responseJsonGenerator, List<Object> list) throws IOException {

		responseJsonGenerator.writeObject(list);

	}
	public void writeJSON(HttpServletResponse response, List list, String total) throws IOException {
		JsonGenerator responseJsonGenerator = factory.createJsonGenerator(response.getOutputStream(),
				JsonEncoding.UTF8);
		responseJsonGenerator.writeStartObject();// 开始写入json格式的字符串此语句相当于："{"
		responseJsonGenerator.writeObjectField("total", total);
		responseJsonGenerator.writeFieldName("rows"); // 创建一个数组类似：rows:[
		writeJSON(responseJsonGenerator, list);
		responseJsonGenerator.writeEndObject();// 结束json对象
		responseJsonGenerator.close();
	}

	public void writeJSON(HttpServletResponse response, List list, int total) throws IOException {
		writeJSON( response,  list,  total+"");
	}

	public boolean JudgeIsMoblie(HttpServletRequest request) {
		boolean isMoblie = false;
		String[] mobileAgents = {"iphone", "android", "phone", "mobile", "wap",
				"netfront", "java", "opera mobi", "opera mini", "ucweb",
				"windows ce", "symbian", "series", "webos", "sony",
				"blackberry", "dopod", "nokia", "samsung", "palmsource", "xda",
				"pieplus", "meizu", "midp", "cldc", "motorola", "foma",
				"docomo", "up.browser", "up.link", "blazer", "helio", "hosin",
				"huawei", "novarra", "coolpad", "webos", "techfaith",
				"palmsource", "alcatel", "amoi", "ktouch", "nexian", "ericsson",
				"philips", "sagem", "wellcom", "bunjalloo", "maui",
				"smartphone", "iemobile", "spice", "bird", "zte-", "longcos",
				"pantech", "gionee", "portalmmm", "jig browser", "hiptop",
				"benq", "haier", "^lct", "320x320", "240x320", "176x220",
				"w3c ", "acs-", "alav", "alca", "amoi", "audi", "avan", "benq",
				"bird", "blac", "blaz", "brew", "cell", "cldc", "cmd-", "dang",
				"doco", "eric", "hipt", "inno", "ipaq", "java", "jigs", "kddi",
				"keji", "leno", "lg-c", "lg-d", "lg-g", "lge-", "maui", "maxo",
				"midp", "mits", "mmef", "mobi", "mot-", "moto", "mwbp", "nec-",
				"newt", "noki", "oper", "palm", "pana", "pant", "phil", "play",
				"port", "prox", "qwap", "sage", "sams", "sany", "sch-", "sec-",
				"send", "seri", "sgh-", "shar", "sie-", "siem", "smal", "smar",
				"sony", "sph-", "symb", "t-mo", "teli", "tim-",
				/* "tosh", */ "tsm-", "upg1", "upsi", "vk-v", "voda", "wap-",
				"wapa", "wapi", "wapp", "wapr", "webc", "winw", "winw", "xda",
				"xda-", "Googlebot-Mobile"};
		if (request.getHeader("User-Agent") != null) {
			for (String mobileAgent : mobileAgents) {
				if (request.getHeader("User-Agent").toLowerCase()
						.indexOf(mobileAgent) >= 0) {
					isMoblie = true;
					break;
				}
			}
		}
		return isMoblie;
	}
}
