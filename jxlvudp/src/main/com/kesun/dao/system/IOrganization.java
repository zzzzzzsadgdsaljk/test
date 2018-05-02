package kesun.dao.system;


import kesun.entity.system.Organization;

import java.util.List;

/**
 * Created by wph-pc on 2017/9/26.
 */
public interface IOrganization {
    /*更新当前数据词典的父级节点*/
    int updateParent(Organization node);
}
