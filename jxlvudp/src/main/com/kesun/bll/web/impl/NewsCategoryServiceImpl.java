package kesun.bll.web.impl;

import kesun.bll.SuperService;
import kesun.bll.web.INewsCategory;
import kesun.dao.IDoData;
import kesun.dao.web.impl.DNewsCategory;
import kesun.entity.system.Organization;
import kesun.entity.web.NewsCategory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by wph-pc on 2017/10/16.
 */
@Service("bNewsCategory")
public class NewsCategoryServiceImpl extends SuperService implements INewsCategory {
    @Resource(name="dNewsCategory")
    private DNewsCategory dao;//信息分类数据层对象变量
    public NewsCategoryServiceImpl(){
        setModel(new NewsCategory());
    }
    public int updateParent() {
        if (getModel()==null || getModel() instanceof Organization ==false) return 0;
        return dao.updateParent((NewsCategory) getModel());
    }
    public List<Map<String, Object>> getLoadoutExcelColumns() {
        return null;
    }

    public String getLoadoutExcelFileName() {
        return "新闻分类信息表";
    }

    public IDoData getDAO() {
        return dao;
    }

    public List getSaveAll(String filePath) {
        return null;
    }
}
