package kesun.controller.web.impl;


import kesun.bll.SuperService;
import kesun.bll.web.impl.AdvertisementServiceImpl;
import kesun.controller.BusinessController;
import kesun.util.JSONAndObject;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wph-pc on 2017/5/30.
 */
@Controller
@RequestMapping("advertisement")
public class AdvertisementController extends BusinessController implements kesun.controller.web.IAdvertisement {
    @Resource(name="bAdvertisement")
    private AdvertisementServiceImpl bll;

    @RequestMapping("index")
    public String index()
    {
        return "web/advertisement/advertisementManage";
    }


    public SuperService getService() {
        return bll;
    }

    public Map<String, Object> getConditionParam(JSONObject param) {
        if (param==null) return  null;//判断条件是否为空param是页面传递的值
        Map<String,Object> values=new HashMap<String, Object>();
        if (JSONAndObject.GetJsonStringValue(param,"condition")!=null)
        {
            values.put("name", JSONAndObject.GetJsonStringValue(param,"condition"));
            values.put("id",JSONAndObject.GetJsonStringValue(param,"condition"));
        }
        return values;
    }

    public Map<String, Object> setFindFilter(JSONObject param) {
        return null;
    }
}
