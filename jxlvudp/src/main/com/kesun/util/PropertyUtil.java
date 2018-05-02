package kesun.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 属性文件读取工具
 * Created by wph-pc on 2017/11/20.
 */
public class PropertyUtil {
    private String fileName="";//文件名称
    private Properties props=null;//属性集合
    private void init()
    {
        props = new Properties();
        InputStream in = null;
        try {
              in = PropertyUtil.class.getClassLoader().getResourceAsStream(fileName);
               props.load(in);
        } catch (FileNotFoundException e) {
            props=null;
            e.printStackTrace();
            System.out.println("属性文件未找到");
        } catch (IOException e) {
            props=null;
            e.printStackTrace();
            System.out.println("出现IOException");
        } finally {
            try {
                if(null != in) {
                    in.close();
                }
            } catch (IOException e) {
                props=null;
                e.printStackTrace();
                System.out.println("属性文件流关闭出现异常");
            }
        }
    }
    public PropertyUtil(String filePath)
    {
        fileName=filePath;
        init();
    }

    public String getProperty(String key){
        if(null == props) {
            return "";
        }
        return props.getProperty(key);
    }

    public String getProperty(String key, String defaultValue) {
        if(null == props) {
            return "";
        }
        return props.getProperty(key, defaultValue);
    }

}
