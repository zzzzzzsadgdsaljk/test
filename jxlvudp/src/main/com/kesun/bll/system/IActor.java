package kesun.bll.system;


import kesun.bll.ITreeNode;
import kesun.entity.system.Actor;
import kesun.entity.system.SystemMenu;

import java.util.List;

/**
 * Created by wph-pc on 2017/5/29.
 */
public interface IActor extends ITreeNode {
    /*判断目标角色是否正在使用中*/
    boolean isUse();
    int setFunctionPower(List<Actor> actors, List<SystemMenu> powers);
    /*查找角色actors的功能权限*/
    List<SystemMenu> getFunctionPower(List<Actor> actors);
}
