package kesun.bll.system.impl;

 ;
 import kesun.bll.SuperService;
 import kesun.bll.system.IDictionary;
 import kesun.dao.IDoData;
 import kesun.dao.system.impl.DDictionary;
 import kesun.entity.system.Dictionary;
 import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by wph-pc on 2017/9/26.
 */
@Service("bDictionary")
public class DictionaryServiceImpl extends SuperService implements IDictionary {
    @Resource(name="dDictionary")
    private DDictionary dao;
    public DictionaryServiceImpl()
    {
        setModel(new Dictionary());
    }
    public int updateParent() {
        if (getModel()==null || getModel() instanceof Dictionary==false) return 0;
        return dao.updateParent((Dictionary) getModel());
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
}
