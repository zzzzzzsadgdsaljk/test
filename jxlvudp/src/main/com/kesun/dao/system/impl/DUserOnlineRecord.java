package kesun.dao.system.impl;

import kesun.dao.DaoSupport;
import kesun.dao.system.IUserOnlineRecord;
import org.springframework.stereotype.Repository;

/**
 * Created by wph-pc on 2017/5/30.
 */
@Repository("dUserOnlineRecord")
public class DUserOnlineRecord extends DaoSupport implements IUserOnlineRecord {
    public DUserOnlineRecord() {
        setMapperName("mapper.UserOnlineRecordMapper");
    }
}
