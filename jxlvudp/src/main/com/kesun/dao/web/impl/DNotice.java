package kesun.dao.web.impl;

import kesun.dao.DaoSupport;
import kesun.dao.web.INotice;
import org.springframework.stereotype.Repository;

/**
 * Created by heying on 2017/10/29.
 */
@Repository("dNotice")
public class DNotice extends DaoSupport implements INotice {
    public DNotice() {
        setMapperName("mapper.NoticeMapper");
    }
    public Boolean objectInUse(String id)
    {
        return false;
    }
}
