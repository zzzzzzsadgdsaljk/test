package kesun.entity.web;

import kesun.entity.AbsBusinessObject;

/**
 * 子网站或子频道实体类
 * Created by wph-pc on 2017/5/30.
 */
public class WebSite extends AbsBusinessObject {
    private String indexAddress="";//子网站首页地址
    private boolean isAutoAcceptTopMSG=true;//是否接受主站信息，true表示自动接收，false表示不接收
    private boolean isNeedCheckToTopMSG=true;//发送到主网站信息是否需要审核接收，true表示需要审核，false表示不要审核

    public String getIndexAddress() {
        return indexAddress;
    }

    public void setIndexAddress(String indexAddress) {
        this.indexAddress = indexAddress;
    }

    public boolean isAutoAcceptTopMSG() {
        return isAutoAcceptTopMSG;
    }

    public void setAutoAcceptTopMSG(boolean autoAcceptTopMSG) {
        isAutoAcceptTopMSG = autoAcceptTopMSG;
    }

    public boolean isNeedCheckToTopMSG() {
        return isNeedCheckToTopMSG;
    }

    public void setNeedCheckToTopMSG(boolean needCheckToTopMSG) {
        isNeedCheckToTopMSG = needCheckToTopMSG;
    }
}
