package shiro.session;

import java.io.Serializable;

/**
 * Created by wph-pc on 2017/9/27.
 */
public class SessionStatus implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final String SESSION_STATUS="Session_Status";//session状态名称
    public static final String ONLINE="online";//在线
    public static final String OFFLINE="offline";//离线状态
    public static final String KILLED="killed";//踢出状态
}
