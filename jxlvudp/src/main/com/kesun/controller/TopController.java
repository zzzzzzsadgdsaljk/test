package kesun.controller;

import kesun.bll.SuperService;
import kesun.entity.AbsBusinessObject;
import kesun.entity.AbsSuperObject;
import kesun.entity.Page;
import kesun.entity.SearchViewParam;
import kesun.entity.system.User;
import kesun.util.JSONAndObject;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import shiro.TokenManage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 超级控制层类
 * Created by wph-pc on 2017/9/8.
 */
public abstract class TopController {
    public abstract SuperService getService();//获取服务层对象
    public abstract Map<String,Object> getConditionParam(JSONObject param);//获取查询参数
    public abstract Map<String,Object> setFindFilter(JSONObject param);//设置查询过滤条件，一般用户数据授权操作，可以为空
    public Map<String,Object> setFindFilter(){return null;}//设置过滤条件，可以重写
    /*设置权限过滤条件,使用时，建议重写*/
    public boolean[] setPowerFilter(){
        return null;
    }
    /*获取客户端参数初始化*/
     public abstract AbsSuperObject initParameter(HttpServletRequest request, HttpServletResponse response);

     /*构架返回客户端对象信息*/
     private Object getReturnObject(String code,String msg,Object obj)
     {
         ReturnBean returnObj=new ReturnBean();
         returnObj.setCode(code);
         returnObj.setMessage(msg);
         returnObj.setObj(obj);
         return  returnObj;
     }
     /*更新参数model实体信息,可以由子类重写*/
     public AbsSuperObject updateModel(AbsSuperObject model)
     {
         return model;
     }
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Object add(HttpServletRequest request, HttpServletResponse response) {
         AbsSuperObject model=updateModel(initParameter(request,response));//获取客户端参数信息
        getService().setModel(model);
        if(getService().isAdd()==false)
            return getReturnObject("00000","不符合新增条件，系统取消新增操作",model);
        return getReturnObject(String.valueOf(getService().add()),"新增操作",model);
    }

    /*根据上传后的Excel数据进行批量新增*/
    @RequestMapping(value = "/loadData",produces ="application/json;charset=UTF-8",method = RequestMethod.POST)
    @ResponseBody
    public Object loadData(HttpServletRequest request,@RequestBody String fileName) {
        String path=request.getSession().getServletContext().getRealPath("/uploadfiles")+"/"+fileName.replace("\"","");
        return getReturnObject("00000","Excel导入操作",getService().loadinData(path));
    }
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public Object edit(HttpServletRequest request, HttpServletResponse response) {
        AbsSuperObject model=updateModel(initParameter(request,response));//获取客户端参数信息
        getService().setModel(model);
        if(getService().isEdit()==false)
            return getReturnObject("00000","不符合修改条件，系统取消修改操作",model);

        return getReturnObject(String.valueOf(getService().edit()),"修改操作",model);
    }

