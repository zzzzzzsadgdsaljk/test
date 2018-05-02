package kesun.dao.web.impl;

import kesun.dao.DaoSupport;
import kesun.dao.web.INotice;
import org.springframework.stereotype.Repository;

/**
 * Created by heying on 2017/10/29.
 */
@Repository("dTest")
public class DTest extends DaoSupport implements INotice {
    public DTest() {
        setMapperName("mapper.TestMapper");
    }
    public Boolean objectInUse(String id)
    {
        return false;
    }
}
