package kesun.entity.web;

import kesun.entity.AbsBusinessObject;

/**
 * 广告实体类
 * Created by wph-pc on 2017/5/30.
 */
public class Advertisement extends AbsBusinessObject {
    private String imgAddress="";//广告图片地址
    private String videoAddress="";//广告视频地址
    private String linkUrl="";//广告链接地址
    private String homeUrl="";//广告嵌入页面地址
    private String content="";//广告内容
    private String type="";//广告类型
    private int posX=0;//广告X坐标位置
    private int posY=-1;//广告Y坐标位置
    private int width=0;//广告宽度
    private int height=0;//广告高度
    private int duration=0;//广告持续显示时长，-1表示永久显示;秒为单位
    private WebSite web=null;//广告所在网站

    public String getImgAddress() {
        return imgAddress;
    }

    public void setImgAddress(String imgAddress) {
        this.imgAddress = imgAddress;
    }

    public String getVideoAddress() {
        return videoAddress;
    }

    public void setVideoAddress(String videoAddress) {
        this.videoAddress = videoAddress;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getHomeUrl() {
        return homeUrl;
    }

    public void setHomeUrl(String homeUrl) {
        this.homeUrl = homeUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public WebSite getWeb() {
        return web;
    }

    public void setWeb(WebSite web) {
        this.web = web;
    }
}
