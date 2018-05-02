package kesun.entity;

import kesun.entity.system.Actor;
import kesun.entity.system.Organization;
import kesun.entity.system.User;

/**
 * 带有业务性质的树形结构
 * Created by wph-pc on 2017/10/21.
 */
public class AbsBusinessTreeNode extends AbsTreeNode {
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
