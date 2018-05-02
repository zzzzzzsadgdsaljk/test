package kesun.entity.web;

import kesun.entity.AbsBusinessObject;

import java.util.Date;
import java.util.List;

/**
 * 新闻实体类
 * Created by wph-pc on 2017/5/30.
 */
public class News extends AbsBusinessObject {
    private String content="";//新闻内容
    private Date sendDate;//发布日期
    private WebSite web=null;//发布网站
    private List<ImageNews> images=null;//图片集合
    private NewsCategory newsBlock=null;//新闻版块
    private String type;//新闻类别
    private String videoAddress="";//视频地址
    private boolean isShare=false;//新闻是否子父网站同时显示

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

    public WebSite getWeb() {
        return web;
    }

    public void setWeb(WebSite web) {
        this.web = web;
    }

    public List<ImageNews> getImages() {
        return images;
    }

    public void setImages(List<ImageNews> images) {
        this.images = images;
    }

    public NewsCategory getNewsBlock() {
        return newsBlock;
    }

    public void setNewsBlock(NewsCategory newsBlock) {
        this.newsBlock = newsBlock;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVideoAddress() {
        return videoAddress;
    }

    public void setVideoAddress(String videoAddress) {
        this.videoAddress = videoAddress;
    }

    public boolean getIsShare() {
        return isShare;
    }

    public void setIsShare(boolean share) {
        isShare = share;
    }
}
