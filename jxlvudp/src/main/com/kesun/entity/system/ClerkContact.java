package kesun.entity.system;

import kesun.entity.AbsSuperObject;
import kesun.entity.system.Clerk;

/**
 * 联系人方式
 * Created by wph-pc on 2017/7/20.
 */
public class ClerkContact extends AbsSuperObject {
    private String value="";//联系内容值，例如：名称为手机号，联系内容值：13755XXXX63
    private Clerk clerk=null;//联系方式所属人

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Clerk getClerk() {
        return clerk;
    }

    public void setClerk(Clerk clerk) {
        this.clerk = clerk;
    }
}
