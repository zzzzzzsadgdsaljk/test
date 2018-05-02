package shiro.filter;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.StringUtils;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import shiro.FixURL;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 权限过滤器
 * Created by wph-pc on 2017/9/17.
 */
public class PermissionFilter extends AccessControlFilter {
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        Subject subject=getSubject(servletRequest,servletResponse);//获取用户登录凭证,里面含有用户的权限
        /*先判断带参数的权限*/
        if (o!=null)
       {
        String[] permissions=(String[])o;//权限获取
        if(permissions==null || permissions.length==0) return  false;//无权限

        /*权限验证*/
        for (String perm:permissions
             ) {
            if (subject.isPermitted(perm)) return true;
        }
       }

        /*参数权限没有通过，判断无参权限*/
        HttpServletRequest httpRequest = ((HttpServletRequest)servletRequest);
        String uri = httpRequest.getRequestURI();//获取URI
        String basePath = httpRequest.getContextPath();//获取basePath
        if(null != uri && uri.startsWith(basePath)){
            uri = uri.replaceFirst(basePath, "");
        }
        if(subject.isPermitted(uri)){
            return true;
        }

        return false;
    }

    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        Subject subject = getSubject(servletRequest, servletResponse);
        if (subject.getPrincipal() == null) {//表示没有登录，重定向到登录页面
            saveRequest(servletRequest);
            WebUtils.issueRedirect(servletRequest, servletResponse, FixURL.LOGIN_URL);
        } else {
            if (StringUtils.hasText(FixURL.UNAUTHORIZED_URL)) {//如果有未授权页面跳转过去
                WebUtils.issueRedirect(servletRequest, servletResponse, FixURL.UNAUTHORIZED_URL);
            } else {//否则返回401未授权状态码
                WebUtils.toHttp(servletResponse).sendError(HttpServletResponse.SC_UNAUTHORIZED);
            }
        }
        return false;
    }
}
