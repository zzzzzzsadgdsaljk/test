package kesun.entity;

import kesun.entity.system.Actor;
import kesun.entity.system.Organization;
import kesun.entity.system.User;

/**
 * 业务数据超级类
 * Created by wph-pc on 2017/5/30.
 */
public abstract class AbsBusinessObject extends AbsSuperObject {
    private User user=null;//数据用户拥有者
    private Actor actor=null;//数据角色拥有者
    private Organization org=null;//数据拥有的组织机构

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Actor getActor() {
        return actor;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }

    public Organization getOrg() {
        return org;
    }

    public void setOrg(Organization org) {
        this.org = org;
    }
}
