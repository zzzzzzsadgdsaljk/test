package kesun.bll.web.impl;

import kesun.bll.SuperService;
import kesun.dao.IDoData;
import kesun.dao.web.INotice;
import kesun.dao.web.impl.DNotice;
import kesun.entity.web.Notice;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by heying on 2017/10/29.
 */
@Service("bNotice")
public class NoticeServiceImpl extends SuperService implements INotice {
    @Resource(name = "dNotice")
    private DNotice dao;

    public NoticeServiceImpl() {
        setModel(new Notice());
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
