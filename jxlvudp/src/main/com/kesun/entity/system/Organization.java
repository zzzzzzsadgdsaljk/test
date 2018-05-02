package kesun.entity.system;

import kesun.entity.AbsBusinessTreeNode;

/**
 * 组织机构
 * Created by wph-pc on 2017/5/30.
 */
public class Organization extends AbsBusinessTreeNode {
    private String charger="";//机构负责人
    private String type="";//机构行政类别
    private String phone="";//机构联系方式
    private String url="";//机构网址
    private String address="";//机构地址
    private String postCode="";//邮政编码
    private String description="";//机构职能说明


    public String getCharger() {
        return charger;
    }

    public void setCharger(String charger) {
        this.charger = charger;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Organization{" +
                "charger='" + charger + '\'' +
                ", type='" + type + '\'' +
                ", phone='" + phone + '\'' +
                ", url='" + url + '\'' +
                ", address='" + address + '\'' +
                ", postCode='" + postCode + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