    @RequestMapping(value = "/del", method = RequestMethod.POST)
    @ResponseBody
    public Object del(HttpServletRequest request, HttpServletResponse response) {
        AbsSuperObject model=updateModel(initParameter(request,response));//获取客户端参数信息
        getService().setModel(model);
        if(getService().isDelete()==false)
            return getReturnObject("00000","不符合删除条件，系统取消删除操作",model);

        return getReturnObject(String.valueOf(getService().del()),"删除操作",model);
    }
    @RequestMapping(value = "/deleteAll", method = RequestMethod.POST)
    @ResponseBody
    public Object deleteAll(HttpServletRequest request, HttpServletResponse response) {
        JSONObject param= ControlTool.GetRequestJSON(request,response);//获取页面JSON对象参数
        String ids= JSONAndObject.GetJsonStringValue(param, "ids");

        List<String> cons=new ArrayList<String>();
        for(String id:ids.split(","))
            cons.add(id);
        return getReturnObject(String.valueOf(getService().deleteAll(cons)),"批量删除操作",cons);
    }
    @RequestMapping(value = "/find", method = RequestMethod.POST)
    @ResponseBody
    public Object find(HttpServletRequest request, HttpServletResponse response) {
         JSONObject param=ControlTool.GetRequestJSON(request,response);//获取页面JSON对象参数
         Map<String,Object> values=getConditionParam(param);//获取查询参数
        /*合并数据过滤条件*/
        if (setFindFilter(param)!=null)
            values.putAll(setFindFilter(param));

         SuperService bll=getService();//获取业务对象
        if (bll==null)
            return getReturnObject("000000","系统没有提供业务对象",null);
        if (values==null || values.size()==0)
           return getReturnObject("10000","无条件查询",getService().find(values));
        else
           return getReturnObject("10001","根据条件查询",getService().find(values));
    }
    @RequestMapping(value = "/findForRow", method = RequestMethod.POST)
    @ResponseBody
    public Object findForRow(HttpServletRequest request, HttpServletResponse response) {
        JSONObject param=ControlTool.GetRequestJSON(request,response);//获取页面JSON对象参数
        Map<String,Object> values=getConditionParam(param);//获取查询参数
        /*合并数据过滤条件*/
        if (setFindFilter(param)!=null)
            values.putAll(setFindFilter(param));

        SuperService bll=getService();//获取业务对象
        if (bll==null)
            return getReturnObject("000000","系统没有提供业务对象",null);
        if (values==null || values.size()==0)
            return getReturnObject("10000","无条件查询",getService().findForMap(values));
        else
            return getReturnObject("10001","根据条件查询",getService().findForMap(values));
    }
    /*分页查找*/
    @RequestMapping(value = "/findByPage", method = RequestMethod.POST)
    @ResponseBody
    public Object findByPage(HttpServletRequest request, HttpServletResponse response) {
        JSONObject param=ControlTool.GetRequestJSON(request,response);//获取页面JSON对象参数
        Map<String,Object> values=getConditionParam(param);//获取查询参数
         /*合并数据过滤条件*/
        if (setFindFilter(param)!=null)
            values.putAll(setFindFilter(param));

        SuperService bll=getService();//获取业务对象

        SearchViewParam p=ControlTool.GetViewParam(param);//获取查询参数

        ReturnBeanInPower rbi=new ReturnBeanInPower();
        rbi.setHasPower(setPowerFilter());
        if (bll==null)
        {
            rbi.setCode("000000");
            rbi.setMessage("系统没有提供业务对象");
        }
        if (values==null || values.size()==0)
        {
            rbi.setCode("10000");
            rbi.setMessage("无条件查询");
            rbi.setObj(getService().findByPage(values,p.pageNumber,p.rowsCount));
        }
        else
        {
            rbi.setCode("10001");
            rbi.setMessage("根据条件查询");
            rbi.setObj(getService().findByPage(values,p.pageNumber,p.rowsCount));
        }
        return rbi;
    }

    /*分页查找,为Layui服务*/
    @RequestMapping(value = "/findByPageForLayui", method = RequestMethod.POST)
    @ResponseBody
    public Object findByPageForLayui(HttpServletRequest request, HttpServletResponse response) {
        Map<String,String[]> valueTemp=request.getParameterMap();//获取传递的参数值,客户端传递参数格式如：?page=1&limit=2
        Map<String,Object> values=new HashMap<String, Object>();//过滤后的查询条件参数
        SearchViewParam p=new SearchViewParam();//创建分页参数

        /*过滤参数*/
        for (Map.Entry<String,String[]> entry : valueTemp.entrySet()) {
            if (entry.getKey().trim().equals("page"))
            {
                p.pageNumber=Integer.valueOf(entry.getValue()[0])-1;
            }else if(entry.getKey().trim().equals("limit"))
            {
                p.rowsCount=Integer.valueOf(entry.getValue()[0]) ;
            }
            else
              values.put(entry.getKey().trim(),entry.getValue()[0]);
        }
        /*查询参数与过滤参数合并*/
        if (setFindFilter()!=null)
        {
            values.putAll(setFindFilter());
        }
        SuperService bll=getService();//获取业务对象

        class LayuiTable{
            public String code="";
            public String msg="";
            public int count=0;
            public Object data=null;
           // public boolean[] hasPower=null;
        }
        LayuiTable rbi=new LayuiTable();
       //rbi.hasPower=setPowerFilter();
        if (bll==null)
        {
            rbi.code="000000";
            rbi.msg="系统没有提供业务对象";
        }
        if (values==null || values.size()==0)
        {
            rbi.code="10000";
            rbi.msg="无条件查询";
            Page data=getService().findByPage(values,p.pageNumber,p.rowsCount);
            rbi.data=data.getRows();
            rbi.count=data.getTotal();
        }
        else
        {
            rbi.code="10001";
            rbi.msg="条件查询";
            Page data=getService().findByPage(values,p.pageNumber,p.rowsCount);
            rbi.data=data.getRows();
            rbi.count=data.getTotal();
        }

        return rbi;
    }

