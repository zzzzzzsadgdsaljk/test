package kesun.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import kesun.entity.AbsSuperObject;
import kesun.entity.SearchViewParam;
import kesun.util.JSONAndObject;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


/*********************************
 * 类名：控制层公共操作类
 * 描述：定义控制层公共操作。
 * 作者：wph
 * 创建日期：2017-5-15 10:50:5
 * 版本：1.0.0.0
 * *******************************/
public class ControlTool {
	/*根据页面传递的常规JSON参数，获取*/
	public static SearchViewParam GetViewParam(JSONObject param)
	{ 	 
		SearchViewParam model=new SearchViewParam();
		try {
			model.condition=param.getString("condition");
			//model.doType=param.getInt("doType");
			model.pageNumber=param.getInt("pageNumber");
			model.rowsCount=param.getInt("rowsCount");
			return model;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return model;
		}
	}
	public static SearchViewParam GetViewParamNP(JSONObject param)
	{
		SearchViewParam model=new SearchViewParam();
		try {
			model.condition=param.getString("condition");
			return model;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return model;
		}
	}

	/*获取页面传递JSON操作doType的数据*/
	public static int GetDoType(JSONObject param)
	{  
		try {
			return param.getInt("doType"); 
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}

	}
	
	/*获取页面传递JSON操作指定key的数据*/
	public static Object GetJSONValue(JSONObject param,String key)
	{  
		try {
			return param.get(key); 
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	/*
	* 获取客户端请求参数，客户端的数据必须为JSON对象，返回JSON对象
	* */
	public static JSONObject GetRequestJSON(HttpServletRequest request, HttpServletResponse response)
	{
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		try
		{
			String json= JSONAndObject.GetPostData(request.getInputStream(), request.getContentLength(), "utf-8");
		   return JSONAndObject.GetJSONObject(json);
		}catch(IOException e)
		{
			return null;
		}

	}
    /*将JSON格式字符串数据转换成指定的目标对象*/
	public static <T> T GetJSONToJavaBean(HttpServletRequest request, HttpServletResponse response,Class<T> javaBean)
	{
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		try
		{
			String json= JSONAndObject.GetPostData(request.getInputStream(), request.getContentLength(), "utf-8");
			T temp=JSON.parseObject(json, new TypeReference<T>() {});
			return temp;
		}catch(IOException e)
		{
			return null;
		}

	}
	public static PrintWriter GetResonseOutObject( HttpServletResponse response)
	{
		try {
			return response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
	}
	}
}
