package kesun.bll.web.impl;


import kesun.bll.SuperService;
import kesun.bll.web.INews;
import kesun.dao.IDoData;
import kesun.dao.web.impl.DNews;
import kesun.entity.web.ImageNews;
import kesun.entity.web.News;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by wph-pc on 2017/11/3.
 */
@Service("bNews")
public class NewsServiceImpl extends SuperService implements INews {
    @Resource(name="dNews")
    private DNews dao;

    public NewsServiceImpl()
    {
        setModel(new News());
    }
    public List<Map<String, Object>> getLoadoutExcelColumns() {
        return null;
    }

    public String getLoadoutExcelFileName() {
        return null;
    }

    public IDoData getDAO() {
        return dao;
    }

    public List getSaveAll(String filePath) {
        return null;
    }

    public int changeStatus(String status, List<String> ids) {
        return dao.changeStatus(status,ids);
    }

    public int delImg(String imageAddress) {
        return dao.delImg(imageAddress);
    }

    public List<ImageNews> imageNews(String id) {
        return dao.imageNews(id);
    }
}
