package kesun.controller.system.impl;

import kesun.bll.SuperService;
import kesun.bll.system.impl.ClerkServiceImpl;
import kesun.controller.BusinessController;
import kesun.controller.SuperController;
import kesun.controller.TopController;
import kesun.controller.system.IClerk;
import kesun.entity.system.Clerk;
import kesun.entity.system.User;
import kesun.util.JSONAndObject;
import kesun.util.SpringContextUtil;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wph-pc on 2017/10/28.
 */
@Controller
@RequestMapping("/clerk")
public class ClerkController extends SuperController implements IClerk {
    @Resource(name="bClerk")
    private ClerkServiceImpl bll;
    public SuperService getService() {
        return bll;
    }

    public Map<String, Object> getConditionParam(JSONObject param) {
        if (param==null) return  null;//判断条件是否为空param是页面传递的值
        Map<String,Object> values=new HashMap<String, Object>();
        if (JSONAndObject.GetJsonStringValue(param,"condition")!=null)
        {
            values.put("xm",JSONAndObject.GetJsonStringValue(param,"condition"));
            values.put("gh",JSONAndObject.GetJsonStringValue(param,"condition"));
            values.put("sfzjh",JSONAndObject.GetJsonStringValue(param,"condition"));
        }
        return values;
    }

    public Map<String, Object> setFindFilter(JSONObject param) {
        return null;
    }



    @RequestMapping("/findClerk")
    @ResponseBody
    public Clerk findClerk(){
        return FindClerk();
    }
    /*获取当前用户所属的大学*/
    public static Clerk FindClerk(){
        User temp= TopController.GetCurrentUser();
        if (temp!=null && temp.getOrg()!=null)
        {
            ClerkServiceImpl tempBll= (ClerkServiceImpl) SpringContextUtil.getBean("bClerk");
            return tempBll.findClerk(temp.getOrg());
        }
        else
            return null;
    }







    @RequestMapping("index")
    public String index(){
        return "system/clerk/clerkManage";
    }
}
