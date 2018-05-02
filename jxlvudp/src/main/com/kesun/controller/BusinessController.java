package kesun.controller;

import kesun.entity.AbsBusinessObject;
import kesun.entity.AbsSuperObject;
import kesun.entity.system.User;
import kesun.util.JSONAndObject;
import kesun.util.Tool;
import org.json.JSONObject;
import shiro.TokenManage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 业务数据超级控制层类
 * Created by wph-pc on 2017/9/8.
 */
public abstract class BusinessController extends TopController {

    /*获取客户端参数初始化*/
    public AbsSuperObject initParameter(HttpServletRequest request, HttpServletResponse response)
     {
         JSONObject param= ControlTool.GetRequestJSON(request,response);//获取页面JSON对象参数
         //AbsBusinessObject model= JSONAndObject.JSONObjectToJavaBean(param,((AbsBusinessObject)getService().getModel()).getClass()); //获取页面对象
         AbsBusinessObject model= ControlTool.GetJSONToJavaBean(request,response,((AbsBusinessObject)getService().getModel()).getClass()); //获取页面对象

         if (model.getId().trim().equals(""))
             model.setId(Tool.CreateID());
         /*此处写入用户操作信息，组织结构信息、角色信息*/
         User temp=TopController.GetCurrentUser();
         model.setActor(temp.getActors()==null?null:temp.getActors().get(0));//设置用户角色
         model.setOrg(temp.getOrg()==null?null:temp.getOrg());
         model.setUser(temp);

         getService().setModel(model);//设置操作实体对象
         return model;
     }
}
