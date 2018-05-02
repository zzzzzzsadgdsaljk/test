package kesun.controller.system.impl;


import kesun.bll.SuperService;
import kesun.bll.system.impl.ActorTypeServiceImpl;
import kesun.controller.SuperController;
import kesun.controller.system.IActorType;
import kesun.entity.AbsSuperObject;
import kesun.entity.system.ActorType;
import kesun.util.JSONAndObject;
import kesun.util.Tool;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import shiro.TokenManage;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wph-pc on 2017/5/26.
 */
@Controller
@RequestMapping("/actorType")
public class ActorTypeController extends SuperController implements IActorType {
    @Resource(name="bActorType")
    private ActorTypeServiceImpl bll;

    public SuperService getService() {
        return bll;
    }
    /*重写权限过滤*/
    @Override
    public boolean[] setPowerFilter()
    {
        String[] lPowers=new String[3];//权限组
        lPowers[0]="/actorType/getMe";
        lPowers[1]="/actorType/edit";
        lPowers[2]="/actorType/del";
        return TokenManage.IsHasPowers(lPowers);
    }
    /*重写父类方法*/
    public AbsSuperObject updateModel(AbsSuperObject model)
    {
        if (model==null)
        {
            model=new ActorType();
            model.setId(Tool.CreateID());
        }
        if (model.getId().trim().equals(""))
            model.setId(Tool.CreateID());
        return model;
    }
    public Map<String, Object> getConditionParam(JSONObject param) {
        if (param==null) return  null;//判断条件是否为空param是页面传递的值
        Map<String,Object> values=new HashMap<String, Object>();
        if (JSONAndObject.GetJsonStringValue(param,"condition")!=null)
        {
            values.put("name",JSONAndObject.GetJsonStringValue(param,"condition"));
            values.put("id",JSONAndObject.GetJsonStringValue(param,"condition"));
        }
        return values;
    }

    public Map<String, Object> setFindFilter(JSONObject param) {
        return null;
    }

    @RequestMapping("/actorType")
    public String index(){
        return "system/actorType/actorTypeManage";
    }
}
