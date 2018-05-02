package kesun.controller.web.impl;

import kesun.bll.SuperService;
import kesun.bll.web.impl.NewsServiceImpl;
import kesun.controller.BusinessController;
import kesun.controller.ControlTool;
import kesun.controller.ReturnBean;
import kesun.controller.web.INews;
import kesun.entity.AbsSuperObject;
import kesun.entity.web.ImageNews;
import kesun.entity.web.News;
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
import java.util.Map;

/**
 * Created by wph-pc on 2017/11/3.
 */
@Controller
@RequestMapping("/news")
public class NewsController extends BusinessController implements INews {

    @Resource(name="bNews")
    private NewsServiceImpl bll;
    public SuperService getService() {
        return bll;
    }

    public Map<String, Object> getConditionParam(JSONObject param) {
        if (param==null) return  null;//判断条件是否为空param是页面传递的值
        Map<String,Object> values=new HashMap<String, Object>();
        if (JSONAndObject.GetJsonStringValue(param,"condition")!=null)
        {
            values.put("id", JSONAndObject.GetJsonStringValue(param,"condition"));
            values.put("name",JSONAndObject.GetJsonStringValue(param,"condition"));
        }
        if (JSONAndObject.GetJsonStringValue(param,"status")!=null)
        {
            values.put("status",JSONAndObject.GetJsonStringValue(param,"status"));

        }
        return values;
    }

    public Map<String, Object> setFindFilter(JSONObject param) {
        return null;
    }

    /*重写父类方法*/
    public AbsSuperObject updateModel(AbsSuperObject model)
    {
        if (model!=null && model instanceof News)
        {
            News temp=(News)model;
            if (temp.getImages()!=null)
            {
                for (ImageNews obj:temp.getImages()
                     ) {
                    obj.setId(Tool.CreateID());
                }
            }
            return temp;
        }
        return model;
    }
    @RequestMapping("/updateStatus")
    @ResponseBody
    public Object changeStatus(HttpServletRequest request, HttpServletResponse response)
    {
        AbsSuperObject model=updateModel(initParameter(request,response));//获取客户端参数信息
        getService().setModel(model);
        ReturnBean returnBean=new ReturnBean();
        if (bll.changeStatus(model.getStatus(),Tool.StringArrayToList(model.getId().split(",")))>0)
        {
            returnBean.setCode("1");
            returnBean.setMessage("状态变更成功！");
        }
        else
        {
            returnBean.setCode("-1");
            returnBean.setMessage("状态变更失败！");
        }
        return returnBean;

    }
    @RequestMapping("/index")
    public String index(){
        return "web/news/NewsManage";
    }

    //删除新闻图片
    @RequestMapping(value="/delImg")
    @ResponseBody
    public Object delImg(HttpServletRequest request, HttpServletResponse response){
        AbsSuperObject model = initParameter(request,response);
        ReturnBean returnBean=new ReturnBean();
        if (bll.delImg(model.getId())>0)
        {
            returnBean.setCode("1");
            returnBean.setMessage("图片删除成功！");
        }
        else
        {
            returnBean.setCode("-1");
            returnBean.setMessage("图片删除失败！");
        }
        return returnBean;
    }
    //查询新闻图片
    @RequestMapping(value = "/SelectImageNews")
    @ResponseBody
    public Object imageNews(HttpServletRequest request, HttpServletResponse response) {
        //根据NewsID去查询图片
        AbsSuperObject model = initParameter(request,response);
        ReturnBean returnBean=new ReturnBean();
        if (bll.imageNews(model.getId()).size()>0)
        {
            returnBean.setCode("1");
            returnBean.setObj(bll.imageNews(model.getId()));
            returnBean.setMessage("数据即将呈现！请稍后......");
        }
        else
        {
            returnBean.setCode("-1");
            returnBean.setMessage("对不起，该数据暂时还没有！");
        }
        return returnBean;
    }
    //此方法是重写删除方法，原因就是
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    @ResponseBody
    public Object del(HttpServletRequest request, HttpServletResponse response) {
        AbsSuperObject model=updateModel(initParameter(request,response));//获取客户端参数信息
        getService().setModel(model);
        ReturnBean returnBean=new ReturnBean();
        if(getService().del() > 0){
            returnBean.setCode("1");
            returnBean.setMessage("恭喜您,数据删除成功 ^ _ ^ ! !");
        }
        else{
            returnBean.setCode("-1");
            returnBean.setMessage("对不起，数据删除失败！+ _ + !!");
        }
        return returnBean;
    }
}
