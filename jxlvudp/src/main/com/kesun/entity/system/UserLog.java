package kesun.entity.system;

import kesun.entity.AbsSuperObject;

import java.util.Date;

/**
 * 用户操作日志类
 * Created by wph-pc on 2017/5/29.
 */
public class UserLog extends AbsSuperObject {
    private String ipAddress="";//操作IP地址
    private Date doDate;//操作时间
    private User byUser=null;//操作用户
    private String actionName="";//操作功能名称
    private String description="";//操作说明
    private String sessionID="";//登录的sessionID
    private String type="0";//登录设备类型，0表示PC端，1表示Android端，2表示iOS端，3表示其他设备
    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Date getDoDate() {
        return doDate;
    }

    public void setDoDate(Date doDate) {
        this.doDate = doDate;
    }

    public User getByUser() {
        return byUser;
    }

    public void setByUser(User byUser) {
        this.byUser = byUser;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
}
