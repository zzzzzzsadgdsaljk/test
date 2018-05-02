package kesun.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * String的相关操作工具
 *
 */
public class StringUtils extends org.apache.commons.lang.StringUtils{
	/*判断对象组是否为空，如果任何一个为空，返回true，否则返回FALSE*/
	public static boolean IsBlank(Object...objects){
		Boolean result = false ;
		for (Object object : objects) {
			if(null == object || "".equals(object.toString().trim()) 
					|| "null".equals(object.toString().trim())){
				result = true ; 
				break ; 
			}
		}
		return result ; 
	}
	/*根据参数大小产生随机的字符串*/
	public static String GetRandom(int length) {
		String val = "";
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			// 输出字母还是数字
			String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
			// 字符串
			if ("char".equalsIgnoreCase(charOrNum)) {
				// 取得大写字母还是小写字母
				int choice = random.nextInt(2) % 2 == 0 ? 65 : 97;
				val += (char) (choice + random.nextInt(26));
			} else if ("num".equalsIgnoreCase(charOrNum)) { // 数字
				val += String.valueOf(random.nextInt(10));
			}
		}
		return val.toLowerCase();
	}
	/*判断对象组不能空，如果都不为空返回true，否则返回false*/
	public static boolean IsNotBlank(Object...objects){
		return !IsBlank(objects);
	}
	public static boolean IsBlank(String...objects){
		Object[] object = objects ;
		return IsBlank(object);
	}
	public static boolean IsNotBlank(String...objects){
		Object[] object = objects ;
		return !IsBlank(object);
	}
	public static boolean IsBlank(String str){
		Object object = str ;
		return IsBlank(object);
	}
	public static boolean IsNotBlank(String str){
		Object object = str ;
		return !IsBlank(object);
	}

	public static String TrimToEmpty(Object str){
	  return (IsBlank(str) ? "" : str.toString().trim());
	}
    
    /**
	 * 转换成Unicode
	 */
    public static String ToUnicode(String str) {
        String as[] = new String[str.length()];
        String s1 = "";
        for (int i = 0; i < str.length(); i++) {
        	int v = str.charAt(i);
        	if(v >=19968 && v <= 171941){
	            as[i] = Integer.toHexString(str.charAt(i) & 0xffff);
	            s1 = s1 + "\\u" + as[i];
        	}else{
        		 s1 = s1 + str.charAt(i);
        	}
        }
        return s1;
     }
    /**
     * 判断字符串是否包含汉字
     */
    public static Boolean IsContainsCHN(String txt){
    	if(isBlank(txt)){
    		return false;
    	}
    	for (int i = 0; i < txt.length(); i++) { 

    		String bb = txt.substring(i, i + 1); 

    		boolean cc = Pattern.matches("[\u4E00-\u9FA5]", bb);
    		if(cc)
    		return cc ;
    	}
		return false;
    }
    /**
     * 去掉HTML代码
     */
    public static String RemoveHtml(String news) {
      String s = news.replaceAll("amp;", "").replaceAll("<","<").replaceAll(">", ">");
      
      Pattern pattern = Pattern.compile("<(span)?\\sstyle.*?style>|(span)?\\sstyle=.*?>", Pattern.DOTALL);
      Matcher matcher = pattern.matcher(s);
      String str = matcher.replaceAll("");
      
      Pattern pattern2 = Pattern.compile("(<[^>]+>)",Pattern.DOTALL);
      Matcher matcher2 = pattern2.matcher(str);
      String strhttp = matcher2.replaceAll(" ");
      
      
      String regEx = "(((http|https|ftp)(\\s)*((\\:)|：))(\\s)*(//|//)(\\s)*)?"
         + "([\\sa-zA-Z0-9(\\.|．)(\\s)*\\-]+((\\:)|(:)[\\sa-zA-Z0-9(\\.|．)&%\\$\\-]+)*@(\\s)*)?"
         + "("
         + "(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])"
         + "(\\.|．)(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)"
         + "(\\.|．)(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)"
         + "(\\.|．)(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])"
         + "|([\\sa-zA-Z0-9\\-]+(\\.|．)(\\s)*)*[\\sa-zA-Z0-9\\-]+(\\.|．)(\\s)*[\\sa-zA-Z]*"
         + ")"
         + "((\\s)*(\\:)|(：)(\\s)*[0-9]+)?"
         + "(/(\\s)*[^/][\\sa-zA-Z0-9\\.\\,\\?\\'\\\\/\\+&%\\$\\=~_\\-@]*)*";
      Pattern p1 = Pattern.compile(regEx,Pattern.DOTALL);
      Matcher matchhttp = p1.matcher(strhttp);
      String strnew = matchhttp.replaceAll("").replaceAll("(if[\\s]*\\(|else|elseif[\\s]*\\().*?;", " ");
      
      
      Pattern patterncomma = Pattern.compile("(&[^;]+;)",Pattern.DOTALL);
      Matcher matchercomma = patterncomma.matcher(strnew);
      String strout = matchercomma.replaceAll(" ");
      String answer = strout.replaceAll("[\\pP‘’“”]", " ")
        .replaceAll("\r", " ").replaceAll("\n", " ")
        .replaceAll("\\s", " ").replaceAll("　", "");

      
      return answer;
    }
    /**
	 * 将字符串数组转成List,去掉数组中的空字符串
	 */
	public static List<String> Array2ListFilterBlank(String[] array){
		List<String> list = new ArrayList<String>();
		for (String string : array) {
			if(StringUtils.isNotBlank(string)){
				list.add(string);
			}
		}
		return list;
	}
	/**
	 * 把数组转换成set
	 */
	public static Set<?> Array2Set(Object[] array) {
		Set<Object> set = new TreeSet<Object>();
		for (Object obj : array) {
			if(null != obj){
				set.add(obj);
			}
		}
		return set;
	}
	public static Set<String> Array2Set(String... array) {
		Set<String> set = new TreeSet<String>();
		for (String obj : array) {
			if(null != obj){
				set.add(obj);
			}
		}
		return set;
	}
	/**
	 * serializable toString
	 */
	public static String ToString(Serializable serializable) {
		if(null == serializable){
			return null;
		}
		try {
			return (String)serializable;
		} catch (Exception e) {
			return serializable.toString();
		}
	}
   
}
