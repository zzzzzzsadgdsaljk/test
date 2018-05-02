package kesun.entity.system;

import kesun.entity.AbsTreeNode;

import java.util.List;

/**
 * Created by wph-pc on 2017/5/29.
 */
public class Actor extends AbsTreeNode {
    private String description="";//说明
    private ActorType type=null;//角色类型
    private List<SystemMenu> powers=null;//权限集合
    private Boolean isPower=false;
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ActorType getType() {
        return type;
    }

    public void setType(ActorType type) {
        this.type = type;
    }

    public Boolean getPower() {
        return isPower;
    }

    public void setPower(Boolean power) {
        isPower = power;
    }

    public List<SystemMenu> getPowers() {
        return powers;
    }

    public void setPowers(List<SystemMenu> powers) {
        this.powers = powers;
    }
}
