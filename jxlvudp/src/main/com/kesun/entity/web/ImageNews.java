package kesun.entity.web;

import kesun.entity.AbsSuperObject;

/**
 * 图片新闻
 * Created by wph-pc on 2017/5/30.
 */
public class ImageNews extends AbsSuperObject {
    private String title="";//图片新闻标题
    private String imageAddress="";//图片地址
    private String description="";//图片说明
    private int serial=-1;//显示序号
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageAddress() {
        return imageAddress;
    }

    public void setImageAddress(String imageAddress) {
        this.imageAddress = imageAddress;
    }

    public int getSerial() {
        return serial;
    }

    public void setSerial(int serial) {
        this.serial = serial;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
