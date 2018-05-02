package kesun.controller;

import kesun.entity.AbsSuperObject;
import kesun.util.JSONAndObject;
import kesun.util.Tool;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 超级控制层类
 * Created by wph-pc on 2017/9/8.
 */
public abstract class SuperController extends TopController {

    /*获取客户端参数初始化*/
    public AbsSuperObject initParameter(HttpServletRequest request, HttpServletResponse response)
     {
         JSONObject param= ControlTool.GetRequestJSON(request,response);//获取页面JSON对象参数
         AbsSuperObject model= JSONAndObject.JSONObjectToJavaBean(param,getService().getModel().getClass()); //获取页面对象

         if (model.getId().trim().equals(""))
             model.setId(Tool.CreateID());
         getService().setModel(model);//设置操作实体对象
         return model;
     }
}
