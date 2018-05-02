package shiro.session;

import org.apache.shiro.session.Session;

import java.io.Serializable;
import java.util.Collection;

/**
 * Created by wph-pc on 2017/9/27.
 * Session操作接口定义
 */
public interface ShiroSessionRepository {
    void saveSession(Session session);
    void deleteSession(Serializable sessionId);
    Session getSession(Serializable sessionId);
    Collection<Session> getAllSessions();
}
