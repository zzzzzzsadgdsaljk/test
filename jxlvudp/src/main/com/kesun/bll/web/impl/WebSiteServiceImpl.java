package kesun.bll.web.impl;

import kesun.bll.SuperService;
import kesun.bll.web.IWebSite;
import kesun.dao.IDoData;
import kesun.dao.web.impl.DWebSite;
import kesun.entity.web.WebSite;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by wph-pc on 2017/5/30.
 */
@Service("bWebSite")
public class WebSiteServiceImpl extends SuperService implements IWebSite {
    @Resource(name="dWebSite")
    private DWebSite dao;
    public WebSiteServiceImpl()
    {
        setModel(new WebSite());
    }
    public List<Map<String, Object>> getLoadoutExcelColumns() {
        return null;
    }

    public String getLoadoutExcelFileName() {
        return "网站信息表";
    }

    public IDoData getDAO() {
        return dao;
    }

    public List getSaveAll(String filePath) {
        return null;
    }
}
