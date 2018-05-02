package kesun.controller.web.impl;

import com.alibaba.fastjson.JSON;

import kesun.bll.web.impl.LeaveMessageServiceImpl;
import kesun.controller.ControlTool;
import kesun.entity.SearchViewParam;
import kesun.entity.system.UserOnlineRecord;
import kesun.entity.web.LeaveMessage;
import kesun.util.JSONAndObject;
import kesun.util.Tool;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by wph-pc on 2017/5/30.
 */
@Controller
@RequestMapping("leaveMessage")
public class LeaveMessageController implements kesun.controller.web.ILeaveMessage {
    @Resource(name="bLeaveMessage")
    private LeaveMessageServiceImpl bll;
    private LeaveMessage model=new LeaveMessage();
    private PrintWriter out =null;
    private final Log logger = LogFactory.getLog(LeaveMessageController.class);

    @RequestMapping("index")
    public String index()
    {
        return "web/leaveMessage/leaveMessageManage";
    }
    @RequestMapping("writeMsg")
    public String writeMsg()
    {
        return "web/leaveMessage/leaveMessage";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public void add(HttpServletRequest request, HttpServletResponse response) {
        JSONObject param= ControlTool.GetRequestJSON(request,response);//获取页面JSON对象参数
        model= JSONAndObject.JSONObjectToJavaBean(param, LeaveMessage.class);
        model.setId(Tool.CreateID());
        model.setStatus("正常");
        model.setCreateDate(new Date());
        model.setLeaveDate(new Date());
        bll.setModel(model);

        out=ControlTool.GetResonseOutObject(response);//获取服务器输出对象
        out.write("{\"result\":"+bll.add()+"}");
    }
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public void edit(HttpServletRequest request, HttpServletResponse response) {
        JSONObject param= ControlTool.GetRequestJSON(request,response);//获取页面JSON对象参数
        model= JSONAndObject.JSONObjectToJavaBean(param, LeaveMessage.class);
        model.setStatus("正常");
        out=ControlTool.GetResonseOutObject(response);//获取服务器输出对象
        //model.setLeaveDate(new java.util.Date());
        bll.setModel(model);
        Object obj= bll.getMe();//获取对象
        if (obj==null || request.getSession().getAttribute("user")==null)
        {
            out.write("{\"result\":0}");
            return;
        }
        LeaveMessage temp=(LeaveMessage) obj;//对象转换
        ((LeaveMessage) obj).setResponse(param.getString("response"));
        /*处理回复的操作者*/
        UserOnlineRecord online=(UserOnlineRecord)request.getSession().getAttribute("user");
        temp.setWorker(online.getLoginUser());
        temp.setResponseDate(new Date());
        bll.setModel(temp);
        out.write("{\"result\":"+bll.edit()+"}");
    }
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public void del(HttpServletRequest request, HttpServletResponse response) {
        JSONObject param=ControlTool.GetRequestJSON(request,response);//获取页面JSON对象参数
        model=JSONAndObject.JSONObjectToJavaBean(param, LeaveMessage.class);
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
    @RequestMapping(value = "/find", method = RequestMethod.POST)
    public void find(HttpServletRequest request, HttpServletResponse response) {
        JSONObject param=ControlTool.GetRequestJSON(request,response);//获取页面JSON对象参数

        Map<String,Object> values = new java.util.HashMap<String,Object>();//查询条件存放处

        String name=JSONAndObject.GetJsonStringValue(param, "condition");
		/*查询参数定义*/
        values.put("name",name);

        //将查询结果以JSON格式字符串返回
        String  temp= JSON.toJSONString(bll.find(values));

        out=ControlTool.GetResonseOutObject(response);//获取服务器输出对象
        out.write(temp);
    }
    @RequestMapping(value = "/findByPage", method = RequestMethod.POST)
    public void findByPage(HttpServletRequest request, HttpServletResponse response) {
        JSONObject param=ControlTool.GetRequestJSON(request,response);//获取页面JSON对象参数

        Map<String,Object> values = new java.util.HashMap<String,Object>();//查询条件存放处

        SearchViewParam p=ControlTool.GetViewParam(param);//获取查询参数
		/*查询参数定义*/
        values.put("name",p.condition);
        //将查询结果以JSON格式字符串返回
        String  temp= JSON.toJSONString(bll.findByPage(values, p.pageNumber, p.rowsCount));

        out=ControlTool.GetResonseOutObject(response);//获取服务器输出对象
        out.write(temp);
    }

    public void loadoutData(HttpServletRequest request, HttpServletResponse response) {

    }

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

    public void response(HttpServletRequest request, HttpServletResponse response) {

    }

    public void findResponseMSG(HttpServletRequest request, HttpServletResponse response) {

    }
}
