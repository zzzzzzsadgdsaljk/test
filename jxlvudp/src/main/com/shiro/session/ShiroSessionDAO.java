package shiro.session;

import kesun.util.LoggerUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;

import java.io.Serializable;
import java.util.Collection;

/**
 * Created by wph-pc on 2017/9/27.
 * Session对象统一管理
 */
public class ShiroSessionDAO  extends AbstractSessionDAO {
    private ShiroSessionRepository shiroSessionRepository;

    public ShiroSessionRepository getShiroSessionRepository() {
        return shiroSessionRepository;
    }

    public void setShiroSessionRepository(ShiroSessionRepository shiroSessionRepository) {
        this.shiroSessionRepository = shiroSessionRepository;
    }

    protected Serializable doCreate(Session session) {
        Serializable sessionId = this.generateSessionId(session);
        this.assignSessionId(session, sessionId);

        getShiroSessionRepository().saveSession(session);
        return sessionId;
    }

    protected Session doReadSession(Serializable serializable) {
        return getShiroSessionRepository().getSession(serializable);
    }

    public void update(Session session) throws UnknownSessionException {
        getShiroSessionRepository().saveSession(session);
    }

    public void delete(Session session) {
        if (session == null) {
            LoggerUtils.error(getClass(), "Session 不能为null");
            return;
        }
        Serializable id = session.getId();
        if (id != null)
        {
            getShiroSessionRepository().deleteSession(id);
        }
    }

    public Collection<Session> getActiveSessions() {
        return getShiroSessionRepository().getAllSessions();
    }
}
