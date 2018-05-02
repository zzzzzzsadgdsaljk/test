package kesun.controller.system.impl;

import com.alibaba.fastjson.JSON;
import kesun.bll.SuperService;
import kesun.bll.system.impl.ActorServiceImpl;
import kesun.controller.SuperController;
import kesun.controller.ControlTool;
import kesun.controller.ReturnBean;
import kesun.controller.system.IActor;
import kesun.entity.AbsSuperObject;
import kesun.entity.system.Actor;
import kesun.entity.system.SystemMenu;
import kesun.util.JSONAndObject;
import kesun.util.SpringContextUtil;
import kesun.util.Tool;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import shiro.HandleRealm;
import shiro.ShiroManage;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wph-pc on 2017/5/29.
 */
@Controller
@RequestMapping("actor")
public class ActorController extends SuperController implements IActor {
    @Resource(name="bActor")
    private ActorServiceImpl bll;

    public SuperService getService() {
        return bll;
    }

    /*重写父类方法*/
    public AbsSuperObject updateModel(AbsSuperObject model)
    {
        if (model==null)
        {
            model=new Actor();
            model.setId(Tool.CreateID());
        }
        if (model.getId().trim().equals(""))
            model.setId(Tool.CreateID());
        return model;
    }
    /*提供根据角色编号、名称、父级目录和分类名称进行查询*/
    public Map<String, Object> getConditionParam(JSONObject param) {
        if (param==null) return  null;//判断条件是否为空param是页面传递的值

        Map<String,Object> values=new HashMap<String, Object>();
        if (JSONAndObject.GetJsonStringValue(param,"id")!=null)
            values.put("id",JSONAndObject.GetJsonStringValue(param,"id"));
        if (JSONAndObject.GetJsonStringValue(param,"ids")!=null && !JSONAndObject.GetJsonStringValue(param,"ids").trim().equals(""))//批量id查询
        {
            String ids=JSONAndObject.GetJsonStringValue(param,"ids");
            List<String> temp=new ArrayList<String>();
            for (String str:ids.split(",")) {
                temp.add(str);
            }
            values.put("ids",temp);
        }
        else
            values.put("ids",null);
        if (JSONAndObject.GetJsonStringValue(param,"condition")!=null)
            values.put("name",JSONAndObject.GetJsonStringValue(param,"condition"));
        if (JSONAndObject.GetJsonStringValue(param,"type")!=null)
            values.put("type",JSONAndObject.GetJsonStringValue(param,"type"));
        if (JSONAndObject.GetJsonStringValue(param,"parent")!=null)
            values.put("parent",JSONAndObject.GetJsonStringValue(param,"parent"));
        return values;
    }

    public Map<String, Object> setFindFilter(JSONObject param) {
        return null;
    }

    @RequestMapping(value = "/findTree", method = RequestMethod.POST)
    @ResponseBody
    public Object findTree(HttpServletRequest request, HttpServletResponse response) {
        JSONObject param= ControlTool.GetRequestJSON(request,response);//获取页面JSON对象参数
        Map<String,Object> values=getConditionParam(param);//获取查询参数
        List<Object> lTemp=(List<Object>)bll.find(values);

        Actor model=new Actor();
        //将查询结果以JSON格式字符串返回
        return JSON.toJSONString(model.convertTreeList(lTemp));
    }
    /*根据角色ID创建角色对象组*/
    private List<Actor> getListInActor(List<String> source)
    {
        List<Actor> lActors=new ArrayList<Actor>();//存放角色对象
        /*设置角色对象*/
        if (source!=null && source.size()>0)
            for (String id:source) {
                Actor temp=new Actor();
                temp.setId(id);
                lActors.add(temp);
            }
            else
                return null;
        return lActors;
    }

    /*角色授权操作*/
    @RequestMapping(value = "/setPower", method = RequestMethod.POST)
    @ResponseBody
    public Object setPower(@RequestBody String data) {
        String actors=JSONAndObject.GetStringFromJSON(data,"actors");//才JSON字符串获取JSON对象
        String power=JSONAndObject.GetStringFromJSON(data,"power");
        List<Actor> lActors=getListInActor(Tool.StringArrayToList(actors.split(",")));
        List<SystemMenu> lMenus=new ArrayList<SystemMenu>();//存放权限对象

        List<String> powers=Tool.StringArrayToList(power.split(","));;
        /*设置权限对象*/
        if (powers!=null && powers.size()>0)
        for (String id:powers) {
            if(id.trim().equals("")) continue;//跳过空权限
            SystemMenu temp=new SystemMenu();
            temp.setId(id);
            lMenus.add(temp);
        }

        int result=bll.setFunctionPower(lActors,lMenus);//授权分配
        /*重载授权信息*/
        ShiroManage sm=(ShiroManage) SpringContextUtil.getBean("shiroManager");//获取Shiro管理对象
        sm.reCreateFilterChains();//调用权限重载对象

        resetUserPowerById();//权限重新加载

        ReturnBean rb=new ReturnBean();
        rb.setCode(String.valueOf(result));
        rb.setMessage("角色授权操作");
        rb.setObj(null);
        return JSON.toJSONString(rb);
    }
    /*获取角色权限*/
    @RequestMapping(value = "/getPower", method = RequestMethod.POST)
    @ResponseBody
    public Object getPower(@RequestBody String source) {
        String actors=JSONAndObject.GetStringFromJSON(source,"actors");//才JSON字符串获取JSON对象
        List<Actor> lActors=getListInActor(Tool.StringArrayToList(actors.split(",")));
        ReturnBean rb=new ReturnBean();
        rb.setCode("00000");
        rb.setMessage("角色授权操作");
        rb.setObj(bll.getFunctionPower(lActors));
        return JSON.toJSONString(rb);
    }

    private void resetUserPowerById()
    {
        Subject subject = SecurityUtils.getSubject();

        String realmName = subject.getPrincipals().getRealmNames().iterator().next();

       //第一个参数为用户名,第二个参数为realmName,test想要操作权限的用户
        SimplePrincipalCollection principals = new SimplePrincipalCollection(subject.getPrincipal(),realmName);

        subject.runAs(principals);
        HandleRealm shiroRealm=(HandleRealm)SpringContextUtil.getBean("shiroRealm");
        shiroRealm.getAuthorizationCache().remove(subject.getPrincipals());
        subject.releaseRunAs();
    }
    @RequestMapping("/index")
    public String index()
    {
        return "system/actor/actorManage";
    }
}
