package shiro.filter;


import kesun.util.LoggerUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import redis.VCache;
import shiro.TokenManage;
import shiro.session.SessionStatus;
import shiro.session.ShiroSessionRepository;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by wph-pc on 2017/9/27.
 */
public class KickoutSessionFilter extends AccessControlFilter {
    final static String ONLINE_USER ="online";
    final static String KICKOUT_STATUS="killed";
    //静态注入
    static String kickoutUrl;
    static VCache cache;
    static ShiroSessionRepository shiroSessionRepository;
    public ShiroSessionRepository getShiroSessionRepository() {
        return shiroSessionRepository;
    }

    public static void setShiroSessionRepository(ShiroSessionRepository shiroSessionRepository) {
        KickoutSessionFilter.shiroSessionRepository = shiroSessionRepository;
    }

    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue ) throws Exception {
        HttpServletRequest httpRequest = ((HttpServletRequest)request);
        String url = httpRequest.getRequestURI();
        Subject subject = getSubject(request, response);
        //如果是相关目录 or 如果没有登录 就直接return true
        if(url.startsWith("/admin/") || (!subject.isAuthenticated() && !subject.isRemembered())){
            return Boolean.TRUE;
        }
        Session session = subject.getSession();
        Serializable sessionId = session.getId();

        Object status=session.getAttribute(SessionStatus.SESSION_STATUS);//获取当前用户状态
        if (status==null)
        {
            session.setAttribute(SessionStatus.SESSION_STATUS,SessionStatus.ONLINE);
        }
        else
        {
            String marker = (String)status;
            if (marker.equals(SessionStatus.OFFLINE.trim()) || marker.equals(SessionStatus.KILLED.trim()) ) {
            return  Boolean.FALSE;
            }
        }


        //从缓存获取用户-Session信息 <UserId,SessionId>
        LinkedHashMap<String, Serializable> infoMap = cache.get(ONLINE_USER, LinkedHashMap.class);
        //如果不存在，创建一个新的
        infoMap = null == infoMap ? new LinkedHashMap<String, Serializable>() : infoMap;

        //获取tokenId
        String userId = TokenManage.getUserId();

        //如果已经包含当前Session，并且是同一个用户，跳过。
        if(infoMap.containsKey(userId) && infoMap.containsValue(sessionId)){
            //更新存储到缓存1个小时（这个时间最好和session的有效期一致或者大于session的有效期）
            cache.setex(ONLINE_USER, infoMap, 3600);
            return Boolean.TRUE;
        }
        //如果用户相同，Session不相同，那么就要处理了
        /**
         * 如果用户Id相同,Session不相同
         * 1.获取到原来的session，并且标记为踢出。
         * 2.继续走
         */
        if(infoMap.containsKey(userId) && !infoMap.containsValue(sessionId)){
            Serializable oldSessionId = infoMap.get(userId);
            Session oldSession = shiroSessionRepository.getSession(oldSessionId);
            if(null != oldSession){
                //标记session已经踢出
                oldSession.setAttribute(SessionStatus.SESSION_STATUS, SessionStatus.KILLED);
                shiroSessionRepository.saveSession(oldSession);//更新session
                LoggerUtils.fmtDebug(getClass(), "kickout old session success,oldId[%s]",oldSessionId);
            }else{
                shiroSessionRepository.deleteSession(oldSessionId);
                infoMap.remove(userId);
                //存储到缓存1个小时（这个时间最好和session的有效期一致或者大于session的有效期）
                cache.setex(ONLINE_USER, infoMap, 3600);
            }
            return  Boolean.TRUE;
        }

        if(!infoMap.containsKey(userId) && !infoMap.containsValue(sessionId)){
            infoMap.put(userId, sessionId);
            //存储到缓存1个小时（这个时间最好和session的有效期一致或者大于session的有效期）
            cache.setex(ONLINE_USER, infoMap, 3600);
        }
        return Boolean.TRUE;
    }

    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        //先退出
        Subject subject = getSubject(request, response);
        subject.logout();
        WebUtils.getSavedRequest(request);
        //再重定向
        WebUtils.issueRedirect(request, response,kickoutUrl);
        return false;
    }


    public static  String getKickoutUrl() {
        return kickoutUrl;
    }

    public static  void setKickoutUrl(String kickoutUrl) {
        KickoutSessionFilter.kickoutUrl = kickoutUrl;
    }
}
