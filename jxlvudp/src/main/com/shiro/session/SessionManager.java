package shiro.session;


import kesun.bll.system.impl.UserOnlineRecordServiceImpl;
import kesun.entity.system.User;
import kesun.entity.system.UserOnlineRecord;
import kesun.util.LoggerUtils;
import kesun.util.SpringContextUtil;
import kesun.util.StringUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;

import java.util.*;

/**
 * Created by wph-pc on 2017/9/27.
 * Session统一管理
 */
public class SessionManager {
    public static final String SESSION_STATUS ="SESSION_STATUS";//session状态
    private ShiroSessionRepository shiroSessionRepository;//session存储管理对象创建
    private ShiroSessionDAO customShiroSessionDAO;

    public ShiroSessionRepository getShiroSessionRepository() {
        return shiroSessionRepository;
    }

    public void setShiroSessionRepository(ShiroSessionRepository shiroSessionRepository) {
        this.shiroSessionRepository = shiroSessionRepository;
    }

    public ShiroSessionDAO getCustomShiroSessionDAO() {
        return customShiroSessionDAO;
    }

    public void setCustomShiroSessionDAO(ShiroSessionDAO customShiroSessionDAO) {
        this.customShiroSessionDAO = customShiroSessionDAO;
    }

    /*获取所有的在线用户*/
    public List<UserOnlineRecord> getAllUser() {
        //获取所有session
        Collection<Session> sessions = customShiroSessionDAO.getActiveSessions();
        List<UserOnlineRecord> list = new ArrayList<UserOnlineRecord>();

        for (Session session : sessions) {
            UserOnlineRecord bo = getSessionUserOnline(session);
            if(null != bo){
                list.add(bo);
            }
        }
        return list;
    }
    /*根据用户id查找登录凭证*/
    public List<SimplePrincipalCollection> getSimplePrincipalCollectionByUserId(String...userIds){
        //把userIds 转成Set，好判断
        Set<?> idset = StringUtils.Array2Set(userIds) ;
        //获取所有session
        Collection<Session> sessions = customShiroSessionDAO.getActiveSessions();
        if (sessions==null || sessions.size()==0) return null;
        //定义返回
        List<SimplePrincipalCollection> list = new ArrayList<SimplePrincipalCollection>();
        for (Session session : sessions) {
            //获取SimplePrincipalCollection
            Object obj = session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
            if(null != obj && obj instanceof SimplePrincipalCollection){
                //强转
                SimplePrincipalCollection spc = (SimplePrincipalCollection)obj;
                //判断用户，匹配用户ID。
                obj = spc.getPrimaryPrincipal();
                if(null != obj && obj instanceof UserOnlineRecord){
                    UserOnlineRecord user = (UserOnlineRecord)obj;
                    //比较用户ID，符合即加入集合
                    if(null != user && idset.contains(user.getId())){
                        list.add(spc);
                    }
                }
            }
        }
        return list;
    }
    public UserOnlineRecord getSession(String sessionId) {
        Session session = shiroSessionRepository.getSession(sessionId);
        UserOnlineRecord bo = getSessionUserOnline(session);
        return bo;
    }

