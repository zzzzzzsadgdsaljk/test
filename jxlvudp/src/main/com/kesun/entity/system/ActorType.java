package kesun.entity.system;

import kesun.entity.AbsSuperObject;


import java.util.List;

public class ActorType extends AbsSuperObject {
    private Organization org=null;//组织机构
    private String type="";//分类类型，为个人或集体
    public Organization getOrg() {
        return org;
    }

    public void setOrg(Organization org) {
        this.org = org;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}