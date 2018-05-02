package kesun.util;


import kesun.entity.system.UserOnlineRecord;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.*;

/**
 * 监控用户在线
 * Created by wph-pc on 2017/7/24.
 */
public class SessionListener implements HttpSessionListener {
    private final String COUNT_KEY = "sessions";

    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        HttpSession session = httpSessionEvent.getSession();
        if (session==null) return;
        ServletContext application = session.getServletContext();

        // 在application范围由一个HashSet集保存所有的session
        List<Object> sessions = (List<Object>) application.getAttribute(COUNT_KEY);
        if (sessions == null) {
            sessions = new ArrayList<Object>();
            application.setAttribute(COUNT_KEY, sessions);
        }

        // 新创建的session均添加到HashSet集中
        sessions.add(session);

        // 然后使用sessions.size()获取当前活动的session数，即为“在线人数”
        System.out.println("sessionCreated 网站在线人数（含未登录的访问） 为：" + sessions.size()+"sessionID:"+session.getId());
    }

    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        HttpSession session = httpSessionEvent.getSession();
        ServletContext application = session.getServletContext();
        List<Object> sessions = (List<Object>) application.getAttribute(COUNT_KEY);
        if (null != sessions && sessions.contains(session)) {
            if (session.getAttribute("user") instanceof UserOnlineRecord)
            {
                Object userObj = session.getAttribute("user");
            System.out.println("userObj is:" + userObj);
            if (userObj != null) {//判断是否为登录后包含用户信息的数据（已经登录）
                UserOnlineRecord user = (UserOnlineRecord) userObj;
                String username = user.getLoginUser().getName();
                System.out.println("用户" + username + "退出");
                /*编写退出日志代码*/

            } else {//未进行登录
                System.out.println("session存在，但是尚未进行用户登录");
            }
            }
        } else {
            //session已经不存在此退出对象了
            System.out.println("session已经不存在此退出对象了");
        }

        // 销毁的session均从HashSet集中移除
        if (sessions != null) {
            sessions.remove(session);
            //更新在线人数数据
            System.out.println("sessionDestroyed 网站在线人数（含未登录的访问） 为：" + sessions.size());
        }
    }
}