    private UserOnlineRecord getSessionUserOnline(Session session){
        //获取session登录信息。
        Object obj = session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
        if(null == obj){
            return null;
        }
        //确保是 SimplePrincipalCollection对象。
        if(obj instanceof SimplePrincipalCollection){
            SimplePrincipalCollection spc = (SimplePrincipalCollection)obj;
            /**
             * 获取用户登录的，@link SampleRealm.doGetAuthenticationInfo(...)方法中
             * return new SimpleAuthenticationInfo(user,user.getPswd(), getName());的user 对象。
             */
            obj = spc.getPrimaryPrincipal();//用户的ID
            if(null != obj && obj instanceof String){
                /*根据Session的ID和用户账号查询数据库中是否存在日志*/

                UserOnlineRecord userBo = new UserOnlineRecord();
                if (session.getAttribute("user")!=null)
                    userBo.setLoginUser((User)session.getAttribute("user"));
                else
                {
                    userBo.setLoginUser(new User());
                    userBo.getLoginUser().setNumber(obj.toString());
                    userBo.getLoginUser().setId(obj.toString());
                    userBo.getLoginUser().setName(obj.toString());
                }

                //最后一次和系统交互的时间
                userBo.setLastAccessDate(session.getLastAccessTime());
                //主机的ip地址
                userBo.setIpAddress(session.getHost());
                //session ID
                userBo.setId(session.getId().toString());
                userBo.setSessionID(session.getId().toString());
                //session最后一次与系统交互的时间
                userBo.setLogoutDate(session.getLastAccessTime());
                //session创建时间
                userBo.setLoginDate(session.getStartTimestamp());
                //回话到期 ttl(ms)
                userBo.setTimeout(session.getTimeout());
                //session创建时间
                userBo.setStartTime(session.getStartTimestamp());

                Object sessionStatus =session.getAttribute(SessionStatus.SESSION_STATUS);

                if(sessionStatus!=null && sessionStatus.toString().trim()!=""){
                    userBo.setSessionStatus(sessionStatus.toString().trim());
                }
                else
                   userBo.setSessionStatus(SessionStatus.ONLINE);
                session.setAttribute(SessionStatus.SESSION_STATUS,SessionStatus.ONLINE);
                if (sessionStatus!=null)
                  userBo.setStatus(sessionStatus.toString());
                else
                    userBo.setStatus(SessionStatus.ONLINE);
                return userBo;
            }
        }
        return null;
    }
    /*改变Session状态*/
    public int changeSessionStatus(String status,String sessionIds) {
        try {
            String[] sessionIdArray = null;
            if(sessionIds.indexOf(",") ==-1){
                sessionIdArray = new String[]{sessionIds};
            }else{
                sessionIdArray = sessionIds.split(",");
            }
            for (String id : sessionIdArray) {
                Session session = shiroSessionRepository.getSession(id);
                if (status.trim().equals(SessionStatus.KILLED))
                   session.setAttribute(SessionStatus.SESSION_STATUS, SessionStatus.KILLED);
                if (status.trim().equals(SessionStatus.OFFLINE))
                    session.setAttribute(SessionStatus.SESSION_STATUS, SessionStatus.OFFLINE);
                if (status.trim().equals(SessionStatus.ONLINE))
                    session.setAttribute(SessionStatus.SESSION_STATUS, SessionStatus.ONLINE);

                customShiroSessionDAO.update(session);
            }
            return 1;
        } catch (Exception e) {
            LoggerUtils.fmtError(getClass(), e, "改变Session状态错误，sessionId[%s]", sessionIds);
            return 0;
        }
    }
    /*踢出在线用户*/
    public void forbidUserById(String id) {
        //获取所有在线用户
        for(UserOnlineRecord bo : getAllUser()){
            String userId = bo.getId();
            //匹配用户ID
            if(userId.equals(id)){
                //获取用户Session
                Session session = shiroSessionRepository.getSession(bo.getSessionID());
                session.setAttribute(SessionStatus.SESSION_STATUS,SessionStatus.KILLED);
                //更新Session
                customShiroSessionDAO.update(session);
            }
        }
    }
    /*日志中是否存在指定用户和Session,如果存在，返回ID，否则返回null*/
    private String getOnlineIDByUserSession(String userId,Session session)
    {
        UserOnlineRecordServiceImpl bll=(UserOnlineRecordServiceImpl) SpringContextUtil.getBean("bUserOnlineRecord");
        Map<String,Object> values=new HashMap<String, Object>();
        values.put("loginUser",userId);
        values.put("sessionID",session.getId());
        List<?> objs=bll.find(values);
        if (objs!=null && objs.size()>0)
            return ((UserOnlineRecord)objs.get(0)).getId() ;
        else
            return null;
    }

    /*将当前用户在线记录保存在数据库中*/
    public void createOnlineRecordToDB(Session session,String status)
    {
        SessionManager sessionManager=(SessionManager) SpringContextUtil.getBean("customSessionManager");//获取Session管理对象
        if (sessionManager==null || session==null) return;

        UserOnlineRecord onlineRecord=sessionManager.getSession(session.getId().toString());//根据当前Session的ID获取在线人员信息
        if (onlineRecord==null || onlineRecord.getId().trim().equals("") || onlineRecord.getLoginUser()==null || onlineRecord.getLoginUser().getNumber().trim().equals("")) return;
        UserOnlineRecordServiceImpl bll=(UserOnlineRecordServiceImpl)SpringContextUtil.getBean("bUserOnlineRecord");
        onlineRecord.setLoginDate(new Date());
        onlineRecord.setLogoutDate(new Date());
        onlineRecord.setStatus(status);
        bll.setModel(onlineRecord);
        Object obj=bll.getMe();
        if (obj==null)
            bll.add();
        else
            bll.edit();

    }
}
