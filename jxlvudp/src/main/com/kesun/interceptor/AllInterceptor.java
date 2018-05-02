package kesun.interceptor;

import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;

/**
 * Created by wph-pc on 2017/6/11.
 */
public class AllInterceptor implements WebRequestInterceptor {
    public void preHandle(WebRequest webRequest) throws Exception {
        System.out.println("AllInterceptor...............................");
        webRequest.setAttribute("request", "request", WebRequest.SCOPE_REQUEST);//这个是放到request范围内的，所以只能在当前请求中的request中获取到
        webRequest.setAttribute("session", "session", WebRequest.SCOPE_SESSION);//这个是放到session范围内的，如果环境允许的话它只能在局部的隔离的会话中访问，否则就是在普通的当前会话中可以访问
        webRequest.setAttribute("globalSession", "globalSession", WebRequest.SCOPE_GLOBAL_SESSION);//如果环境允许的话，它能在全局共享的会话中访问，否则就是在普通的当前会话中访问

    }

    public void postHandle(WebRequest webRequest, ModelMap modelMap) throws Exception {

          System.out.println("postHandle-------------------------");;

    }

    public void afterCompletion(WebRequest webRequest, Exception e) throws Exception {
        System.out.println(e + "-=-=--=--=-=-=-=-=-=-=-=-==-=--=-=-=-=");
    }
}
