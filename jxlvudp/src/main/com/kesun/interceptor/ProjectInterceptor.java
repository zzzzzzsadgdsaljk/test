package kesun.interceptor;


import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by wph-pc on 2017/6/11.
 */
public class ProjectInterceptor implements HandlerInterceptor {
    private List<String> excludedUrls;
    public void setExcludeUrls(List<String> excludeUrls) {
              this.excludedUrls = excludeUrls;
         }
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        System.out.println("拦截器在工作中......"+httpServletRequest.getMethod());
        /*静态资源直接通过*/
        if(o instanceof ResourceHttpRequestHandler){
            return true;
        }
        String requestUri = httpServletRequest.getRequestURI();
        /*过滤登录页面和主页面*/
        for (String url : excludedUrls) {
            if (requestUri.endsWith(url)) {
                return true;
            }
        }
        /*处理用户是否登录*/
//        Object obj=httpServletRequest.getSession().getAttribute("user");//获取用户登录信息
//        if (obj instanceof com.kesun.entity.User==false) {
//            throw new ControllerException();
//        }

        /*用户权限*/
        return true;//通过请求
        //throw new ControllerException();
    }

    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
