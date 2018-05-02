package kesun.entity.system;

import kesun.entity.AbsSuperObject;

import java.util.List;

/**
 * 角色权限清单
 * Created by wph-pc on 2017/5/30.
 */
public class ActorPowerSheet extends AbsSuperObject {
    private Actor actor=null;//角色
    private List<SystemMenu> powers=null;//权限表

    public Actor getActor() {
        return actor;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }

    public List<SystemMenu> getPowers() {
        return powers;
    }

    public void setPowers(List<SystemMenu> powers) {
        this.powers = powers;
    }
}
