package shiro.session;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;



/**
 * Created by wph-pc on 2017/9/27.
 */
public class ShiroSessionListener implements SessionListener {
    private final String COUNT_KEY = "sessions";
    private ShiroSessionRepository shiroSessionRepository;

    public ShiroSessionRepository getShiroSessionRepository() {
        return shiroSessionRepository;
    }

    public void setShiroSessionRepository(ShiroSessionRepository shiroSessionRepository) {
        this.shiroSessionRepository = shiroSessionRepository;
    }

    public void onStart(Session session) {
        System.out.println("on start");

    }

    public void onStop(Session session) {
        System.out.println("on Stop");
    }

    public void onExpiration(Session session) {
        shiroSessionRepository.deleteSession(session.getId());
    }
}
