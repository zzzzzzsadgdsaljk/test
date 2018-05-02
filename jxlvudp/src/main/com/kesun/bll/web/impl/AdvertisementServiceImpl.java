package kesun.bll.web.impl;

import kesun.bll.SuperService;
import kesun.bll.web.IAdvertisement;
import kesun.dao.IDoData;
import kesun.dao.web.impl.DAdvertisement;
import kesun.entity.web.Advertisement;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by wph-pc on 2017/5/30.
 */
@Service("bAdvertisement")
public class AdvertisementServiceImpl extends SuperService implements IAdvertisement {
    @Resource(name="dAdvertisement")
    private DAdvertisement dao;

    public AdvertisementServiceImpl()
    {
        setModel(new Advertisement());
    }

    public List<Map<String, Object>> getLoadoutExcelColumns() {
        return null;
    }

    public String getLoadoutExcelFileName() {
        return "广告信息表";
    }

    public IDoData getDAO() {
        return dao;
    }

    public List getSaveAll(String filePath) {
        return null;
    }
}
