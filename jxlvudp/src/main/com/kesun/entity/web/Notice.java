package kesun.entity.web;

import kesun.entity.AbsBusinessObject;
import kesun.entity.system.User;

import java.util.Date;

/**
 * 通知实体类
 * Created by wph-pc on 2017/5/30.
 */
public class Notice extends AbsBusinessObject {
    private String content="";//通知内容
    private Date sendDate;//通知发布日期
    private User sender=null;//通知发布者
    private WebSite web=null;//通知发布网站

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public WebSite getWeb() {
        return web;
    }

    public void setWeb(WebSite web) {
        this.web = web;
    }
}
