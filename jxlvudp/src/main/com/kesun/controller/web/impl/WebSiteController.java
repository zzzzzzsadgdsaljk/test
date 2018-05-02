package kesun.controller.web.impl;


import kesun.bll.SuperService;
import kesun.bll.web.impl.WebSiteServiceImpl;
import kesun.controller.SuperController;
import kesun.util.JSONAndObject;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import shiro.TokenManage;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wph-pc on 2017/5/30.
 */
@Controller
@RequestMapping("webSite")
public class WebSiteController extends SuperController implements kesun.controller.web.IWebSite {

    @Resource(name="bWebSite")
    private WebSiteServiceImpl bll;
    public SuperService getService() {
        return bll;
    }

    /*重写权限过滤*/
    @Override
    public boolean[] setPowerFilter()
    {
        String[] lPowers=new String[3];//权限组
        lPowers[0]="/webSite/getMe";
        lPowers[1]="/webSite/edit";
        lPowers[2]="/webSite/del";
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

    @RequestMapping("/index")
    public String index(){
        return "web/website/websiteManage";
    }
}
