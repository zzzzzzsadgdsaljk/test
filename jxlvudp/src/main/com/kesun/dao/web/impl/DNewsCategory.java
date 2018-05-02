package kesun.dao.web.impl;


import kesun.dao.DaoSupport;
import kesun.dao.web.INewsCategory;
import kesun.entity.web.NewsCategory;
import org.springframework.stereotype.Repository;

/**
 * Created by wph-pc on 2017/10/16.
 */
@Repository("dNewsCategory")
public class DNewsCategory extends DaoSupport implements INewsCategory {
    public DNewsCategory(){
        setMapperName("mapper.NewsCategoryMapper");//设置新闻分类数据库操作的配置文件
    }

    public int updateParent(NewsCategory node) {
        if (node==null || node.getParent()==null || getSqlSessionTemplate()==null) return 0;
        return getSqlSessionTemplate().insert(getMapperName()+".updateParent",node);
    }
}
