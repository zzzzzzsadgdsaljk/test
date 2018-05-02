package kesun.controller.system.impl;

import com.alibaba.fastjson.JSON;
import kesun.bll.system.impl.UserOnlineRecordServiceImpl;
import kesun.bll.system.impl.UserServiceImpl;
import kesun.controller.ControlTool;
import kesun.controller.ReturnBean;
import kesun.controller.system.IUserController;
import kesun.dao.DaoSupport;
import kesun.entity.SearchViewParam;
import kesun.entity.system.Actor;
import kesun.entity.system.User;
import kesun.entity.system.UserOnlineRecord;
import kesun.util.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.VCache;
import shiro.ShiroMD5;
import shiro.TokenManage;
import shiro.session.SessionManager;
import shiro.session.SessionStatus;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.*;

import static org.json.XMLTokener.entity;

/**
 * 用户控制层
 * Created by wph-pc on 2017/5/29.
 */
@Controller
@RequestMapping("user")
public class UserController implements IUserController {
    @Resource(name="bUser")
    private UserServiceImpl bll;
    @Resource(name="bUserOnlineRecord")
    private UserOnlineRecordServiceImpl onlineBll;
    private User model=new User();
    private PrintWriter out =null;
    private final Log logger = LogFactory.getLog(UserController.class);
    @RequestMapping("index")
    public String index()
    {
        return "system/user/UserManage";
    }
    @RequestMapping("userOnline")
    public String userOnline()
    {
        return "system/user/userWatchOnline";
    }
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public void add(HttpServletRequest request, HttpServletResponse response) {
        JSONObject param= ControlTool.GetRequestJSON(request,response);//获取页面JSON对象参数
        model=JSONAndObject.JSONObjectToJavaBean(param, User.class);
        model.setId(model.getNumber());
        model.setCreateDate(new Date());
        model.setPassword(ShiroMD5.GetPwd(model.getNumber(),model.getNumber()));//MD5.GetPwd(model.getNumber())
        model.setStatus("正常");
        bll.setModel(model);

        out= ControlTool.GetResonseOutObject(response);//获取服务器输出对象
        /*判断当前用户是否存在，如果存在则返回*/
        if (bll.getMe()!=null)
        {
            out.write("{\"result\":10000}");
        }
        else
        out.write("{\"result\":"+bll.add()+"}");
    }
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public void edit(HttpServletRequest request, HttpServletResponse response) {
        JSONObject param=ControlTool.GetRequestJSON(request,response);//获取页面JSON对象参数
        model= JSONAndObject.JSONObjectToJavaBean(param, User.class);
        model.setCreateDate(new Date());
        model.setStatus("正常");
        bll.setModel(model);

        out=ControlTool.GetResonseOutObject(response);//获取服务器输出对象
        out.write("{\"result\":"+bll.edit()+"}");
    }

    @RequestMapping(value = "/testDB", method = RequestMethod.POST)
    @ResponseBody
    public Object testDB(HttpServletRequest request, HttpServletResponse response) {
        if (((DaoSupport)bll.getDAO()).getSqlSessionTemplate().getConnection()==null)
            return false;
        else
            return true;
    }
//
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public void del(HttpServletRequest request, HttpServletResponse response) {
        JSONObject param=ControlTool.GetRequestJSON(request,response);//获取页面JSON对象参数
        model= JSONAndObject.JSONObjectToJavaBean(param, User.class);
        bll.setModel(model);

        out=ControlTool.GetResonseOutObject(response);//获取服务器输出对象
        out.write("{\"result\":"+bll.del()+"}");
    }
    @RequestMapping(value = "/deleteAll", method = RequestMethod.POST)
    public void deleteAll(HttpServletRequest request, HttpServletResponse response) {
        JSONObject param=ControlTool.GetRequestJSON(request,response);//获取页面JSON对象参数
        String ids=JSONAndObject.GetJsonStringValue(param, "ids");

        List<String> cons=new ArrayList<String>();
        for(String id:ids.split(","))
            cons.add(id);

        out=ControlTool.GetResonseOutObject(response);//获取服务器输出对象
        out.write("{\"result\":"+bll.deleteAll(cons)+"}");
    }

