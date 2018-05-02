package kesun.dao.system.impl;


import kesun.dao.DaoSupport;
import kesun.dao.system.IDictionary;
import kesun.entity.system.Dictionary;
import org.springframework.stereotype.Repository;

/**
 * Created by wph-pc on 2017/9/26.
 */
@Repository("dDictionary")
public class DDictionary extends DaoSupport implements IDictionary {
    public DDictionary()
    {
        setMapperName("mapper.DictionaryMapper");
    }

    public int updateParent(Dictionary node) {
        if (node==null || node.getParent()==null || getSqlSessionTemplate()==null) return 0;
        return getSqlSessionTemplate().insert(getMapperName()+".updateParent",node);
    }
}
