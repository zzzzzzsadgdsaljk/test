package kesun.entity.web;

import kesun.entity.AbsBusinessTreeNode;

/**
 * 新闻分类实体类
 * Created by wph-pc on 2017/5/30.
 */
public class NewsCategory extends AbsBusinessTreeNode {
    private WebSite web=null;//所属网站
    private String description="";//分类说明

    public WebSite getWeb() {
        return web;
    }

    public void setWeb(WebSite web) {
        this.web = web;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
