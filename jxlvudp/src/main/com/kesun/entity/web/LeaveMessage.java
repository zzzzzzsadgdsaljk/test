package kesun.entity.web;

import kesun.entity.AbsSuperObject;
import kesun.entity.system.User;

import java.util.Date;

/**
 * 留言板实体类
 * Created by wph-pc on 2017/5/30.
 */
public class LeaveMessage extends AbsSuperObject {
    private String content="";//留言内容
    private Date leaveDate;//留言时间
    private String response="";//反馈内容
    private  Date responseDate;//反馈日期
    private User worker=null;//反馈人

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getLeaveDate() {
        return leaveDate;
    }

    public void setLeaveDate(Date leaveDate) {
        this.leaveDate = leaveDate;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Date getResponseDate() {
        return responseDate;
    }

    public void setResponseDate(Date responseDate) {
        this.responseDate = responseDate;
    }

    public User getWorker() {
        return worker;
    }

    public void setWorker(User worker) {
        this.worker = worker;
    }
}
