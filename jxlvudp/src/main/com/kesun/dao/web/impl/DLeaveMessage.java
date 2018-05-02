package kesun.dao.web.impl;


import kesun.dao.DaoSupport;
import kesun.dao.web.ILeaveMessage;
import kesun.entity.web.LeaveMessage;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by wph-pc on 2017/5/30.
 */
@Repository("dLeaveMessage")
public class DLeaveMessage extends DaoSupport implements ILeaveMessage {
    public DLeaveMessage()
    {
        setMapperName("mapper.LeaveMessageMapper");
    }
    public int response(String content) {
        return 0;
    }

    public List<LeaveMessage> findResponseMSG(Map<String, Object> conValus) {
        return null;
    }
}