    /*导出操作*/
    @RequestMapping(value = "/loadout", method = RequestMethod.POST)
    public void loadoutData(HttpServletRequest request, HttpServletResponse response) {

        try {
            request.setCharacterEncoding("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        JSONObject param=ControlTool.GetRequestJSON(request,response);//获取页面JSON对象参数
        Map<String,Object> values=getConditionParam(param);//获取查询参数
        SuperService bll=getService();//获取业务对象

        List<Map<String,Object>> lResult=bll.findForMap(values);
        if (lResult==null || lResult.size()==0) {
            response.setContentType("text/html");
            ControlTool.GetResonseOutObject(response).write("没有符合条件的角色类型数据导出");//获取服务器输出对象
             return;
        }

        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        String fileName =getService().getLoadoutExcelFileName();//获取导出文件名称
        try {
            String agent = (String)request.getHeader("USER-AGENT");//获取浏览器名称
            if(agent != null && agent.toLowerCase().indexOf("firefox") > 0)
            {
                response.setHeader("Content-Disposition", "attachment; filename="+new String(fileName.getBytes("GB2312"),"ISO-8859-1") + ".xls");
             }
            else
            {
                response.setHeader("Content-Disposition", "attachment; filename=" + java.net.URLEncoder.encode(fileName, "UTF-8")+".xls");
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            fileName ="temp";
        }
        OutputStream fOut = null;

        // 产生工作簿对象
        HSSFWorkbook workbook = new HSSFWorkbook();
        //产生工作表对象
        HSSFSheet sheet = workbook.createSheet();
        HSSFRow titleRow = sheet.createRow(0);//创建一行
        /*设置列信息*/
        List<Map<String,Object>> cols=getService().getLoadoutExcelColumns();//获取列信息
        if (cols!=null && cols.size()>0)
            for(int i=0;i<=cols.size();i++)
            {
                if (i==0)
                    titleRow.createCell(i).setCellValue("序号");//自动创建序号列
                else
                    titleRow.createCell(i).setCellValue((String)kesun.util.Tool.GetMapFirstValueName(cols.get(i-1)));
            }
         else
        {
            response.setContentType("text/html");
            ControlTool.GetResonseOutObject(response).write("系统没有设置导出的列名称");//获取服务器输出对象
            return;
        }
          /*写入数据*/
        for (int i = 1; i <= lResult.size(); i++)
        {
            HSSFRow row = sheet.createRow((int)i);//创建一行
            row.createCell(0).setCellValue(i);//填充序号值
            for(int j=0;j<cols.size();j++)
            {
                row.createCell(j+1).setCellValue(lResult.get(i-1).get(kesun.util.Tool.GetMapFirstKeyName(cols.get(j))).toString());
            }
        }
        try {
            fOut = response.getOutputStream();
            workbook.write(fOut);
            fOut.flush();
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Object print(HttpServletRequest request, HttpServletResponse response) {
       return  null;
    }

    public void printDataTable(HttpServletRequest request, HttpServletResponse response) {

    }

    public void display(HttpServletRequest request, HttpServletResponse response) {

    }
    @RequestMapping(value = "/getMe", method = RequestMethod.POST)
    @ResponseBody
    public Object getMe(HttpServletRequest request, HttpServletResponse response) {
        AbsSuperObject model=updateModel(initParameter(request,response));//获取客户端参数信息
        Object temp=getService().getMe();
        if (temp!=null && temp instanceof AbsSuperObject)
        {
            model=(AbsSuperObject)temp;
            model.setOldId(model.getId());
        }
        return getReturnObject("00000","对象获取操作",model);
    }

    public static User GetCurrentUser()
    {
        /*此处写入用户操作信息，组织结构信息、角色信息*/
        Object worker= TokenManage.getSession().getAttribute("user");
        if (worker!=null && worker instanceof User)
        {
            User user=(User) worker;
            return user;
        }
        else
            return null;
    }
}
