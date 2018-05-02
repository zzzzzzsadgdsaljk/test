package kesun.dao.system;


import kesun.entity.AbsSuperObject;
import kesun.entity.system.Actor;
import kesun.entity.system.SystemMenu;

import java.util.List;

/**
 * Created by wph-pc on 2017/5/30.
 */
public interface IActor {
    /*判断目标角色是否正在使用中*/
    boolean isUse(AbsSuperObject actor);
    /*对指定的actors角色组进行powers功能授权*/
    int setFunctionPower(List<Actor> actors, List<SystemMenu> powers);
    /*查找角色actors的功能权限*/
    List<SystemMenu> getFunctionPower(List<Actor> actors);
}
