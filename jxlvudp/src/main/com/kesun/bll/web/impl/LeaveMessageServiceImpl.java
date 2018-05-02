package kesun.bll.web.impl;

import kesun.bll.SuperService;
import kesun.bll.web.ILeaveMessage;
import kesun.dao.DaoSupport;
import kesun.dao.web.impl.DLeaveMessage;
import kesun.entity.web.LeaveMessage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by wph-pc on 2017/5/30.
 */
@Service("bLeaveMessage")
public class LeaveMessageServiceImpl extends SuperService implements ILeaveMessage {
    @Resource(name="dLeaveMessage")
    private DLeaveMessage dao;

    public LeaveMessageServiceImpl()
    {
        setModel(new LeaveMessage());
    }
    public int response(String content) {
        return 0;
    }

    public List<LeaveMessage> findResponseMSG(Map<String, Object> conValus) {
        return null;
    }

    public List<Map<String, Object>> getLoadoutExcelColumns() {
        return null;
    }

    public String getLoadoutExcelFileName() {
        return "留言信息表";
    }

    public DaoSupport getDAO() {
        return dao;
    }

    public List getSaveAll(String filePath) {
        return null;
    }
}
