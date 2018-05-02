package redis;


import kesun.util.LoggerUtils;
import kesun.util.SerializeUtil;
import org.apache.shiro.session.Session;
import shiro.session.SessionManager;
import shiro.session.SessionStatus;
import shiro.session.ShiroSessionRepository;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

/**
 * Created by wph-pc on 2017/9/27.
 */
public class RedisShiroSessionRepository implements ShiroSessionRepository {
    public static final String REDIS_SHIRO_SESSION = "shiro-session:";
    //这里有个小BUG，因为Redis使用序列化后，Key反序列化回来发现前面有一段乱码，解决的办法是存储缓存不序列化
    public static final String REDIS_SHIRO_ALL = "*shiro-session:*";
    private static final int SESSION_VAL_TIME_SPAN = 18000;
    private static final int DB_INDEX = 1;

    private RedisManager jedisManager;
    public void saveSession(Session session) {
        if (session == null || session.getId() == null)
            throw new NullPointerException("session is empty");
        try {
            byte[] key = SerializeUtil.serialize(buildRedisSessionKey(session.getId()));


            //不存在才添加。
            if(null == session.getAttribute(SessionManager.SESSION_STATUS)){
                //Session 踢出自存存储。
                SessionStatus sessionStatus = new SessionStatus();
                session.setAttribute(SessionManager.SESSION_STATUS, sessionStatus);
            }

            byte[] value = SerializeUtil.serialize(session);
            long sessionTimeOut = session.getTimeout() / 1000;
            Long expireTime = sessionTimeOut + SESSION_VAL_TIME_SPAN + (5 * 60);
            getJedisManager().saveValueByKey(DB_INDEX, key, value, expireTime.intValue());

        } catch (Exception e) {
            LoggerUtils.fmtError(getClass(), e, "save session error，id:[%s]",session.getId());
        }
    }

    public void deleteSession(Serializable sessionId) {
        if (sessionId == null) {
            throw new NullPointerException("session id is empty");
        }
        try {
            getJedisManager().deleteByKey(DB_INDEX,
                    SerializeUtil.serialize(buildRedisSessionKey(sessionId)));



        } catch (Exception e) {
            LoggerUtils.fmtError(getClass(), e, "删除session出现异常，id:[%s]",sessionId);
        }
    }

    public Session getSession(Serializable sessionId) {
        if (sessionId == null)
            throw new NullPointerException("session id is empty");
        Session session = null;
        try {
            byte[] value = getJedisManager().getValueByKey(DB_INDEX, SerializeUtil
                    .serialize(buildRedisSessionKey(sessionId)));
            session = SerializeUtil.deserialize(value, Session.class);
        } catch (Exception e) {
            LoggerUtils.fmtError(getClass(), e, "获取session异常，id:[%s]",sessionId);
        }
        return session;
    }

    public Collection<Session> getAllSessions() {
        Collection<Session> sessions = null;
        try {
            sessions = getJedisManager().AllSession(DB_INDEX,REDIS_SHIRO_SESSION);
        } catch (Exception e) {
            LoggerUtils.fmtError(getClass(), e, "获取全部session异常");
        }
        return sessions;
    }

    private String buildRedisSessionKey(Serializable sessionId) {
        return REDIS_SHIRO_SESSION + sessionId;
    }

    public RedisManager getJedisManager() {
        return jedisManager;
    }

    public void setJedisManager(RedisManager jedisManager) {
        this.jedisManager = jedisManager;
    }

}
