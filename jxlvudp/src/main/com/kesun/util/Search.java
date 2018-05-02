package kesun.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List; 
import java.util.Map;

import kesun.entity.Page;
import org.json.JSONException;
import org.json.JSONObject;

 
public class Search {
	public static List<String> GetAllFields(Object o)
	{ 
		try {
			Class<?> cls = Class.forName(o.getClass().getName());
			Method[] methods=cls.getMethods();
			List<String> fields=new ArrayList<String>();

			for(Method m:methods)
			{
				if (m.getName().indexOf("get")==0) 
				{   
					fields.add(m.getName().substring(3));
				}
			} 
			return fields;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	/** 
	 * ����������ȡ����ֵ 
	 * */  
	public static Object GetPropertyValueByName(String fieldName, Object o) {  
		if (o==null) return null;
		String[] fieldNames=fieldName.split("\\.");//������磺abc.ab.c�ṹ�������ȡ����������
		Object value =o; 

		for(String str:fieldNames)
		{  
			String firstLetter = str.substring(0, 1).toUpperCase();    
			String getter = "get" + firstLetter + str.substring(1);    
			try{
				if (value==null) return null;
				Method method = value.getClass().getMethod(getter);
				if (method==null) return null;
				value = method.invoke(value); 
			}catch(Exception ex)
			{ 
				if(ex instanceof java.lang.reflect.InvocationTargetException){  

					((java.lang.reflect.InvocationTargetException)ex).getTargetException().printStackTrace();

				}  
				System.out.println("�����ֶΣ�"+fieldName+"�е�-��"+str);
				ex.printStackTrace();					
				return null;
			}
		} 		
		return value;    

	}  
	/*
	 * ���ܣ����÷�����Ƹ���������ȡ���Զ���
	 * @param:cls��Class����
	 * @param:field������
	 * @return:�������Զ���
	 * */
	public static Field GetField(Class<?> cls,String field)
	{
		if (cls==null) return null;
		try {
			return cls.getDeclaredField(field);
		} catch (NoSuchFieldException e2) {
			if (cls.getSuperclass()!=null) 
				return GetField(cls.getSuperclass(),field);
		}
		return null;
	}

	/*
	 * ��̬����ַ���������и�ֵ����������Է��д������ʽ�磺a.b.c
	 * */
	public static Boolean SetPropertyValueByName(String fieldName, Object o,Object value) {  
		Class<?> cls;
		try {
			cls = Class.forName(o.getClass().getName());
		} catch (ClassNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
			return false;
		}//��ȡ��������� 
		try {
			String[] fieldNames=fieldName.split("\\.");
			if (fieldNames.length<=1)
			{
				Field f=GetField(cls,fieldName);
				if (f==null) return false;
				f.setAccessible(true); 
				f.set(o, value);			
				return true;
			}
			else {
				Object temp=o;
				for(int i=0;i<fieldNames.length-1;i++)
				{
					Field f=GetField(temp.getClass(),fieldNames[i]);
					if (f==null) return false; 

					f.setAccessible(true); 
					try {
						temp=Class.forName(f.getType().getName()).newInstance();
						f.set(o,temp);
					} catch (InstantiationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return false;
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return false;
					}  
				}
				Field f=GetField(temp.getClass(),fieldNames[fieldNames.length-1]);
				if (f==null) return false;
				f.setAccessible(true); 
				f.set(temp, value);

				return true;  
			}
		}catch (IllegalArgumentException e) { 
			e.printStackTrace();
			return false;
		} catch (IllegalAccessException e) { 
			e.printStackTrace();
			return false;
		}// ��̬����ֵ   	 
	}
	/*
	 * ������obj��ָ������fieldsת��ΪJson�ַ��ʽ
	 * */
	public static String ToObjectJson(Object obj,List<String> fields)
	{
		if (obj==null || fields==null || fields.size()==0) return "[{}]";
		JSONObject jsonObject = new JSONObject();
		for(String str:fields)		
		{
			try {
				jsonObject.put(str.split("\\.")[0], GetPropertyValueByName(str,obj));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "[{}]";
			}
		}
		return jsonObject.toString();
	}
	/*
	 * ������obj��ָ������fieldsת��ΪJson�ַ��ʽ
	 * */
	public static String ToObjectJson(Object obj,List<String> fields,List<String> fieldsAlis)
	{
		if (obj==null || fields==null || fields.size()==0) return "{}";
		JSONObject jsonObject = new JSONObject();
		for(int i=0;i<fields.size();i++)		
		{
			try {
				if (fieldsAlis!=null && fieldsAlis.size()>0)
					jsonObject.put(fieldsAlis.get(i), GetPropertyValueByName(fields.get(i),obj));
				else
					jsonObject.put(fields.get(i).split("\\.")[0], GetPropertyValueByName(fields.get(i),obj));	
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "{}";
			}
		}
		return jsonObject.toString();
	}
	/*
	 * �������б�objsת��ΪJson�ַ��ʽ
	 * */
	public static String ToObjectJson(List<Object> objs,List<String> fields)
	{
		if (objs==null || fields==null || fields.size()==0) return "[]";
		String strTemp="";
		for(Object o:objs)
		{
			if (strTemp=="")
				strTemp=ToObjectJson(o,fields);
			else
				strTemp+=","+ToObjectJson(o,fields);
		}
		return "["+strTemp+"]";
	}
	
	public static String ToJsonFromSet(java.util.Set<Object> objs,List<String> fields)
	{
		if (objs==null || fields==null || fields.size()==0) return "[]";
		String strTemp="";
		for(Object o:objs)
		{
			if (strTemp=="")
				strTemp=ToObjectJson(o,fields);
			else
				strTemp+=","+ToObjectJson(o,fields);
		}
		return "["+strTemp+"]";
	}
	/*
	 * 将数据源转换成指定fields的JSON格式
	 * */
//	public static String ToOjectJsonByResultSet(ResultSet source,List<String> fields)
//	{
//		if (source==null || fields==null || fields.size()==0) return "[]";
//		String result="";
//		 
//		try {
//			while(source.next())
//			{  
//				JSONObject jsonObject = new JSONObject();
//				//String temp="";
//				for(String f:fields)
//				{
//					try {
//						jsonObject.put(f,source.getString(f)==null?"":source.getString(f));
//					} catch (JSONException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//						return "[]";
//					}
////					if (temp.equals(""))
////						temp="\""+f+"\":\""+source.getString(f)+"\"";
////					else
////						temp+=",\""+f+"\":\""+source.getString(f)+"\"";
//				}
//				if (result.equals(""))
//					result=jsonObject.toString();
//				else {
//					result+=","+jsonObject.toString();
//				}
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return "[]";
//		}
//		 
//		return "["+result+"]";
//	}
	public static String ToOjectJsonByResultSet(List<Map<String,Object>> source,List<String> fields)
	{
		if (source==null || fields==null || fields.size()==0) return "[]";
		String result="";
		 
		java.util.Iterator<Map<String,Object>> its=source.iterator();
		while(its.hasNext())
		{  
			JSONObject jsonObject = new JSONObject();
			Map<String,Object> it=its.next();
			//String temp="";
			for(String f:fields)
			{
				try {
					jsonObject.put(f,it.get(f.toUpperCase())==null?"":Tool.GetMapValue(it,f));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return "[]";
				}
//					if (temp.equals(""))
//						temp="\""+f+"\":\""+source.getString(f)+"\"";
//					else
//						temp+=",\""+f+"\":\""+source.getString(f)+"\"";
			}
			if (result.equals(""))
				result=jsonObject.toString();
			else {
				result+=","+jsonObject.toString();
			}
		}
		 
		return "["+result+"]";
	}
	/*��List<Object>�����ڶ�ά���飬ת����JSON���Դ*/
	public static String ToArrayObjectJson(List<Object> objs,List<String> fields)
	{
		if (objs==null || fields==null || fields.size()==0) return "[]";
		StringBuilder sb=new StringBuilder();
		for(Object o:objs)
		{
			if(sb.toString()=="")
				sb.append("{");
			else {
				sb.append(",{");
			}
			Object[] source=(Object[])o;
			if (source.length!=fields.size()) return "[]";
			int i=0;
			for(String f:fields)
			{
				if (i==0)
					sb.append("\""+f+"\""+":\""+source[i++]+"\"");
				else {
					sb.append(",\""+f+"\""+":\""+source[i++]+"\""); 
				}
			}
			sb.append("}");

		}
		return "["+sb.toString()+"]";
	}
	/*��List<Object>�����ڶ�ά���飬ת����JSON���Դ*/
	public static String ToArrayObjectJson(List<Object> objs,List<String> fields,int pageNumber,int rowsCount)
	{
		if (objs==null || fields==null || fields.size()==0) 
			return "{\"total\":0,\"rows\":0}"; 

		int start=(pageNumber-1)*rowsCount;//��ʼ��������
		int end=start+rowsCount;//��ֹ��������
		if (end+1>objs.size())
			end=objs.size();
		int count=objs.size();//��¼����
		objs=objs.subList(start, end);

		String strTemp=ToArrayObjectJson(objs,fields);

		return "{\"total\":"+count+",\"rows\":"+strTemp+"}"; 
	}
	/*
	 * �������б�objsת��ΪJson�ַ��ʽ
	 * */
//	public static String ToObjectJson(List<Object> objs,List<String> fields,List<String> fieldsAlis)
//	{
//		if (objs==null || fields==null || fields.size()==0) return "[]";
//		String strTemp="";
//		for(Object o:objs)
//		{
//			if (strTemp=="")
//				strTemp=ToObjectJson(o,fields,fieldsAlis);
//			else
//				strTemp+=","+ToObjectJson(o,fields,fieldsAlis);
//		}
//		return "["+strTemp+"]";
//	}
	public static String ToObjectJson(ResultSet objs,List<String> fields,List<String> fieldsAlis)
	{
		if (objs==null || fields==null || fields.size()==0) return "[]";
		String strTemp="";
		try {
			while(objs.next())
			{
				String rows="";//临时行json
				/*解析第一行数据*/
				for (int i=0;i<fields.size();i++) {
					if (rows=="")
						rows="\""+fieldsAlis.get(i)+"\":\""+objs.getObject(fields.get(i))+"\"";
					else {
						rows+=","+"\""+fieldsAlis.get(i)+"\":\""+objs.getObject(fields.get(i))+"\"";
					}
				}
				if (strTemp=="")
					strTemp="{"+rows+"}";
				else {
					strTemp=",{"+rows+"}";
				}
			}
		} catch (SQLException e) { 
			e.printStackTrace();
			return "[]";
		} 
		return "["+strTemp+"]";
	}
	
	public static String ToObjectJson(List<Map<String,Object>> objs,List<String> fields,List<String> fieldsAlis)
	{
		if (objs==null || fields==null || fields.size()==0) return "[]";
		String strTemp="";
		java.util.Iterator<Map<String,Object>> its=objs.iterator();
		 
		while(its.hasNext())
		{
			Map<String,Object> obj=its.next();
			String rows="";//临时行json
			/*解析第一行数据*/
			for (int i=0;i<fields.size();i++) {
				if (rows=="")
					rows="\""+fieldsAlis.get(i)+"\":\""+Tool.GetMapValue(obj, fields.get(i))+"\"";
				else {
					rows+=","+"\""+fieldsAlis.get(i)+"\":\""+Tool.GetMapValue(obj, fields.get(i))+"\"";
				}
			}
			if (strTemp=="")
				strTemp="{"+rows+"}";
			else {
				strTemp=",{"+rows+"}";
			}
		} 
		return "["+strTemp+"]";
	}
	
	public static String ToObjectJson(ResultSet objs,List<String> fields,int pageNumber,int rowsCount)
	{  
		if (objs==null || fields==null || fields.size()==0) 
			return "{\"total\":0,\"rows\":0}"; 

		int start=(pageNumber-1)*rowsCount;  
		int  count =0;
		try {
			objs.last();
			count = objs.getRow(); //获得ResultSet的总行数 

			objs.first();//指针返回

		} catch (SQLException e1) { 
			e1.printStackTrace();
			return "{\"total\":0,\"rows\":0}"; 
		}   

		String strTemp="";
		int pos=0;
		try {
			do
			{  
				if (pos>=start && pos<start+rowsCount)
				{
					String rows="";//临时行json 
					/*解析第一行数据*/
					for (int i=0;i<fields.size();i++) {
						if (rows=="")
							rows="\""+fields.get(i)+"\":\""+objs.getObject(fields.get(i))+"\"";
						else {
							rows+=","+"\""+fields.get(i)+"\":\""+objs.getObject(fields.get(i))+"\"";
						}
					}
					if (strTemp=="")
						strTemp="{"+rows+"}";
					else {
						strTemp+=",{"+rows+"}";
					}
				}
				pos++;
			}while(objs.next());

		} catch (SQLException e) { 
			e.printStackTrace();
			return "{\"total\":0,\"rows\":0}"; 
		} 
		return "{\"total\":"+count+",\"rows\":["+strTemp+"]}"; 

	}

	public static String ToObjectJson(List<Map<String,Object>> objs,List<String> fields,int pageNumber,int rowsCount)
	{  
		if (objs==null || fields==null || fields.size()==0) 
			return "{\"total\":0,\"rows\":0}"; 

		int start=(pageNumber-1)*rowsCount;  
		int  count =objs.size(); 
		String strTemp="";
		int pos=0;
		java.util.Iterator<Map<String,Object>> its=objs.iterator();
		while(its.hasNext())
		{
			if (pos>=start && pos<start+rowsCount)
			{ 
				Map<String,Object> it=its.next();
				String rows="";//临时行json 
				/*解析第一行数据*/
				for (int i=0;i<fields.size();i++) {
					if (rows=="")
						rows="\""+fields.get(i)+"\":\""+Tool.GetMapValue(it, fields.get(i))+"\"";
					else {
						rows+=","+"\""+fields.get(i)+"\":\""+Tool.GetMapValue(it, fields.get(i))+"\"";
					}
				}
				if (strTemp=="")
					strTemp="{"+rows+"}";
				else {
					strTemp+=",{"+rows+"}";
				}
			}
			pos++;	
		} 
		return "{\"total\":"+count+",\"rows\":["+strTemp+"]}"; 

	}
	public static String ToObjectJson(Page objs, List<String> fields, int pageNumber, int rowsCount)
	{  
		if (objs==null || objs.getRows()==null || fields==null || fields.size()==0)
			return "{\"total\":0,\"rows\":[]}"; 

		int start=(pageNumber-1)*rowsCount;  
		int  count =objs.getTotal(); 
		String strTemp="";
		int pos=0;
		 
		java.util.Iterator<Map<String,Object>> its=objs.getRows().iterator();
		 
		while(its.hasNext())
		{
			if (pos>=start && pos<start+rowsCount)
			{ 
				Map<String,Object> it=its.next();
				String rows="";//临时行json 
				/*解析第一行数据*/
				for (int i=0;i<fields.size();i++) {
					if (rows=="")
						rows="\""+fields.get(i)+"\":\""+Tool.GetMapValue(it, fields.get(i))+"\"";
					else {
						rows+=","+"\""+fields.get(i)+"\":\""+Tool.GetMapValue(it, fields.get(i))+"\"";
					}
				}
				if (strTemp=="")
					strTemp="{"+rows+"}";
				else {
					strTemp+=",{"+rows+"}";
				}
			}
			pos++;	
		} 
		return "{\"total\":"+count+",\"rows\":["+strTemp+"]}"; 

	}
	/*
	 * ����ݽ��з�ҳת��Ϊjson��ʽ
	 * objs���Դ��fields��Ҫ��ʾ���ֶ�
	 * pageNumber��ʾҳ��
	 * rowsCount��ʾÿҳ��ʾ��¼����
	 * */
//	public static String ToObjectJson(List<Object> objs,List<String> fields,int pageNumber,int rowsCount)
//	{
//		if (objs==null || fields==null || fields.size()==0) 
//			return "{\"total\":0,\"rows\":0}"; 
//
//		int start=(pageNumber-1)*rowsCount;//��ʼ��������
//		int end=start+rowsCount;//��ֹ��������
//		if (end+1>objs.size())
//			end=objs.size();
//		int count=objs.size();//��¼����
//		objs=objs.subList(start, end);
//
//		String strTemp=ToObjectJson(objs,fields);
//
//		return "{\"total\":"+count+",\"rows\":"+strTemp+"}"; 
//	}

	/*
	 * ����ݽ��з�ҳת��Ϊjson��ʽ
	 * objs���Դ��fields��Ҫ��ʾ���ֶ�
	 * pageNumber��ʾҳ��
	 * rowsCount��ʾÿҳ��ʾ��¼����
	 * */
	public static String ToObjectJson(List<Object> objs,List<String> fields,List<String> fieldsAlias,int pageNumber,int rowsCount)
	{
		if (objs==null || fields==null || fields.size()==0) 
			return "{\"total\":0,\"rows\":0}";

		int start=(pageNumber-1)*rowsCount;//��ʼ��������
		int end=start+rowsCount;//��ֹ��������
		if (end+1>objs.size())
			end=objs.size();
		int count=objs.size();//��¼����
		objs=objs.subList(start, end);

		String strTemp=ToObjectJson(objs,fields,fieldsAlias);

		return "{\"total\":"+count+",\"rows\":"+strTemp+"}"; 
	}

	/** 
	 * JSON字符串特殊字符处理，比如：“\A1;1300” 
	 * @param s 
	 * @return String 
	 */  
	public static String String2Json(String s) {        
		StringBuffer sb = new StringBuffer();        
		for (int i=0; i<s.length(); i++) {  
			char c = s.charAt(i);    
			switch (c){  
			case '\"':        
				sb.append("\\\"");        
				break;        
			case '\\':        
				sb.append("\\\\");        
				break;        
			case '/':        
				sb.append("\\/");        
				break;        
			case '\b':        
				sb.append("\\b");        
				break;        
			case '\f':        
				sb.append("\\f");        
				break;        
			case '\n':        
				sb.append("\\n");        
				break;        
			case '\r':        
				sb.append("\\r");        
				break;        
			case '\t':        
				sb.append("\\LoginFilter");
				break;        
			default:        
				sb.append(c);     
			}  
		}      
		return sb.toString();     
	}  
}
