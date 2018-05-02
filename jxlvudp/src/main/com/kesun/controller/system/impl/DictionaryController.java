package kesun.controller.system.impl;

import com.alibaba.fastjson.JSON;

import kesun.bll.SuperService;
import kesun.bll.system.impl.DictionaryServiceImpl;
import kesun.controller.SuperController;
import kesun.controller.ControlTool;
import kesun.entity.AbsSuperObject;
import kesun.entity.system.Dictionary;
import kesun.util.JSONAndObject;
import kesun.util.PropertyUtil;
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
@RequestMapping("dictionary")
public class DictionaryController extends SuperController implements kesun.controller.system.IDictionary {
    @Resource(name="bDictionary")
    private DictionaryServiceImpl bll;

    @RequestMapping(value ="index")
    public String index()
    {
        return "system/dictionary/dictionaryManage";
    }

    public SuperService getService() {
        return bll;
    }

    /*重写父类方法*/
    public AbsSuperObject updateModel(AbsSuperObject model)
    {
        if (model==null)
        {
            model=new Dictionary();
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

        if (JSONAndObject.GetJsonStringValue(param,"condition")!=null)
            values.put("name",JSONAndObject.GetJsonStringValue(param,"condition"));

        if (JSONAndObject.GetJsonStringValue(param,"key")!=null)
        {
            PropertyUtil res = new PropertyUtil("dictionary.properties");

            values.put("parent",res.getProperty(JSONAndObject.GetJsonStringValue(param,"key")));
        }
        if (JSONAndObject.GetJsonStringValue(param,"parent")!=null && !JSONAndObject.GetJsonStringValue(param,"parent").trim().equals(""))
            values.put("parent",JSONAndObject.GetJsonStringValue(param,"parent"));
        return values;
    }
    @RequestMapping(value = "/findTree", method = RequestMethod.POST)
    @ResponseBody
    public Object findTree(HttpServletRequest request, HttpServletResponse response) {
        JSONObject param= ControlTool.GetRequestJSON(request,response);//获取页面JSON对象参数
        Map<String,Object> values=getConditionParam(param);//获取查询参数
        List<Object> lTemp=(List<Object>)bll.find(values);

        Dictionary model=new Dictionary();
        //将查询结果以JSON格式字符串返回
        return JSON.toJSONString(model.convertTreeList(lTemp));
    }
    public Map<String, Object> setFindFilter(JSONObject param) {
        return null;
    }
    @RequestMapping(value ="updateParent",method = RequestMethod.POST)
    @ResponseBody
    public Object updateParent(Dictionary node) {
        if (node==null) return null;
        bll.setModel(node);
        return bll.updateParent();
    }
}
