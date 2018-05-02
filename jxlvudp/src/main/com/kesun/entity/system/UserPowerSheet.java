package kesun.entity.system;

import kesun.entity.AbsSuperObject;

import java.util.List;

/**
 * 用户权限清单表
 * Created by wph-pc on 2017/5/30.
 */
public class UserPowerSheet extends AbsSuperObject {
    private User user=null;
    private List<SystemMenu> powers=null;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<SystemMenu> getPowers() {
        return powers;
    }

    public void setPowers(List<SystemMenu> powers) {
        this.powers = powers;
    }
}
