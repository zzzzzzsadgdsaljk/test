package kesun.dao.system.impl;

import kesun.dao.DaoSupport;
import kesun.dao.system.IOrganization;
import kesun.entity.system.Organization;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by wph-pc on 2017/9/26.
 */
@Repository("dOrg")
public class DOrganization extends DaoSupport implements IOrganization {
    public DOrganization()
    {
        setMapperName("mapper.OrganizationMapper");
    }
    public int updateParent(Organization node) {
        if (node==null || node.getParent()==null || getSqlSessionTemplate()==null) return 0;
        return getSqlSessionTemplate().insert(getMapperName()+".updateParent",node);
    }
}
