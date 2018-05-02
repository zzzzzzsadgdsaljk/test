package kesun.bll.system.impl;

import kesun.bll.SuperService;
import kesun.bll.system.IOrganization;
import kesun.dao.IDoData;
import kesun.dao.system.impl.DOrganization;
import kesun.entity.system.Organization;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by wph-pc on 2017/9/26.
 */
@Service("bOrg")
public class OrganizationServiceImpl extends SuperService implements IOrganization {
    @Resource(name="dOrg")
    private DOrganization dao;
    public OrganizationServiceImpl()
    {
        setModel(new Organization());
    }
    public int updateParent() {
        if (getModel()==null || getModel() instanceof Organization ==false) return 0;
        return dao.updateParent((Organization) getModel());
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
