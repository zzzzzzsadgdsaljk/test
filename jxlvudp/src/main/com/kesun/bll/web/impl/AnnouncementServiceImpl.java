package kesun.bll.web.impl;

import kesun.bll.SuperService;
import kesun.bll.web.IAnnouncement;
import kesun.dao.IDoData;
import kesun.dao.web.impl.DAnnouncement;
import kesun.entity.web.Announcement;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by heying on 2017/10/25.
 */
@Service("bAnnouncement")
public class AnnouncementServiceImpl extends SuperService implements IAnnouncement {
   @Resource(name ="dAnnouncement")
    private DAnnouncement dao;
   public AnnouncementServiceImpl(){
       setModel(new Announcement());
   }

    public List<Map<String, Object>> getLoadoutExcelColumns() {
        return null;
    }

    public String getLoadoutExcelFileName() {
        return "公告信息";
    }

    public IDoData getDAO() {
        return dao;
    }

    public List getSaveAll(String filePath) {
        return null;
    }

    @Override
    public Boolean isInUse() {
        return false;
    }
}
