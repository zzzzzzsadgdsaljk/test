package kesun.controller.web.impl;

import com.alibaba.fastjson.JSON;
import kesun.bll.SuperService;
import kesun.bll.web.impl.NewsCategoryServiceImpl;
import kesun.controller.SuperController;
import kesun.controller.ControlTool;
import kesun.entity.web.NewsCategory;
import kesun.util.JSONAndObject;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import shiro.TokenManage;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wph-pc on 2017/10/16.
 */
@Controller
@RequestMapping("newsCategory")
public class NewsCategoryController extends SuperController implements kesun.controller.web.INewsCategory {
    @Resource(name="bNewsCategory")
    private NewsCategoryServiceImpl bll;//业务层对象变量
    public SuperService getService() {
        return bll;
    }
    @Override
    public boolean[] setPowerFilter()
    {
        String[] lPowers=new String[3];//权限组
        lPowers[0]="/newsCategory/getMe";
        lPowers[1]="/newsCategory/edit";
        lPowers[2]="/newsCategory/del";
        return TokenManage.IsHasPowers(lPowers);
    }
    public Map<String, Object> getConditionParam(JSONObject param) {
        if (param==null) return  null;//判断条件是否为空param是页面传递的值
        Map<String,Object> values=new HashMap<String, Object>();
        if (JSONAndObject.GetJsonStringValue(param,"condition")!=null)
        {
            values.put("id", JSONAndObject.GetJsonStringValue(param,"condition"));
            values.put("name",JSONAndObject.GetJsonStringValue(param,"condition"));
        }
        return values;
    }

    public Map<String, Object> setFindFilter(JSONObject param) {
        return null;
    }
    @RequestMapping("index")
    public String index()
    {
        return "web/newscategory/newsCategoryManage";
    }

    @RequestMapping(value ="updateParent",method = RequestMethod.POST)
    @ResponseBody
    public Object updateParent(NewsCategory node) {
        if (node==null) return null;
        bll.setModel(node);
        return bll.updateParent();
    }

    @RequestMapping(value = "/findTree", method = RequestMethod.POST)
    @ResponseBody
    public Object findTree(HttpServletRequest request, HttpServletResponse response) {
        JSONObject param= ControlTool.GetRequestJSON(request,response);//获取页面JSON对象参数
        Map<String,Object> values=getConditionParam(param);//获取查询参数
        List<Object> lTemp=(List<Object>)bll.find(values);

        NewsCategory model=(NewsCategory)bll.getModel();
        //将查询结果以JSON格式字符串返回
        return JSON.toJSONString(model.convertTreeList(lTemp));
    }
}
