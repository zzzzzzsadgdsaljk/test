package kesun.controller;
import kesun.entity.LayuiUploadImage;
import kesun.entity.LayuiUploadImageItem;
import kesun.util.Tool;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


/**
 * 负责文件上传操作
 * Created by wph-pc on 2017/5/28.
 */
@Controller
public class UploadFile {
    private PrintWriter out;
    /*上传文件服务器保存地址*/
    private String getSavePath(HttpServletRequest request, HttpServletResponse response) throws Exception{
        ServletContext servletContext=request.getSession().getServletContext();
        String realPath = servletContext.getRealPath("uploadfiles");
        return realPath;
    }
    private List<String> doUploadFile(CommonsMultipartFile files[], HttpServletRequest request, HttpServletResponse response)
    {
        response.setContentType("text/html;charset=UTF-8");

        if(files==null || files.length==0) return null;

        String path=request.getSession().getServletContext().getRealPath("/uploadfiles");
        /*判断目录是否存在，如果不存在，则创建*/
        File dir = new File(path);
        if (!dir.exists())
            dir.mkdirs();

        List<String> lFiles=new ArrayList<String>();//已经成功的文件名
        for (int i = 0; i < files.length; i++) {
            // 获得原始文件名
            String fileName = files[i].getOriginalFilename();
            System.out.println("原始文件名:" + fileName);
            if (fileName.trim().equals("")) continue;
            // 新文件名
            String newFileName = Tool.CreateID() +fileName.substring(fileName.lastIndexOf(".")) ;
            if (!files[i].isEmpty()) {
                try {
                    FileOutputStream fos = new FileOutputStream(path+"/"
                            + newFileName);
                    InputStream in = files[i].getInputStream();
                    byte[] bytes = new byte[2048];//1024*2
                    int b = 0;
                    while ((b = in.read(bytes)) != -1) {
                        fos.write(bytes,0,b);
                    }
                    fos.close();
                    in.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
            lFiles.add(newFileName);

        }
        return lFiles;
    }
    @RequestMapping(value = "/uploadfile", method = RequestMethod.POST)
    @ResponseBody
    public Object uploadFile(@RequestParam("file") CommonsMultipartFile files[], HttpServletRequest request, HttpServletResponse response) throws java.io.IOException  {
       return doUploadFile(files,request,response);
    }

    @RequestMapping(value = "/uploadfileForLayUI", method = RequestMethod.POST)
    @ResponseBody
    public Object uploadFileForLayUI(@RequestParam("file") CommonsMultipartFile files[], HttpServletRequest request, HttpServletResponse response) throws java.io.IOException  {
        List<String> lFiles= doUploadFile(files,request,response);
        LayuiUploadImage obj=new LayuiUploadImage();
        obj.setCode("0");
        obj.setMsg("上传成功！");
        obj.setData(new LayuiUploadImageItem());

        String tempPath = request.getContextPath();
        String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+tempPath;

        obj.getData().setSrc(basePath+"/uploadfiles/"+lFiles.get(0));
        obj.getData().setTitle("图片");
        return obj;
    }
}
