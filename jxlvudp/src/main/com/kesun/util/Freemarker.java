package kesun.util;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.*;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by lzyxl on 2017/5/24/0024.
 */
public class Freemarker {

    public static Template getTemplate(String ftlName,String ftlPath){
        Configuration config = new Configuration();
        config.setEncoding(Locale.CHINA,"utf-8");
        try {
            config.setDirectoryForTemplateLoading(new File(PathUtil.getClassResources()+"/"+ftlPath));
            Template template = config.getTemplate(ftlName);
            return template;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void printFile(String ftlName, Map<String,Object> root, String outFile, String filePath, String ftlPath) throws Exception{
        try {
            File file = new File(PathUtil.getClasspath() + filePath + outFile);
            if(!file.getParentFile().exists()){				//判断有没有父路径，就是判断文件整个路径是否存在
                file.getParentFile().mkdirs();				//不存在就全部创建
            }
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"));
            Template template = getTemplate(ftlName, ftlPath);
            template.process(root, out);					//模版输出
            out.flush();
            out.close();
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void print(String objectName){
        try {
//          String packageName = "";
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("objectName",objectName);
//          map.put("packageName",packageName);
            map.put("objectNameLower",objectName.toLowerCase());
            String filePath = "src/main/java/com/kesun";
            String ftlPath="ftl";
            Freemarker.printFile("serviceTemplate.ftl", map, "/bll/impl/"+objectName+"ServiceImpl.java", filePath, ftlPath);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*
    运行这里，生成service层代码
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("输入实体类名：");
        print(sc.next());
    }
}