    public void find(HttpServletRequest request, HttpServletResponse response) {

    }
    /*获取当前登录用户*/
    @RequestMapping(value = "/loginUser", method = RequestMethod.POST)
    @ResponseBody
    public Object getCurrentUser()
    {
        return TokenManage.getToken();
    }
  //查询所有用户信息
  @RequestMapping(value = "/findByPage", method = RequestMethod.POST)
    public void findByPage(HttpServletRequest request, HttpServletResponse response) {

        JSONObject param=ControlTool.GetRequestJSON(request,response);//获取页面JSON对象参数

        Map<String,Object> values = new HashMap<String,Object>();//查询条件存放处

        SearchViewParam p=ControlTool.GetViewParam(param);//获取查询参数
		/*查询参数定义*/
        values.put("name",p.condition);
        values.put("number",p.condition);
        //将查询结果以JSON格式字符串返回
        String  temp= JSON.toJSONString(bll.findByPage(values, p.pageNumber, p.rowsCount));
        out=ControlTool.GetResonseOutObject(response);//获取服务器输出对象
        out.write(temp);
    }
   //导出数据
    @RequestMapping(value = "/loadout", method = RequestMethod.POST)
    public void loadoutData(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Map<String,Object> values = new HashMap<String,Object>();//查询条件存放处

        System.out.println("导出参数："+request.getParameter("condition"));
		/*查询参数定义*/
        values.put("name",request.getParameter("condition"));
        List<Map<String,Object>> lResult=bll.findForMap(values);
        if (lResult==null || lResult.size()==0) {
            response.setContentType("text/html");

            out=ControlTool.GetResonseOutObject(response);//获取服务器输出对象
            out.write("没有符合条件的用户数据导出");

            return;
        }
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        String fileName ="系统用户基本信息";
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
        titleRow.createCell(0).setCellValue("序号");
        titleRow.createCell(1).setCellValue("账号");
        titleRow.createCell(2).setCellValue("创建日期");
        titleRow.createCell(3).setCellValue("状态");

        for (int i = 1; i <= lResult.size(); i++)
        {
            HSSFRow row = sheet.createRow((int)i);//创建一行

            row.createCell(0).setCellValue(i);
            row.createCell(1).setCellValue(lResult.get(i-1).get("number").toString());
            row.createCell(2).setCellValue(lResult.get(i-1).get("createDate").toString());
            row.createCell(3).setCellValue(lResult.get(i-1).get("status").toString());
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
  // 打印
    public void print(HttpServletRequest request, HttpServletResponse response) {

    }

    public void printDataTable(HttpServletRequest request, HttpServletResponse response) {

    }

    public void display(HttpServletRequest request, HttpServletResponse response) {

    }

    @RequestMapping(value = "/getMe", method = RequestMethod.POST)
    public void getMe(HttpServletRequest request, HttpServletResponse response) {
        JSONObject param=ControlTool.GetRequestJSON(request,response);//获取页面JSON对象参数
        //获取页面传递的参数ID
        String id=JSONAndObject.GetJsonStringValue(param, "id");
        model.setId(id);
        bll.setModel(model);
        out=ControlTool.GetResonseOutObject(response);//获取服务器输出对象
        out.write(JSON.toJSONString(bll.getMe()));
    }
    /*根据用户请求，获取当前用户在线状态记录*/
    private UserOnlineRecord getUserOnlineRecord(HttpServletRequest request,User u,String token_key,String type)
    {
        UserOnlineRecord temp=new UserOnlineRecord();
        temp.setId(Tool.CreateID());
        temp.setName("用户登录");
        temp.setLoginDate(new Date(request.getSession().getCreationTime()));
        temp.setIpAddress(request.getRemoteAddr());
        temp.setSessionID(token_key);
        temp.setLoginUser(u);
        temp.setType(type);
        temp.setStatus("正常");
        return temp;
    }
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Object login(HttpServletRequest request, HttpServletResponse response) {

        JSONObject param= ControlTool.GetRequestJSON(request,response);//获取页面JSON对象参数
        model= JSONAndObject.JSONObjectToJavaBean(param, User.class);
        model.setNumber(model.getId());
        ReturnBean back=new ReturnBean();
         /*判断数据库连接是否成功*/

        try {

            model = TokenManage.login(model,true);
            back.setCode("200");
            back.setMessage("登录成功");
            back.setObj(model);


            /**
             * shiro 获取登录之前的地址
             */
            SavedRequest savedRequest = WebUtils.getSavedRequest(request);
            String url = null ;
            if(null != savedRequest){
                url = savedRequest.getRequestUrl();
            }

            LoggerUtils.fmtDebug(getClass(), "获取登录之前的URL:[%s]",url);
            //如果登录之前没有地址，那么就跳转到首页。
            if(StringUtils.isBlank(url)){
                url = request.getContextPath() + "/index";
            }
             return back;
        } catch (DisabledAccountException e) {
            back.setCode("500");
            back.setMessage("帐号已经禁用");
        }catch (AccountException ae)
        {
            back.setCode("600");
            back.setMessage("帐号或密码错误");
        }
        catch (Exception e) {
            back.setCode("700");
            back.setMessage("验证异常，异常信息："+e.toString());
        }
        return back;
    }
    @RequestMapping(value = "/login_ByMobile", method = RequestMethod.POST)
    public void login_ByMobile(HttpServletRequest request, HttpServletResponse response) {
        JSONObject param=ControlTool.GetRequestJSON(request,response);//获取页面JSON对象参数
        model=JSONAndObject.JSONObjectToJavaBean(param, User.class);
        bll.setModel(model);
        out=ControlTool.GetResonseOutObject(response);//获取服务器输出对象
        out.write("{\"result\":\""+ MD5.GetPwd(Tool.CreateID())+"\"}");

    }
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        TokenManage.logout();
    }
    @RequestMapping(value = "/changePwd", method = RequestMethod.POST)
    public void changePwd(HttpServletRequest request, HttpServletResponse response) {
        JSONObject param= ControlTool.GetRequestJSON(request,response);//获取页面JSON对象参数
        model= JSONAndObject.JSONObjectToJavaBean(param, User.class);//获取用户信息
        String newPwd=param.getString("newPwd");//获取新密码
        out=ControlTool.GetResonseOutObject(response);//获取服务器输出对象
        /*提取当前用户信息*/
        User tempUser=TokenManage.getToken(); //获取当前用户对象
        if(tempUser==null)
        {
            out.write("{\"result\":-100}");//当前用户没有登录
            return;
        }

        /*比对提供原密码是否正确*/
        if (tempUser.getPassword().equals(ShiroMD5.GetPwd(tempUser.getNumber(),model.getPassword()))==false) //MD5.GetPwd(model.getPassword())
        {
            out.write("{\"result\":-200}");//输入的原密码错误
            return;
        }

        bll.setModel(tempUser);
        int code=bll.changePwd(ShiroMD5.GetPwd(tempUser.getNumber(),newPwd));//MD5.GetPwd(newPwd)修改密码
        out.write("{\"result\":"+code+"}");

    }
    @RequestMapping(value = "/initPassword", method = RequestMethod.POST)
    public void initPassword(HttpServletRequest request, HttpServletResponse response) {
        JSONObject param= ControlTool.GetRequestJSON(request,response);//获取页面JSON对象参数
        String[] ids=param.getString("ids").split(",");
        out=ControlTool.GetResonseOutObject(response);//获取服务器输出对象

        if (ids==null || ids.length==0)
        {
            out.write("{\"result\":0}");
            return;
        }

        List<User> lUsers=new ArrayList<User>();
        for (String id:ids
             ) {
            User temp=new User();
            temp.setId(id);
            lUsers.add(temp);
        }
        out.write("{\"result\":"+bll.initPassword(lUsers)+"}");
    }

    public void writeLog(HttpServletRequest request, HttpServletResponse response) {

    }
    @RequestMapping(value = "/setUserState", method = RequestMethod.POST)
    public void manageUserState(HttpServletRequest request, HttpServletResponse response) {
        JSONObject param= ControlTool.GetRequestJSON(request,response);//获取页面JSON对象参数
        model= JSONAndObject.JSONObjectToJavaBean(param, User.class);//获取用户信息
        String newStatus=param.getString("newStatus");//获取新密码
        out=ControlTool.GetResonseOutObject(response);//获取服务器输出对象
        bll.setModel(model);
        out.write("{\"result\":"+bll.manageUserState(newStatus)+"}");
    }
    @RequestMapping(value = "/setActor", method = RequestMethod.POST)
    public void setActor(HttpServletRequest request, HttpServletResponse response) {
        JSONObject param= ControlTool.GetRequestJSON(request,response);//获取页面JSON对象参数
        out=ControlTool.GetResonseOutObject(response);//获取服务器输出对象
        model=new User();
        String users=param.getString("users");//获取用户的id，如果存在多个，中间用逗号隔开

        org.json.JSONArray temp= param.getJSONArray("actors");
        List<Actor> lActors=null;
        if (temp!=null && temp.length()>0)
        {
            lActors=new ArrayList<Actor>();
            for(int i=0;i<temp.length();i++)
            {
                lActors.add(JSONAndObject.JSONObjectToJavaBean(temp.getJSONObject(i), Actor.class));
            }
        }
        /*处理多个用户的问题*/
        String[] userIDs=users.split(",");
        int result=-100;//操作结果

        for (String id:userIDs
             ) {
            model.setId(id);
            bll.setModel(model);
            result=bll.setActor(lActors);
            if (result<=0) break;
        }


       out.write("{\"result\":"+result+"}");


    }
    /*获取指定用户的角色分配情况，并以JSON格式返回已授权的角色对象*/
    @RequestMapping(value = "/findActor", method = RequestMethod.POST)
    public void findActor(HttpServletRequest request, HttpServletResponse response) {
        JSONObject param= ControlTool.GetRequestJSON(request,response);//获取页面JSON对象参数
        model= JSONAndObject.JSONObjectToJavaBean(param, User.class);
        out=ControlTool.GetResonseOutObject(response);//获取服务器输出对象

        if (model==null || model.getId().equals(""))
        {
            out.write("{}");
            return;
        }
        bll.setModel(model);
        out.write(JSON.toJSONString(bll.findActor()));
    }

    class WatchData
    {
        public int page=0;
        public int total=0;
        public List<UserOnlineRecord> rows=null;
    }

    /*获取当前用户在线信息*/
    private List<UserOnlineRecord> getOnlineData(HttpServletRequest request)
    {
        SessionManager sessionManager=(SessionManager) SpringContextUtil.getBean("customSessionManager");
        List<UserOnlineRecord> lData=sessionManager.getAllUser();

        if (lData.size()==0)
            return null;
        else
            return lData;
    }
    static VCache cache;
    /*剔除指定用户在线，1表示剔除成功，0表示没有该用户,-1表示没有session对象*/
    @RequestMapping(value = "/removeUser", method = RequestMethod.POST)
    @ResponseBody
    public Object removeSession(HttpServletRequest request, HttpServletResponse response)
    {
        JSONObject param= ControlTool.GetRequestJSON(request,response);//获取页面JSON对象参数
        String sessionID=param.getString("id");//获取当前下线的SessionID
        String userId=param.getString("userId");

        SessionManager sessionManager=(SessionManager)SpringContextUtil.getBean("customSessionManager");
        ReturnBean returnBean=new ReturnBean();//返回对象
        if (sessionID!=null && sessionID.trim()!="")
        {
            returnBean.setCode(String.valueOf(sessionManager.changeSessionStatus(SessionStatus.KILLED,sessionID)));
            returnBean.setMessage("您选择的用户已被强制下线！");
            returnBean.setObj(null);
        }
        else
        {
            returnBean.setCode("0");
            returnBean.setMessage("系统没有获取到当前用户的会话ID！");
            returnBean.setObj(null);
        }
        return returnBean;
    }

    @RequestMapping(value = "/watchOnline", method = RequestMethod.POST)
    public void watchOnline(HttpServletRequest request, HttpServletResponse response) {
        JSONObject param=ControlTool.GetRequestJSON(request,response);//获取页面JSON对象参数
        SearchViewParam p=ControlTool.GetViewParam(param);//获取查询参数
        out=ControlTool.GetResonseOutObject(response);//获取服务器输出对象
        List<UserOnlineRecord> lOnline= getOnlineData(request);//获取现在数据

        String userName=p.condition;//用户名
        /*开始过滤*/
        List<UserOnlineRecord> temp=new ArrayList<UserOnlineRecord>();
        if (lOnline!=null && lOnline.size()>0)
        for (UserOnlineRecord obj:lOnline
             ) {
            if (obj.getLoginUser().getNumber().indexOf(userName)>=0 || obj.getLoginUser().getNickName().indexOf(userName)>=0)
                temp.add(obj);
        }

        lOnline=temp;
        WatchData data=new WatchData();
        data.total=lOnline==null?0:lOnline.size();
        data.page=p.pageNumber;

        if (lOnline!=null && lOnline.size()>0)
        {
            if (lOnline.size()<p.pageNumber*p.rowsCount+p.rowsCount)
                data.rows=lOnline.subList(p.pageNumber*p.rowsCount,lOnline.size());
            else
                data.rows=lOnline.subList(p.pageNumber*p.rowsCount,p.pageNumber*p.rowsCount+p.rowsCount);

        }
        else
          data.rows=new ArrayList();
        out.write(JSON.toJSONString(data));
    }
}
