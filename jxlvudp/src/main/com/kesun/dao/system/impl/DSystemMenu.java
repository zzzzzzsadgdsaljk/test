package kesun.dao.system.impl;

import kesun.dao.DaoSupport;
import kesun.dao.system.ISystemMenu;
import org.springframework.stereotype.Repository;

/**
 * Created by wph-pc on 2017/9/23.
 */
@Repository("dSystemMenu")
public class DSystemMenu extends DaoSupport implements ISystemMenu {
    public DSystemMenu() {
        setMapperName("mapper.SystemMenuMapper");
    }
}
