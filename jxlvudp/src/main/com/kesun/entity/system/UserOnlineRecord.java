package kesun.entity.system;


import kesun.entity.AbsSuperObject;
import shiro.session.SessionStatus;

import java.util.Date;

/**
 * 用户登录与退出日志清单
 * Created by wph-pc on 2017/5/29.
 */
public class UserOnlineRecord extends AbsSuperObject {
    private  String ipAddress="";//登录地址
    private User loginUser=null;//登录用户
    private Date loginDate;//登录时间
    private Date lastAccessDate;//最近一次访问时间
    private Date logoutDate;//退出时间
    private String sessionID="";//登录的sessionID
    private Long timeout;//回到会话期
    private Date startTime;//创建时间
    private String sessionStatus= SessionStatus.ONLINE;//session状态
    private String type="0";//登录设备类型，0表示PC端，1表示Android端，2表示iOS端，3表示其他设备

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public User getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(User loginUser) {
        this.loginUser = loginUser;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    public Date getLogoutDate() {
        return logoutDate;
    }

    public void setLogoutDate(Date logoutDate) {
        this.logoutDate = logoutDate;
    }

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getTimeout() {
        return timeout;
    }

    public void setTimeout(Long timeout) {
        this.timeout = timeout;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public String getSessionStatus() {
        return sessionStatus;
    }

    public void setSessionStatus(String sessionStatus) {
        this.sessionStatus = sessionStatus;
    }

    public Date getLastAccessDate() {
        return lastAccessDate;
    }

    public void setLastAccessDate(Date lastAccessDate) {
        this.lastAccessDate = lastAccessDate;
    }
}
