package kesun.controller.system.impl;

import com.alibaba.fastjson.JSON;

import kesun.bll.SuperService;
import kesun.bll.system.impl.OrganizationServiceImpl;
import kesun.controller.SuperController;
import kesun.controller.ControlTool;
import kesun.controller.system.IOrganization;
import kesun.entity.AbsSuperObject;
import kesun.entity.system.Organization;
import kesun.util.JSONAndObject;
import kesun.util.Tool;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wph-pc on 2017/9/26.
 */
@Controller
@RequestMapping("org")
public class OrganizationController extends SuperController implements IOrganization {
    @Resource(name="bOrg")
    private OrganizationServiceImpl bll;
    @RequestMapping(value ="updateParent",method = RequestMethod.POST)
    @ResponseBody
    public Object updateParent(Organization node) {
        if (node==null) return null;
        bll.setModel(node);
        return bll.updateParent();
    }
    @RequestMapping(value ="index")
    public String index()
    {
        return "system/organization/organizationManage";
    }
    public SuperService getService() {
        return bll;
    }
    /*重写父类方法*/
    public AbsSuperObject updateModel(AbsSuperObject model)
    {
        if (model==null)
        {
            model=new Organization();
            model.setId(Tool.CreateID());
        }
        if (model.getId().trim().equals(""))
            model.setId(Tool.CreateID());
        return model;
    }
    public Map<String, Object> getConditionParam(JSONObject param) {
        if (param==null) return  null;//判断条件是否为空param是页面传递的值

        Map<String,Object> values=new HashMap<String, Object>();
        if (JSONAndObject.GetJsonStringValue(param,"id")!=null)
            values.put("id", JSONAndObject.GetJsonStringValue(param,"id"));

        if (JSONAndObject.GetJsonStringValue(param,"condition")!=null)
            values.put("name",JSONAndObject.GetJsonStringValue(param,"condition"));

        if (JSONAndObject.GetJsonStringValue(param,"parent")!=null)
            values.put("parent",JSONAndObject.GetJsonStringValue(param,"parent"));
        return values;
    }

    @RequestMapping(value = "/findTree", method = RequestMethod.POST)
    @ResponseBody
    public Object findTree(HttpServletRequest request, HttpServletResponse response) {
        JSONObject param= ControlTool.GetRequestJSON(request,response);//获取页面JSON对象参数
        Map<String,Object> values=getConditionParam(param);//获取查询参数
        List<Object> lTemp=(List<Object>)bll.find(values);

        Organization model=(Organization)bll.getModel();
        //将查询结果以JSON格式字符串返回
        return JSON.toJSONString(model.convertTreeList(lTemp));
    }

    public Map<String, Object> setFindFilter(JSONObject param) {
        return null;
    }
}
