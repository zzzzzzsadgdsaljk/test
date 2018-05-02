package kesun.bll.system.impl;

import kesun.bll.SuperService;
import kesun.bll.system.IUserOnlineRecord;
import kesun.dao.IDoData;
import kesun.dao.system.impl.DUserOnlineRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/*******************************************
 * 用户在线状态
 * Created by wph-pc on 2017/5/26.
 *******************************************/
@Service("bUserOnlineRecord")
public class UserOnlineRecordServiceImpl extends SuperService implements IUserOnlineRecord {
    @Autowired
    private DUserOnlineRecord dao;

    public List<Map<String, Object>> getLoadoutExcelColumns() {
        return null;
    }

    public String getLoadoutExcelFileName() {
        return "在线用户信息表";
    }

    public IDoData getDAO() {
        return dao;
    }

    public List getSaveAll(String filePath) {
            return null;
    }
}
